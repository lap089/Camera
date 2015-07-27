package com.example.user.test_camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OrcActivity extends Activity {
    public static final String PACKAGE_NAME = "com.datumdroid.android.ocr.simple";
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/SimpleAndroidOCR/";

    // You should have the trained data file in assets folder
    // You can get them at:
    // http://code.google.com/p/tesseract-ocr/downloads/list
    public static final String lang = "eng";

    private static final String TAG = "SimpleAndroidOCR.java";

    protected ImageView _image;
    protected EditText _field;
    protected String _path;
    protected boolean _taken;
    protected Bitmap bitmap;

    protected static final String PHOTO_TAKEN = "photo_taken";

    @Override
    public void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orc);

        _image = (ImageView) findViewById(R.id.orcimage);
        _field = (EditText) findViewById(R.id.recognizedtext);
        _path = getIntent().getExtras().getString("Path", null);


        File imgFile = new File(_path);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        if(bitmap!=null) {
            //     int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
            //     Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            //     bitmap = scaled;
            //      if(size.x < bitmap.getWidth() && size.y < bitmap.getHeight())
            //    {
            double scale = (double) size.x/bitmap.getWidth();
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap,size.x/2 , size.y/2, true);
            bitmap = scaled;
            //  }
            _image.setImageBitmap(bitmap);
        }


        onPhotoTaken();
    }


    // Simple android photo capture:
    // http://labs.makemachine.net/2010/03/simple-android-photo-capture/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(OrcActivity.PHOTO_TAKEN, _taken);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState()");
        if (savedInstanceState.getBoolean(OrcActivity.PHOTO_TAKEN)) {
            onPhotoTaken();
        }
    }



    protected void onPhotoTaken() {
       /* _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(_path, options);

        try {
            ExifInterface exif = new ExifInterface(_path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Log.v(TAG, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            Log.v(TAG, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }

            // Convert to ARGB_8888, required by tess
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        } catch (IOException e) {
            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
        }

        // _image.setImageBitmap( bitmap );

        Log.v(TAG, "Before baseApi");
*/



        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH, lang);
        baseApi.setImage(bitmap);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        Log.v(TAG, "OCRED TEXT: " + recognizedText);

        if ( lang.equalsIgnoreCase("eng") ) {
            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
        }

        recognizedText = recognizedText.trim();

        if ( recognizedText.length() != 0 ) {
            _field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
            _field.setSelection(_field.getText().toString().length());
        }

        // Cycle done.
    }

    // www.Gaut.am was here
    // Thanks for reading!
}
