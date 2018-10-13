package com.mikiruki.pictdetector.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.provider.Settings;

/**
 * Helper class used to set Activity appearance - screen orientation.
 * As OpenCV lib does not support proper portrait orientation displaying it is necessary
 * to force landscape orientation as only in that way cam view is related to phone orientation.
 * Created by MikiRuki on 2018-10-03.
 */

public class ActivitySetup extends Activity {
    public static void setScreenOrientation(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void enableAutoOrientation(Context context)
    {
        try {
            if (Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 0) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
