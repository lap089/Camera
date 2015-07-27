package com.example.user.test_camera;

import android.app.Activity;
import android.app.ProgressDialog;
<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
<<<<<<< HEAD
import android.widget.Button;
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class ImageEditorActivity extends Activity {

    public int BLUR = 0;
    public int MINOR = 1;

    private ImageView imageview;
    private ImageButton Blurbutton;
<<<<<<< HEAD
    private Button orc;
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
    private ImageButton Minorbutton;
    private String path;
    private Bitmap bitmap;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
        imageview = (ImageView) findViewById(R.id.imageview);
        Blurbutton = (ImageButton) findViewById(R.id.blur);
        Minorbutton = (ImageButton) findViewById(R.id.minor);
<<<<<<< HEAD
        orc = (Button) findViewById(R.id.orc);
=======
>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f
        path = getIntent().getExtras().getString("PathImage", null);

        File imgFile = new File(path);

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
            imageview.setImageBitmap(bitmap);
        }
           Blurbutton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View arg0) {
                   new BitmapAsyn().execute(BLUR);
               }
           });

        Minorbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new BitmapAsyn().execute(MINOR);
            }
        });

<<<<<<< HEAD
        orc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(ImageEditorActivity.this, OrcActivity.class);
                intent.putExtra("Path",path);
                startActivity(intent);
            }
        });
=======

>>>>>>> 6969cf0a748f5d3fe187bf01a4911a3dd29e429f

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public class BitmapAsyn extends AsyncTask<Integer,Void,Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(ImageEditorActivity.this);
            mProgressDialog.setMessage("Processing...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            try{
                InputStream is = getResources().openRawResource(R.raw.hieu);
               // bitmap1 = BitmapFactory.decodeStream(is);

                if(params[0] == 0)
                bitmap =  BitmapEditor.BoxBlurImprove(bitmap,25,2);
                else if(params[0]==1)
                bitmap =  BitmapEditor.MirrorLeftToRight(bitmap);

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"bmp1.png");
                OutputStream os1 = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,os1);
                return bitmap;
            }
            catch (Exception e){e.printStackTrace();}

            return null;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
              imageview.setImageBitmap(result);
            //  SendMail("test1");
          //  Toast.makeText(ImageEditorActivity.this,"Done!")
            mProgressDialog.dismiss();
        }


    }

}
