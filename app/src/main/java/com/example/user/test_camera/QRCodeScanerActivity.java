package com.example.user.test_camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class QRCodeScanerActivity extends Activity {

    Boolean FlashMode = true;
    String TAG = "Zbar";
    String LIFECYCLE_TAG="LifeCycle";
    FrameLayout frameLayout;// a placeholder for code scanner preview
    ZBarScannerView myScannerView;// scanner view which we will add into frameLayout
    ZBarScannerView.ResultHandler resultHandler;//result handler for myScannerView

    Button FlashOnOffButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_activity);

        frameLayout = (FrameLayout) findViewById(R.id.PlaceHolderFram);

        //TODO: init the result handler
        resultHandler = new ZBarScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                // Do something with the result here
                Log.v(TAG, result.getContents()); // Prints scan results
                Log.v(TAG, result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
                turnOnCamera();//Keep the light on

                //Result show here
                ShowResultDialog(result.getContents());
            }
        };

        //TODO: init view and add view to layout
        myScannerView = new ZBarScannerView(QRCodeScanerActivity.this);
        frameLayout.addView(myScannerView);

        //A button to turn flash on and off
        FlashOnOffButton = (Button) findViewById(R.id.FlashOnOff_bt);
        FlashOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TurnFlashOnOff();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnOnCamera();
    }

    private void turnOnCamera(){
        //TODO: SET Result Handle Here
        myScannerView.setResultHandler(resultHandler);
        //TODO Start camera
        myScannerView.startCamera();
        //TODO Set flash and autofocus
        myScannerView.setFlash(FlashMode);
        myScannerView.setAutoFocus(true);
    }

    protected void onStop() {
        // Stop camera on stop
        super.onStop();
        Log.i(LIFECYCLE_TAG, "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LIFECYCLE_TAG, "onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(LIFECYCLE_TAG,"onPause");
        myScannerView.stopCamera();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void ShowResultDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScanerActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // MainActivity.this.onResume();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void TurnFlashOnOff(){
        if (FlashMode)
            FlashMode =false;
        else FlashMode = true;
        myScannerView.setFlash(FlashMode);
    }

}
