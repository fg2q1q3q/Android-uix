package me.shouheng.uix

import android.graphics.Color
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.Px
import android.support.annotation.Size
import android.view.Gravity
import me.shouheng.uix.UIXConfig.Button
import me.shouheng.uix.UIXConfig.Dialog
import me.shouheng.uix.UIXConfig.minClickDelayTime
import me.shouheng.uix.config.EmptyLoadingStyle
import me.shouheng.uix.utils.UIXUtils

/**
 * UIX 框架全局的配置，主要提供一些默认的配置参数更改
 *
 * 1. 一般对自定义配置，参考 [minClickDelayTime] 等；
 * 2. 按钮全局自定义，参考 [Button]
 * 3. 对话框全局自定义，参考 [Dialog]
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 12:31
 */
object UIXConfig {
    /**
     * 防止连续点击的时候允许的两次点击事件之间的间隔
     */
    var minClickDelayTime                       = 500
    /**
     * RecyclerView 判定为上下滚动的距离
     */
    var rvScrollJudgeHeight                     = 20
    /**
     * ViewPager 变换放缩的大小
     */
    @FloatRange(from = .0, to = 1.0)
    var depthPageTransScale                     = 0.75f

    object Dialog {
        /**
         * 对话框边距，单位 px
         */
        @Px
        var margin: Int                         = UIXUtils.dp2px(20f)
        /**
         * 对话框圆角，单位 px
         */
        @Px
        var cornerRadius: Int                   = UIXUtils.dp2px(15f)
        /**
         * 对话框明主题背景色
         */
        @ColorInt
        var lightBGColor: Int                   = UIXUtils.getColor(R.color.uix_default_light_bg_color)
        /**
         * 对话框暗主题背景色
         */
        @ColorInt
        var darkBGColor: Int                    = UIXUtils.getColor(R.color.uix_default_dark_bg_color)
        /**
         * 点击对话框外部是否可以取消对话框的全局配置
         */
        var outCancelable                       = true
        /**
         * 点击返回按钮是否可以取消对话框的全局配置
         */
        var backCancelable                      = true
        /**
         * 按钮底部的分割线的颜色
         */
        @ColorInt
        var dividerColor: Int?                  = null

        /**
         * 全局自定义群组 [SimpleTitle]
         */
        @ColorInt var titleTextColor: Int?      = null
        @Size var titleTextSize: Float          = 16f
        var titleTypeFace: Int                  = Typeface.NORMAL
        var titleGravity: Int                   = Gravity.CENTER

        /**
         * 全局自定义群组 [SimpleContent]
         */
        @ColorInt var contentTextColor: Int?      = null
        @Size var contentTextSize: Float          = 16f
        var contentTypeFace: Int                  = Typeface.NORMAL
        var contentGravity: Int                   = Gravity.CENTER

        /**
         * 全局配置群组 [MessageDialog]
         */
        @Size var msgTextSize: Float            = 16F
        @Size var msgTextColor: Int             = Color.WHITE
        var msgTypeFace: Int                    = Typeface.NORMAL
        @EmptyLoadingStyle
        var msgLoadingStyle: Int                = EmptyLoadingStyle.STYLE_IOS
        var msgCancelable: Boolean              = true
        var msgAnimation: Boolean                = true
        var msgBgColor: Int                     = Color.parseColor("#C0000000")
        var msgBgBorderRadiusInDp: Float        = 8f
    }

    object Button {
        /**
         * 按钮正常时的文字颜色
         */
        @ColorInt
        var textColor: Int                      = Color.BLACK
        /**
         * 按钮禁用时的文字颜色
         */
        @ColorInt
        var textDisableColor: Int               = Color.BLACK
        /**
         * 按钮正常时的颜色
         */
        @ColorInt
        var normalColor: Int                    = Color.TRANSPARENT
        /**
         * 按钮按下时的颜色距离正常时颜色的差值，值越大，按下时颜色越黑
         */
        @FloatRange(from = 0.0, to = 1.0)
        var fraction: Float                     = .1f
        /**
         * 按钮选中时的颜色
         */
        @ColorInt
        var selectedColor: Int                  = Color.TRANSPARENT
        /**
         * 按钮禁用时的颜色
         */
        @ColorInt
        var disableColor: Int                   = Color.TRANSPARENT
        /**
         * 按钮的圆角，单位 px
         */
        @Px
        var cornerRadius: Int                   = UIXUtils.dp2px(30f)
    }
}