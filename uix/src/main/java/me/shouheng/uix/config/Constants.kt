package me.shouheng.uix.config

import android.support.annotation.IntDef
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_CITY
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_MIDDLE
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_TRIPLE
import me.shouheng.uix.config.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.config.DialogPosition.Companion.POS_CENTER
import me.shouheng.uix.config.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_MATCH
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_WRAP
import me.shouheng.uix.config.EmptyLoadingStyle.Companion.STYLE_ANDROID
import me.shouheng.uix.config.EmptyLoadingStyle.Companion.STYLE_IOS
import me.shouheng.uix.config.EmptyViewState.Companion.STATE_EMPTY
import me.shouheng.uix.config.EmptyViewState.Companion.STATE_LOADING
import me.shouheng.uix.config.LoadingButtonState.Companion.LOADING_STATE_DISABLE
import me.shouheng.uix.config.LoadingButtonState.Companion.LOADING_STATE_LOADING
import me.shouheng.uix.config.LoadingButtonState.Companion.LOADING_STATE_NORMAL


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
    }
}

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

/**
 * 列表为空的控件当前的状态
 */
@IntDef(value = [STATE_LOADING, STATE_EMPTY])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class EmptyViewState {
    companion object {
        /**
         * 加载中
         */
        const val STATE_LOADING                     = 0
        /**
         * 列表为空
         */
        const val STATE_EMPTY                       = 1
    }
}

/**
 * 加载小圆圈的风格
 */
@IntDef(value = [STYLE_ANDROID, STYLE_IOS])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class EmptyLoadingStyle {
    companion object {
        /**
         * Android 风格
         */
        const val STYLE_ANDROID                     = 0
        /**
         * iOS 风格
         */
        const val STYLE_IOS                         = 1
    }
}

/**
 * 带进度条的按钮的状态
 */
@IntDef(value = [LOADING_STATE_NORMAL, LOADING_STATE_LOADING, LOADING_STATE_DISABLE])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class LoadingButtonState {
    companion object {
        /**
         * 正常状态
         */
        const val LOADING_STATE_NORMAL              = 0
        /**
         * 加载状态
         */
        const val LOADING_STATE_LOADING             = 1
        /**
         * 一般状态
         */
        const val LOADING_STATE_DISABLE             = 2
    }
}