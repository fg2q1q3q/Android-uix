package me.shouheng.uix.image.proc;

import android.graphics.Bitmap;

/**
 * 图片处理相关的管理类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @since 2020/09/01 23:12
 */
public final class ImageProcessor {

    /**
     * 对指定的 bitmap 进行二值化处理
     *
     * @param bitmap bitmap
     * @return       处理结果
     */
    public static Bitmap binarizationProcess(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        nativeBinaryzation(bitmap, outBitmap, 5);
        return outBitmap;
    }

    /**
     * 对指定的 bitmap 进行灰度处理
     *
     * @param bitmap bitmap
     * @return       处理结果
     */
    public static Bitmap greyProcess(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        nativeGreyProcess(bitmap, outBitmap);
        return outBitmap;
    }

    /**
     * 在 native 层做二值化处理
     *
     * @param srcBitmap source bitmap
     * @param outBitmap output bitmap
     * @param d         sigma color
     */
    public static native void nativeBinaryzation(Bitmap srcBitmap, Bitmap outBitmap, int d);

    /**
     * 在 native 层做灰度处理
     *
     * @param srcBitmap source bitmap
     * @param outBitmap output bitmap
     */
    public static native void nativeGreyProcess(Bitmap srcBitmap, Bitmap outBitmap);

    static {
        System.loadLibrary("img_cropper");
    }
}
