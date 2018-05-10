package com.mikiruki.pictdetector.activities;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mikiruki.pictdetector.R;
import com.mikiruki.pictdetector.camutils.CameraPreview;

public class MainActivity extends AppCompatActivity {

    FrameLayout preview;
    CameraPreview cameraPreview;
    Camera camInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void initializeComponents(){
        setContentView(R.layout.activity_main);
        preview = (FrameLayout) findViewById(R.id.camera_preview);

        camInstance = getCameraInstance();
        cameraPreview = new CameraPreview(this, camInstance);
        preview.addView(cameraPreview);
    }

    /**
     * Class used to retrieve camera instance
     * @return
     *      Camera instance if succeeded, null otherwise
     */
    public static Camera getCameraInstance(){
        Camera camera = null;
        try {
            // attempt to get a Camera instance
            camera = Camera.open();
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        // returns null if camera is unavailable
        return camera;
    }

    private void releaseCamera(){
        if (camInstance != null){
            camInstance.release();        // release the camera for other applications
            camInstance = null;
        }
    }
}
