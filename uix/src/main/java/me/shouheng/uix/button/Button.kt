package me.shouheng.uix.button

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.Gravity
import me.shouheng.uix.R
import me.shouheng.uix.UIXConfig
import me.shouheng.uix.UIXConfig.Button.disableColor
import me.shouheng.uix.UIXConfig.Button.normalColor
import me.shouheng.uix.UIXConfig.Button.selectedColor
import me.shouheng.uix.utils.UIXUtils

/**
 * 普通的点击按钮
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-01-05 10:11
 */
class Button : AppCompatButton {

    private var stateListDrawable: StateListDrawable? = null
    private var disableDrawable: Drawable? = null
    private var normalDrawable: Drawable? = null

    private var textDisableColor: Int = UIXConfig.Button.textDisableColor

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Button)
            textDisableColor = typedArray.getColor(R.styleable.LoadingButton_loading_button_text_disable_color, textDisableColor)
            val normalColor = typedArray.getColor(R.styleable.Button_btn_normal_color, normalColor)
            val selectedColor = if (typedArray.hasValue(R.styleable.Button_btn_selected_color))
                typedArray.getColor(R.styleable.Button_btn_selected_color, selectedColor)
            else UIXUtils.computeColor(normalColor, Color.BLACK, UIXConfig.Button.fraction)
            val disableColor = typedArray.getColor(R.styleable.Button_btn_disable_color, disableColor)
            val cornerRadius = typedArray.getDimensionPixelSize(R.styleable.Button_btn_corner_radius, UIXConfig.Button.cornerRadius)
            normalDrawable = UIXUtils.getGradientDrawable(normalColor, cornerRadius.toFloat())
            disableDrawable = UIXUtils.getGradientDrawable(disableColor, cornerRadius.toFloat())
            val selectedDrawable = UIXUtils.getGradientDrawable(selectedColor, cornerRadius.toFloat())
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
}