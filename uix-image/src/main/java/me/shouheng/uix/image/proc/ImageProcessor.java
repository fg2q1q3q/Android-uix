package me.shouheng.uix.image.proc;

import android.graphics.Bitmap;

/**
 * 图片处理相关的管理类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @since 2020/09/01 23:12
 */
public final class ImageProcessor {

    public static Bitmap binarization(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        nativeBinarization(bitmap, outBitmap, 5);
        return outBitmap;
    }

    public static Bitmap grerify(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        nativeGrerify(bitmap, outBitmap);
        return outBitmap;
    }

//    public static native void nativeBinarization(Bitmap srcBitmap, Bitmap outBitmap, int d);
//
//    public static native void nativeGrerify(Bitmap srcBitmap, Bitmap outBitmap);
//
//    static {
//        System.loadLibrary("img_cropper");
//    }
}
