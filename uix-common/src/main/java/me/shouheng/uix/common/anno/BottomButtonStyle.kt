package me.shouheng.uix.common.anno

import android.support.annotation.IntDef
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_TRIPLE

/**
 * 对话框底部的按钮的风格
 */
@IntDef(value = [BUTTON_STYLE_SINGLE, BUTTON_STYLE_DOUBLE, BUTTON_STYLE_TRIPLE])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class BottomButtonStyle {
    companion object {
        const val BUTTON_STYLE_SINGLE                     = 1
        const val BUTTON_STYLE_DOUBLE                     = 2
        const val BUTTON_STYLE_TRIPLE                     = 3
    }
}