package com.example.user.test_camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
<<<<<<< HEAD
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
=======

import android.app.Activity;
import android.content.Intent;
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
<<<<<<< HEAD
import android.hardware.camera2.CameraDevice;
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    public Point size;
    Preview preview;
    ImageButton buttonClick;
<<<<<<< HEAD
    Button qrcode;
   public static Camera camera;
    public static int currentcamera;
public String path;
public ImageView myImage;
    public static int currentId;
    public ImageButton exchange;

    public static final String PACKAGE_NAME = "com.datumdroid.android.ocr.simple";
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/SimpleAndroidOCR/";
    public static final String lang = "eng";

=======
   public static Camera camera;
public String path;
public ImageButton myImage;
    public static int currentId;
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
       Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
           currentcamera = bundle.getInt("Camerafacing",Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        else
        currentcamera = Camera.CameraInfo.CAMERA_FACING_BACK;

        camera = getCameraInstance(currentcamera);
        exchange = (ImageButton) findViewById(R.id.exchange);
        preview = new Preview(this,camera);
        ((FrameLayout) findViewById(R.id.preview)).addView(preview);
         myImage = (ImageView) findViewById(R.id.imageview);
        buttonClick = (ImageButton) findViewById(R.id.buttonClick);
        qrcode = (Button) findViewById(R.id.qrcode);
=======
        camera = getCameraInstance();
        preview = new Preview(this,camera);
        ((FrameLayout) findViewById(R.id.preview)).addView(preview);
         myImage = (ImageButton) findViewById(R.id.imageview);
        buttonClick = (ImageButton) findViewById(R.id.buttonClick);

>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f

        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
<<<<<<< HEAD
        Log.d(TAG, "Screen size: " + size.x + "-" + size.y);


        new Thread() {
            @Override
            public void run() {
                InitializeORC();
            }
        }.start();
=======
        Log.d(TAG,"Screen size: " + size.x + "-" + size.y);
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f


        buttonClick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD
                new Thread() {
=======

            new Thread() {
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
                    @Override
                    public void run() {
                        preview.camera.takePicture(shutterCallback, rawCallback,
                                jpegCallback);
                    }
                }.start();

            }
        });



<<<<<<< HEAD

=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
        myImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this,ImageEditorActivity.class);
                intent.putExtra("PathImage",path);
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        qrcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, QRCodeScanerActivity.class);
                startActivity(intent);

            }
        });


        exchange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

        //      finish();
        //        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        //        currentcamera = currentcamera == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        //        intent.putExtra("Camerafacing",currentcamera);
        //    startActivity(intent);

                ((FrameLayout) findViewById(R.id.preview)).removeView(preview);
                currentcamera = currentcamera == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
                camera = getCameraInstance(currentcamera);
                preview = new Preview(MainActivity.this,camera);
                ((FrameLayout) findViewById(R.id.preview)).addView(preview);
            }
        });

=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f

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

            Log.d(TAG, "Saving file..." + data.length);

                  createFile createfile = new createFile();
                    createfile.execute(data);


        }

    };


<<<<<<< HEAD

    public void InitializeORC()
    {
        String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }

        // lang.traineddata file with the app (in assets folder)
        // You can get them at:
        // http://code.google.com/p/tesseract-ocr/downloads/list
        // This area needs work and optimization
        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
                //GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/" + lang + ".traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len,count =0 ;
                //while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                    count += len;
                    Log.d(TAG, "Copied " + count);
                }
                in.close();
                //gin.close();
                out.close();

                Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }
        }

    }



=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
    private class createFile extends AsyncTask<byte[],Bitmap,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, values[0].getWidth() + "-" + values[0].getHeight());
        //    int nh = (int) ( values[0].getHeight() * (512.0 / values[0].getWidth()) );
         //   Bitmap scaled = Bitmap.createScaledBitmap(values[0], 512, nh, true);
<<<<<<< HEAD
            if(size.x < values[0].getWidth() && size.y < values[0].getHeight())
            {

                Bitmap scaled = Bitmap.createScaledBitmap(values[0],size.x/4, size.y/4, true);
                values[0] = scaled;
            }
=======
           // if(size.x < values[0].getWidth() && size.y < values[0].getHeight())
           // {
                Bitmap scaled = Bitmap.createScaledBitmap(values[0],size.x/4, size.y/4, true);
                values[0] = scaled;
           // }
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
            myImage.setImageBitmap(values[0]);
        }

        @Override
        protected Bitmap doInBackground(byte[]... params) {

            FileOutputStream outStream = null;
            try {
                // write to local sandbox file system
                // outStream =
                // CameraDemo.this.openFileOutput(String.format("%d.jpg",
                // System.currentTimeMillis()), 0);
                // Or write to sdcard
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+ String.format("%d_lap.jpg", System.currentTimeMillis());

             //   outStream = new FileOutputStream(path);
             //   outStream.write(params[0]);
             //   outStream.close();

                try {
             //       File f = new File(path);
                  /*  ExifInterface exif = new ExifInterface(f.getPath());
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
                    }*/

                    camera.startPreview();
                    Matrix mat = new Matrix();
                    mat.postRotate(Preview.rotation);

                /// /    Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
                    Bitmap bmp = BitmapFactory.decodeByteArray(params[0],0,params[0].length);
                    Bitmap correctBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);


                    publishProgress(correctBmp);
                    // bmp.recycle();

                    File file = new File(path);
                    FileOutputStream fOut = new FileOutputStream(file);

                    correctBmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();


                    Log.i(TAG, "onPictureTaken - wrote bytes: " + params[0].length);
                    return correctBmp;
                }
                catch (IOException e) {
                    Log.w("TAG", "-- Error in setting image");
                }
                catch(OutOfMemoryError oom) {
                    Log.w("TAG", "-- OOM Error in setting image");
                }






                //  SetImage();

         //   } catch (FileNotFoundException e) {
         //       e.printStackTrace();
         //   } catch (IOException e) {
         //       e.printStackTrace();
            } finally {
            }
         return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);


            Toast.makeText(MainActivity.this,"Done!!!",Toast.LENGTH_SHORT);
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();

        if(camera == null) {
<<<<<<< HEAD
            camera = getCameraInstance(currentcamera);
=======
            camera = getCameraInstance();
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
            preview = new Preview(this,camera);
            ((FrameLayout) findViewById(R.id.preview)).addView(preview);

            Log.i(TAG, "onResume");

        }
    }


<<<<<<< HEAD
   
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
<<<<<<< HEAD
        Log.d("Preview","on pause");
        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
       /* if (camera != null) {
            //TODO:
=======
        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
       /* if (camera != null) {
            //TODO: mewo;
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
            camera.release();
            camera = null;
            Log.i(TAG, "Release camera");
        }
*/
<<<<<<< HEAD
        ((FrameLayout) findViewById(R.id.preview)).removeView(preview);
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
    }



    /** A safe way to get an instance of the Camera object. */
<<<<<<< HEAD
    public static Camera getCameraInstance(int camera_facing){
=======
    public static Camera getCameraInstance(){
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
        Camera c = null;
        try {
                int numberOfCameras = Camera.getNumberOfCameras();
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, info);
<<<<<<< HEAD
                    if (info.facing == camera_facing) {
=======
                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
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