package me.shouheng.uix.common.anno

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.Px

@Target(allowedTargets = [AnnotationTarget.TYPE])
@Retention(value = AnnotationRetention.RUNTIME)
annotation class LoadingButtonConfiguration (
        /** 按钮正常时的文字颜色 */
        @ColorInt
        val textColor: Int                      = Color.BLACK,
        /** 按钮禁用时的文字颜色 */
        @ColorInt
        val textDisableColor: Int               = Color.BLACK,
        /** 按钮正常时的颜色 */
        @ColorInt
        val normalColor: Int                    = Color.TRANSPARENT,
        /** 按钮按下时的颜色距离正常时颜色的差值，值越大，按下时颜色越黑 */
        @FloatRange(from = 0.0, to = 1.0)
        val fraction: Float                     = .1f,
        /** 按钮选中时的颜色 */
        @ColorInt
        val selectedColor: Int                  = Color.TRANSPARENT,
        /** 按钮禁用时的颜色 */
        @ColorInt
        val disableColor: Int                   = Color.TRANSPARENT,
        /** 按钮的圆角，单位 dp */
        @Px
        val cornerRadius: Int                   = 30
)
