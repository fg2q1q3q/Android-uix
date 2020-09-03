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
public final class CropManager {

    /**
     * 对传入的 bitmap 进行裁剪
     *
     * @param srcBmp     源 bitmap
     * @param cropPoints 要裁剪的点
     * @return           裁剪结果
     */
    public static Bitmap crop(Bitmap srcBmp, Point[] cropPoints) {
        if (srcBmp == null || cropPoints == null) {
            throw new IllegalArgumentException("srcBmp and cropPoints cannot be null");
        }
        if (cropPoints.length != 4) {
            throw new IllegalArgumentException("The length of cropPoints must be 4 and sort by leftTop, rightTop, rightBottom, leftBottom");
        }
        Point leftTop = cropPoints[0];
        Point rightTop = cropPoints[1];
        Point rightBottom = cropPoints[2];
        Point leftBottom = cropPoints[3];

        int cropWidth = (int) ((CropUtils.getDistance(leftTop, rightTop)
                + CropUtils.getDistance(leftBottom, rightBottom)) / 2);
        int cropHeight = (int) ((CropUtils.getDistance(leftTop, leftBottom)
                + CropUtils.getDistance(rightTop, rightBottom)) / 2);

        ULog.INSTANCE.d("Source : (" + srcBmp.getWidth() + ", " + srcBmp.getHeight() + ")");
        ULog.INSTANCE.d("Expect : (" + leftTop + ", " + rightTop + ", " + leftBottom + ", " + rightBottom + ")");
        ULog.INSTANCE.d("Expect : (" + cropWidth + ", " + cropHeight + ")");
        Bitmap cropBitmap = Bitmap.createBitmap(cropWidth, cropHeight, Bitmap.Config.ARGB_8888);
        CropManager.nativeCrop(srcBmp, cropPoints, cropBitmap);
        ULog.INSTANCE.d("Bitmap size to crop (" + cropBitmap.getWidth() + "," + cropBitmap.getHeight() + ")");
        return cropBitmap;
    }

    /**
     * 对指定的 bitmap 进行裁剪
     *
     * @param srcBitmap 源 bitmap
     * @param points    裁剪的点位
     * @param outBitmap 输出的结果
     */
    private static native void nativeCrop(Bitmap srcBitmap, Point[] points, Bitmap outBitmap);

    static {
        System.loadLibrary("img_cropper");
    }
}
