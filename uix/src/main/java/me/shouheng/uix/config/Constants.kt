package me.shouheng.uix.config

import android.support.annotation.IntDef
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_CITY
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_MIDDLE
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_LEFT_ONLY
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_MIDDLE_ONLY
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_RIGHT_ONLY
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_THREE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_TWO
import me.shouheng.uix.config.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.config.DialogPosition.Companion.POS_CENTER
import me.shouheng.uix.config.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_MATCH
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_WRAP


/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:17
 */

/**
 * 对话框位置
 */
@IntDef(value=[POS_CENTER, POS_BOTTOM, POS_TOP])
@Retention(value = AnnotationRetention.SOURCE)
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class DialogPosition {
    companion object {
        const val POS_CENTER                    = 0
        const val POS_BOTTOM                    = 1
        const val POS_TOP                       = 2
    }
}

/**
 * 对话框风格
 */
@IntDef(value = [STYLE_MATCH, STYLE_WRAP])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class DialogStyle {
    companion object {
        /**
         * 对话框风格：按照对话框内容进行延伸
         */
        const val STYLE_WRAP                        = 0
        /**
         * 对话框风格：对话框宽度填充屏幕
         */
        const val STYLE_MATCH                       = 1

        /**
         * 对话框风格：按照对话框内容进行延伸且无背景
         */
        const val STYLE_WRAP_WITHOUT_BG             = 2
    }
}

/**
 * 对话框底部的按钮的风格
 */
@IntDef(value = [BUTTON_LEFT_ONLY, BUTTON_MIDDLE_ONLY, BUTTON_RIGHT_ONLY,
    BUTTON_TWO, BUTTON_THREE])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class BottomButtonStyle {
    companion object {
        const val BUTTON_LEFT_ONLY                  = 1
        const val BUTTON_MIDDLE_ONLY                = 2
        const val BUTTON_RIGHT_ONLY                 = 3
        const val BUTTON_TWO                        = 4
        const val BUTTON_THREE                      = 5
    }
}

/**
 * 对话框底部按钮的位置
 */
@IntDef(value = [BUTTON_POS_LEFT, BUTTON_POS_MIDDLE, BUTTON_POS_RIGHT])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class BottomButtonPosition {
    companion object {
        const val BUTTON_POS_LEFT                   = 1
        const val BUTTON_POS_MIDDLE                 = 2
        const val BUTTON_POS_RIGHT                  = 3
    }
}

/**
 * 地址选择的最大等级
 */
@IntDef(value = [LEVEL_CITY, LEVEL_AREA])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class AddressSelectLevel {
    companion object {
        const val LEVEL_CITY                        = 0
        const val LEVEL_AREA                        = 1
    }
}