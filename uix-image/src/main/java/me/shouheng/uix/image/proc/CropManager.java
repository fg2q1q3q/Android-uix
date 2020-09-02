package me.shouheng.uix.image.proc;

import android.graphics.Bitmap;
import android.graphics.Point;

import me.shouheng.uix.common.utils.ULog;
import me.shouheng.uix.image.utils.CropUtils;

/**
 * 裁剪相关的管理类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @since 2020/09/01 23:12
 */
public class CropManager {

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

        int cropWidth = (int) ((CropUtils.getDistance(leftTop, rightTop)
                + CropUtils.getDistance(leftBottom, rightBottom)) / 2);
        int cropHeight = (int) ((CropUtils.getDistance(leftTop, leftBottom)
                + CropUtils.getDistance(rightTop, rightBottom)) / 2);

        ULog.INSTANCE.d("srcBitmap: (" + srcBmp.getWidth() + ", " + srcBmp.getHeight() + ")");
        ULog.INSTANCE.d("leftTop:" + leftTop + " rightTop:" + rightTop + " leftBottom:" + leftBottom + " rightBottom:" + rightBottom);
        ULog.INSTANCE.d("Bitmap size to crop (" + cropWidth + "," + cropHeight + ")");
        Bitmap cropBitmap = Bitmap.createBitmap(cropWidth, cropHeight, Bitmap.Config.ARGB_8888);
//        BeautyCropper.nativeCrop(srcBmp, cropPoints, cropBitmap);
        ULog.INSTANCE.d("Bitmap size to crop (" + cropBitmap.getWidth() + "," + cropBitmap.getHeight() + ")");
        return cropBitmap;
    }

//    private static native void nativeCrop(Bitmap srcBitmap, Point[] points, Bitmap outBitmap);
//
//    static {
//        System.loadLibrary("img_cropper");
//    }
}
