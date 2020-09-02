//
// Created by WngShhng on 2019/3/11.
//

#include <jni.h>
#include <string>
#include <android_utils.h>

extern "C" JNIEXPORT void JNICALL
Java_me_shouheng_image_proc_ImageProcessor_nativeBinarization(JNIEnv *env, jclass type, jobject srcBitmap, jobject outBitmap, jint d) {
    Mat srcBitmapMat;
    bitmap_to_mat(env, srcBitmap, srcBitmapMat);

    AndroidBitmapInfo outBitmapInfo;
    AndroidBitmap_getInfo(env, outBitmap, &outBitmapInfo);
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
    mat_to_bitmap(env, dstBitmapMat, outBitmap);
}

extern "C"
JNIEXPORT void JNICALL
Java_me_shouheng_image_proc_ImageProcessor_nativeGrerify(JNIEnv *env, jclass type, jobject srcBitmap, jobject outBitmap) {
    Mat srcBitmapMat;
    bitmap_to_mat(env, srcBitmap, srcBitmapMat);

    AndroidBitmapInfo outBitmapInfo;
    AndroidBitmap_getInfo(env, outBitmap, &outBitmapInfo);
    Mat dstBitmapMat;
    int newHeight = outBitmapInfo.height;
    int newWidth = outBitmapInfo.width;
    dstBitmapMat = Mat::zeros(newHeight, newWidth, srcBitmapMat.type());

    cvtColor(srcBitmapMat, dstBitmapMat, COLOR_RGB2GRAY);
    mat_to_bitmap(env, dstBitmapMat, outBitmap);
}
