package me.shouheng.uix.common.anno

import android.support.annotation.IntDef
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_MATCH
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_WRAP

/**
 * 对话框风格
 */
@IntDef(value = [STYLE_MATCH, STYLE_WRAP])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class DialogStyle {
    companion object {
        /** 对话框风格：按照对话框内容进行延伸 */
        const val STYLE_WRAP                        = 0
        /** 对话框风格：对话框宽度填充屏幕 */
        const val STYLE_MATCH                       = 1
    }
}
