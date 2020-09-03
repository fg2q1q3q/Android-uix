//
// Created by WngShhng on 2019/3/11.
//

#include <jni.h>
#include <string>
#include <android_utils.h>

static struct {
    jclass jClassPoint;
    jmethodID jMethodInit;
    jfieldID jFieldIDX;
    jfieldID jFieldIDY;
} gPointInfo;

/**
 * 库被初始化的时候用来初始化需要的字段信息
 *
 * @param env env
 */
static void initClassInfo(JNIEnv *env) {
    gPointInfo.jClassPoint = reinterpret_cast<jclass>(env -> NewGlobalRef(env -> FindClass("android/graphics/Point")));
    gPointInfo.jMethodInit = env -> GetMethodID(gPointInfo.jClassPoint, "<init>", "(II)V");
    gPointInfo.jFieldIDX = env -> GetFieldID(gPointInfo.jClassPoint, "x", "I");
    gPointInfo.jFieldIDY = env -> GetFieldID(gPointInfo.jClassPoint, "y", "I");
}

/**
 * 将 java 层的点位数组转换为 native 层的 Point 对象
 *
 * @param env    env
 * @param points java 层的点位
 * @return       结果
 */
static std::vector<Point> pointsToNative(JNIEnv *env, jobjectArray points) {
    std::vector<Point> result;
    for(int i = 0, len = env->GetArrayLength(points); i < len; i++) {
        jobject point = env -> GetObjectArrayElement(points, i);
        int pX = env -> GetIntField(point, gPointInfo.jFieldIDX);
        int pY = env -> GetIntField(point, gPointInfo.jFieldIDY);
        result.emplace_back(pX, pY);
    }
    return result;
}

extern "C"
JNIEXPORT void JNICALL
Java_me_shouheng_uix_image_proc_CropManager_nativeCrop(JNIEnv *env, jclass clazz,
                                                       jobject src_bitmap, jobjectArray _points,
                                                       jobject out_bitmap) {
    std::vector<Point> points = pointsToNative(env, _points);
    if (points.size() != 4) return;

    // 获取传入的顶点参数
    Point leftTop = points[0], rightTop = points[1], rightBottom = points[2], leftBottom = points[3];

    // 将源 bitmap 转换为 cv 的 mat 对象
    Mat srcBitmapMat, dstBitmapMat;
    bitmap_to_mat(env, src_bitmap, srcBitmapMat);
    AndroidBitmapInfo outBitmapInfo;
    AndroidBitmap_getInfo(env, out_bitmap, &outBitmapInfo);
    int newHeight = outBitmapInfo.height, newWidth = outBitmapInfo.width;
    dstBitmapMat = Mat::zeros(newHeight, newWidth, srcBitmapMat.type());

    std::vector<Point2f> srcTriangle, dstTriangle;

    srcTriangle.emplace_back(leftTop.x, leftTop.y);
    srcTriangle.emplace_back(rightTop.x, rightTop.y);
    srcTriangle.emplace_back(leftBottom.x, leftBottom.y);
    srcTriangle.emplace_back(rightBottom.x, rightBottom.y);

    dstTriangle.emplace_back(0, 0);
    dstTriangle.emplace_back(newWidth, 0);
    dstTriangle.emplace_back(0, newHeight);
    dstTriangle.emplace_back(newWidth, newHeight);

    // 进行透视变换来获取裁剪结果
    Mat transform = getPerspectiveTransform(srcTriangle, dstTriangle);
    warpPerspective(srcBitmapMat, dstBitmapMat, transform, dstBitmapMat.size());

    // 将裁剪的结果转换为 java 层的对象并进行输出
    mat_to_bitmap(env, dstBitmapMat, out_bitmap);
}

extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return JNI_FALSE;
    }
    initClassInfo(env);
    return JNI_VERSION_1_4;
}