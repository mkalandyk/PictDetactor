package com.mikiruki.pictdetector.imageProcessors;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to images comparison and symbol detection.
 * Created by MikiRuki on 2018-10-10.
 */

public class PatternRecognizer {

    private Map<String, Mat> patterns;
    private Context context;

    public PatternRecognizer(Context context) {
        this.context = context;
        patterns = new HashMap<>();
        loadPatterns(patterns);
    }

    private void loadPatterns(Map<String, Mat> patterns) {
        String templatesPath = "templates";
        AssetManager assetManager = context.getAssets();
        try {
            for (String fileName : assetManager.list(templatesPath)) {
                InputStream inputStream = assetManager.open(templatesPath + "/" + fileName);
                Mat mat = new Mat();
                Utils.bitmapToMat(BitmapFactory.decodeStream(inputStream), mat);
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                patterns.put(fileName, mat);
                Log.i("PatternRecognizer", "filename: " + fileName);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public Mat recognize(Mat inputFrame) {

        Mat template = patterns.get("template");

        int result_cols = inputFrame.cols() - template.cols() + 1;
        int result_rows = inputFrame.rows() - template.rows() + 1;

        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        Imgproc.matchTemplate(inputFrame, patterns.get("template"), result, Imgproc.TM_CCOEFF_NORMED);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        Point matchLoc = mmr.maxLoc;
        Imgproc.rectangle(inputFrame, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(0, 255, 0));

        return inputFrame;
    }
}
