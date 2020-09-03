//
// Created by WngShhng on 2019/3/11.
//

#include <jni.h>
#include <string>
#include <android_utils.h>

extern "C"
JNIEXPORT void JNICALL
Java_me_shouheng_uix_image_proc_ImageProcessor_nativeBinaryzation(JNIEnv *env, jclass clazz,
                                                                  jobject src_bitmap,
                                                                  jobject out_bitmap, jint d) {
    Mat srcBitmapMat;
    bitmap_to_mat(env, src_bitmap, srcBitmapMat);

    AndroidBitmapInfo outBitmapInfo;
    AndroidBitmap_getInfo(env, out_bitmap, &outBitmapInfo);
    Mat dstBitmapMat, greyBitmapMat, bfBitmapMat;
    int newHeight = outBitmapInfo.height;
    int newWidth = outBitmapInfo.width;
    dstBitmapMat = Mat::zeros(newHeight, newWidth, srcBitmapMat.type());
    greyBitmapMat = Mat::zeros(newHeight, newWidth, srcBitmapMat.type());
    bfBitmapMat = Mat::zeros(newHeight, newWidth, srcBitmapMat.type());

    cvtColor(srcBitmapMat, greyBitmapMat, COLOR_RGB2GRAY);
    bilateralFilter(greyBitmapMat, bfBitmapMat, d, d * 2.0, d / 2.0);
//    threshold(greyBitmapMat, dstBitmapMat, 10, 255.0, CV_THRESH_BINARY);
    adaptiveThreshold(bfBitmapMat, dstBitmapMat, 255.0, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 25, 10.0);
    mat_to_bitmap(env, dstBitmapMat, out_bitmap);
}

extern "C"
JNIEXPORT void JNICALL
Java_me_shouheng_uix_image_proc_ImageProcessor_nativeGreyProcess(JNIEnv *env, jclass clazz,
                                                                 jobject src_bitmap,
                                                                 jobject out_bitmap) {
    Mat srcBitmapMat;
    bitmap_to_mat(env, src_bitmap, srcBitmapMat);

    AndroidBitmapInfo outBitmapInfo;
    AndroidBitmap_getInfo(env, out_bitmap, &outBitmapInfo);
    Mat dstBitmapMat;
    int newHeight = outBitmapInfo.height;
    int newWidth = outBitmapInfo.width;
    dstBitmapMat = Mat::zeros(newHeight, newWidth, srcBitmapMat.type());

    cvtColor(srcBitmapMat, dstBitmapMat, COLOR_RGB2GRAY);
    mat_to_bitmap(env, dstBitmapMat, out_bitmap);
}