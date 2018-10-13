package com.mikiruki.pictdetector.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mikiruki.pictdetector.R;
import com.mikiruki.pictdetector.helpers.ActivitySetup;
import com.mikiruki.pictdetector.imageProcessors.PatternRecognizer;
import com.mikiruki.pictdetector.wrappers.JavaCameraViewWrapper;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener {

    private JavaCameraViewWrapper camView;
    public static boolean libInitialized = false;
    private PatternRecognizer patternRecognizer;

    static {
        if(!OpenCVLoader.initDebug()){
            libInitialized = false;
            Log.i("OpenCV", "OpenCV initialization failed");
        } else {
            libInitialized = true;
            Log.i("OpenCV", "OpenCV initialization successful");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();
        runCameraView();

        ActivitySetup.enableAutoOrientation(this);
        ActivitySetup.setScreenOrientation(this);

        patternRecognizer = new PatternRecognizer(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camView != null)
            camView.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(camView != null)
            camView.disableView();
    }

    private void initializeComponents(){
        setContentView(R.layout.activity_main);
        camView = (JavaCameraViewWrapper) findViewById(R.id.camView);
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.i("CV", "started");
    }

    @Override
    public void onCameraViewStopped() {
        Log.i("CV", "stopped");
    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {
        return patternRecognizer.recognize(inputFrame);
    }

    private void runCameraView(){
        camView.setCvCameraViewListener(this);

        camView.enableFpsMeter();
        camView.enableView();
    }
}
