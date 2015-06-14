package com.example.user.test_camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    Preview preview;
    ImageButton buttonClick;
    Camera camera;
public String path;
public ImageView myImage;
    public static int currentId;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = getCameraInstance();
        preview = new Preview(this,camera);
        ((FrameLayout) findViewById(R.id.preview)).addView(preview);
         myImage = (ImageView) findViewById(R.id.imageview);
        buttonClick = (ImageButton) findViewById(R.id.buttonClick);
        buttonClick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

            new Thread() {
                    @Override
                    public void run() {
                        preview.camera.takePicture(shutterCallback, rawCallback,
                                jpegCallback);
                    }
                }.start();

            }
        });

        Log.i(TAG, "onCreate'd");
    }

    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };

    /** Handles data for raw pictcure */
    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    /** Handles data for jpeg picture */
    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {

            Log.d(TAG,"Saving file...");
            FileOutputStream outStream = null;
            try {
                // write to local sandbox file system
                // outStream =
                // CameraDemo.this.openFileOutput(String.format("%d.jpg",
                // System.currentTimeMillis()), 0);
                // Or write to sdcard
                path = String.format("/sdcard/Download/%d_lap.jpg", System.currentTimeMillis());
                outStream = new FileOutputStream(path);
                outStream.write(data);
                outStream.close();

                try {
                    File f = new File(path);
                    ExifInterface exif = new ExifInterface(f.getPath());
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                    int angle = 0;

                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                        angle = 90;
                    }
                    else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                        angle = 180;
                    }
                    else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                        angle = 270;
                    }

                    Matrix mat = new Matrix();
                    mat.postRotate(Preview.rotation);

                    Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
                    Bitmap correctBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);

                    myImage.setImageBitmap(correctBmp);
                   // bmp.recycle();

                   File file = new File(path);
                    FileOutputStream fOut = new FileOutputStream(file);

                    correctBmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                }
                catch (IOException e) {
                    Log.w("TAG", "-- Error in setting image");
                }
                catch(OutOfMemoryError oom) {
                    Log.w("TAG", "-- OOM Error in setting image");
                }






              //  SetImage();
                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            Log.d(TAG, "onPictureTaken - jpeg");

            camera.startPreview();

        }

    };

    public void SetImage()
    {
        File imgFile = new  File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myImage.setImageBitmap(myBitmap);
          /*  Matrix matrix = new Matrix();
            myImage.setScaleType(ImageView.ScaleType.MATRIX);   //required
            matrix.postRotate( Preview.rotation,myImage.getDrawable().getBounds().width()/2, myImage.getDrawable().getBounds().height()/2);
            myImage.setImageMatrix(matrix);
    */    }

    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
       /* if (camera != null) {
            //TODO: mewo;
            camera.release();
            camera = null;
            Log.i(TAG, "Release camera");
        }
*/
    }



    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
                int numberOfCameras = Camera.getNumberOfCameras();
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, info);
                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        //Log.d(DEBUG_TAG, "Camera found");
                        currentId = i;
                        break;
                    }
                }

            c = Camera.open(currentId); // attempt to get a Camera instance
            Log.i(TAG,"open camera");
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.e("CAMERA","No Camera");
        }

        return c; // returns null if camera is unavailable
    }
}