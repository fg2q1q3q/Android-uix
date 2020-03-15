package me.shouheng.uix.common.anno

import android.support.annotation.IntDef
import me.shouheng.uix.common.anno.LoadingStyle.Companion.STYLE_ANDROID
import me.shouheng.uix.common.anno.LoadingStyle.Companion.STYLE_IOS

/**
 * 加载小圆圈的风格
 */
@IntDef(value = [STYLE_ANDROID, STYLE_IOS])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class LoadingStyle {
    companion object {
        /** Android 风格 */
        const val STYLE_ANDROID                     = 0
        /** iOS 风格 */
        const val STYLE_IOS                         = 1
    }
}