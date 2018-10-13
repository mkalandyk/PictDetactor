package com.mikiruki.pictdetector.wrappers;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import org.opencv.android.JavaCameraView;

import java.util.List;

/**
 *
 * Created by MikiRuki on 2018-09-27.
 */

public class JavaCameraViewWrapper extends JavaCameraView {

    private String TAG = "JavaCameraViewWrapper";

    public JavaCameraViewWrapper(Context context, int cameraId) {
        super(context, cameraId);
    }

    public JavaCameraViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Setting camera resolution close to screen resolution.
     */
    @Override
    protected boolean initializeCamera(int width, int height) {

        Camera cam = Camera.open();
        Camera.Parameters params = cam.getParameters();
        List<Camera.Size> supportedSizes = params.getSupportedPreviewSizes();
        cam.release();

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        int bestWidth = 0;
        int bestHeight = 0;
        for(Camera.Size size : supportedSizes){
            if(size.width >= displayMetrics.widthPixels){
                bestWidth = size.width;
                bestHeight = size.height;
                break;
            }
        }

        return super.initializeCamera(bestWidth, bestHeight);
    }
}
