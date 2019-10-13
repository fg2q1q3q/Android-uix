package me.shouheng.uix.dialog

import android.support.annotation.IntDef

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:17
 */

/**
 * 对话框位置
 */
@IntDef(value=[Constants.POS_CENTER, Constants.POS_BOTTOM, Constants.POS_TOP])
@Retention(value = AnnotationRetention.SOURCE)
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class DialogPosition

/**
 * 对话框风格
 */
@IntDef(value = [Constants.STYLE_MATCH, Constants.STYLE_WRAP])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class DialogStyle

object Constants {

    const val POS_CENTER:Int          = 0
    const val POS_BOTTOM:Int          = 1
    const val POS_TOP:Int             = 2

    /**
     * 对话框风格：按照对话框内容进行延伸
     */
    const val STYLE_WRAP              = 0
    /**
     * 对话框风格：对话框宽度填充屏幕
     */
    const val STYLE_MATCH             = 1
}
