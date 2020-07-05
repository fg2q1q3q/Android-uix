package me.shouheng.uix.widget.button

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.Px
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.Gravity
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UIXColorUtils
import me.shouheng.uix.common.utils.UIXImageUtils
import me.shouheng.uix.common.utils.UIXViewUtils
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.button.NormalButton.GlobalConfig

/**
 * 普通的点击按钮 可通过 [GlobalConfig] 设置按钮的全局属性，通过各个 setter 方法设置
 * 某个按钮的属性。使用 setter 方法设置的属性会覆盖掉全局的属性。
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-01-05 10:11
 */
class NormalButton : AppCompatButton {

    private var stateListDrawable: StateListDrawable? = null
    private var disableDrawable: Drawable? = null
    private var normalDrawable: Drawable? = null

    private var textDisableColor: Int = GlobalConfig.textDisableColor

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalButton)
            textDisableColor = typedArray.getColor(R.styleable.NormalButton_btn_text_disable_color, textDisableColor)
            val normalColor = typedArray.getColor(R.styleable.NormalButton_btn_normal_color, GlobalConfig.normalColor)
            val selectedColor = if (typedArray.hasValue(R.styleable.NormalButton_btn_selected_color))
                typedArray.getColor(R.styleable.NormalButton_btn_selected_color, GlobalConfig.selectedColor)
            else UIXColorUtils.computeColor(normalColor, Color.BLACK, GlobalConfig.fraction)
            val disableColor = typedArray.getColor(R.styleable.NormalButton_btn_disable_color, GlobalConfig.disableColor)
            val cornerRadius = typedArray.getDimensionPixelSize(R.styleable.NormalButton_btn_corner_radius, GlobalConfig.cornerRadius)
            normalDrawable = UIXImageUtils.getGradientDrawable(normalColor, cornerRadius.toFloat())
            disableDrawable = UIXImageUtils.getGradientDrawable(disableColor, cornerRadius.toFloat())
            val selectedDrawable = UIXImageUtils.getGradientDrawable(selectedColor, cornerRadius.toFloat())
            this.stateListDrawable = StateListDrawable()
            this.stateListDrawable!!.addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            this.stateListDrawable!!.addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
            typedArray.recycle()
        }
        gravity = Gravity.CENTER
        setEnabledInternal(isEnabled)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setEnabledInternal(enabled)
    }

    private fun setEnabledInternal(enabled: Boolean) {
        if (enabled) {
            background = stateListDrawable
            isClickable = true
        } else {
            background = disableDrawable
            setTextColor(textDisableColor)
            isClickable = false
        }
    }

    fun setStyle(style: TextStyleBean) {
        if (style.textSize != null) this.textSize = style.textSize!!
        if (style.gravity != null) this.gravity = style.gravity!!
        if (style.textColor != null) setTextColor(style.textColor!!)
        if (style.typeFace != null) this.setTypeface(null, style.typeFace!!)
    }

    fun setStyle(self: TextStyleBean, global: TextStyleBean) {
        if (self.textSize != null) {
            this.textSize = self.textSize!!
        } else if (global.textSize != null) {
            this.textSize = global.textSize!!
        }
        if (self.gravity != null) {
            this.gravity = self.gravity!!
        } else if (global.gravity != null) {
            this.gravity = global.gravity!!
        }
        if (self.textColor != null) {
            setTextColor(self.textColor!!)
        } else if (global.textColor != null) {
            setTextColor(global.textColor!!)
        }
        if (self.typeFace != null) {
            this.setTypeface(null, self.typeFace!!)
        } else if (global.typeFace != null) {
            this.setTypeface(null, global.typeFace!!)
        }
    }

    object GlobalConfig {
        /** 按钮正常时的文字颜色 */
        @ColorInt var textColor: Int                            = Color.BLACK
        /** 按钮禁用时的文字颜色 */
        @ColorInt var textDisableColor: Int                     = Color.BLACK
        /** 按钮正常时的颜色 */
        @ColorInt var normalColor: Int                          = Color.TRANSPARENT
        /** 按钮按下时的颜色距离正常时颜色的差值，值越大，按下时颜色越黑 */
        @FloatRange(from = 0.0, to = 1.0) var fraction: Float   = .1f
        /** 按钮选中时的颜色 */
        @ColorInt var selectedColor: Int                        = Color.TRANSPARENT
        /** 按钮禁用时的颜色 */
        @ColorInt var disableColor: Int                         = Color.TRANSPARENT
        /** 按钮的圆角，单位 px */
        @Px var cornerRadius: Int                             = UIXViewUtils.dp2px(30f)
    }
}