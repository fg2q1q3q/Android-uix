package me.shouheng.image.proc;

import android.graphics.Bitmap;
import android.graphics.Point;
import me.shouheng.image.utils.CropUtils;
import me.shouheng.utils.stability.L;

public class BeautyCropper {

    public static Bitmap crop(Bitmap srcBmp, Point[] cropPoints) {
        if (srcBmp == null || cropPoints == null) {
            throw new IllegalArgumentException("srcBmp and cropPoints cannot be null");
        }
        if (cropPoints.length != 4) {
            throw new IllegalArgumentException("The length of cropPoints must be 4 , and sort by leftTop, rightTop, rightBottom, leftBottom");
        }
        Point leftTop = cropPoints[0];
        Point rightTop = cropPoints[1];
        Point rightBottom = cropPoints[2];
        Point leftBottom = cropPoints[3];

        int cropWidth = (int) ((CropUtils.getPointsDistance(leftTop, rightTop)
                + CropUtils.getPointsDistance(leftBottom, rightBottom)) / 2);
        int cropHeight = (int) ((CropUtils.getPointsDistance(leftTop, leftBottom)
                + CropUtils.getPointsDistance(rightTop, rightBottom)) / 2);

        L.d("srcBitmap: (" + srcBmp.getWidth() + ", " + srcBmp.getHeight() + ")");
        L.d("leftTop:" + leftTop + " rightTop:" + rightTop + " leftBottom:" + leftBottom + " rightBottom:" + rightBottom);
        L.d("Bitmap size to crop (" + cropWidth + "," + cropHeight + ")");
        Bitmap cropBitmap = Bitmap.createBitmap(cropWidth, cropHeight, Bitmap.Config.ARGB_8888);
//        BeautyCropper.nativeCrop(srcBmp, cropPoints, cropBitmap);
        L.d("Bitmap size to crop (" + cropBitmap.getWidth() + "," + cropBitmap.getHeight() + ")");
        return cropBitmap;
    }

//    private static native void nativeCrop(Bitmap srcBitmap, Point[] points, Bitmap outBitmap);
//
//    static {
//        System.loadLibrary("img_cropper");
//    }
}
