package me.shouheng.uix.widget.button

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.Px
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import me.shouheng.uix.common.anno.LoadingButtonState
import me.shouheng.uix.common.anno.LoadingButtonState.Companion.LOADING_STATE_DISABLE
import me.shouheng.uix.common.anno.LoadingButtonState.Companion.LOADING_STATE_LOADING
import me.shouheng.uix.common.anno.LoadingButtonState.Companion.LOADING_STATE_NORMAL
import me.shouheng.uix.common.anno.LoadingStyle
import me.shouheng.uix.common.utils.UIXColorUtils
import me.shouheng.uix.common.utils.UIXImageUtils
import me.shouheng.uix.common.utils.UIXResUtils
import me.shouheng.uix.common.utils.UIXViewUtils
import me.shouheng.uix.widget.R

/**
 * 带加载进度条的按钮
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-12-28 15:00
 */
class LoadingButton : LinearLayout {

    private val ll: LinearLayout
    private val pb: ProgressBar
    private val tv: TextView

    private lateinit var stateListDrawable: StateListDrawable
    private lateinit var disableDrawable: Drawable
    private lateinit var normalDrawable: Drawable

    private var loadingStyle: Int = LoadingStyle.STYLE_ANDROID
    private var text: String? = null
    private var textColor: Int = Color.BLACK
    private var textDisableColor: Int = Color.BLACK
    private var loadingButtonState: Int = LOADING_STATE_NORMAL

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.uix_loading_button, this,true)
        ll = findViewById(R.id.ll_btn)
        pb = findViewById(R.id.pb)
        tv = findViewById(R.id.text)
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton)
            text = typedArray.getString(R.styleable.LoadingButton_loading_button_text)
            val size = typedArray.getDimensionPixelSize(R.styleable.LoadingButton_loading_button_text_size, UIXViewUtils.sp2px(16f))
            textColor = typedArray.getColor(R.styleable.LoadingButton_loading_button_text_color, GlobalConfig.textColor)
            textDisableColor = typedArray.getColor(R.styleable.LoadingButton_loading_button_text_disable_color, GlobalConfig.textDisableColor)
            val normalColor = typedArray.getColor(R.styleable.LoadingButton_loading_button_normal_color, GlobalConfig.normalColor)
            val disableColor = typedArray.getColor(R.styleable.LoadingButton_loading_button_disable_color, GlobalConfig.disableColor)
            val selectedColor = if (typedArray.hasValue(R.styleable.LoadingButton_loading_button_selected_color))
                typedArray.getColor(R.styleable.LoadingButton_loading_button_selected_color, GlobalConfig.selectedColor)
            else UIXColorUtils.computeColor(normalColor, Color.BLACK, GlobalConfig.fraction)
            val cornerRadius = typedArray.getDimensionPixelSize(R.styleable.LoadingButton_loading_button_corner_radius,
                    UIXViewUtils.dp2px(GlobalConfig.cornerRadius.toFloat()))
            normalDrawable = UIXImageUtils.getGradientDrawable(normalColor, cornerRadius.toFloat())
            disableDrawable = UIXImageUtils.getGradientDrawable(disableColor, cornerRadius.toFloat())
            val selectedDrawable = UIXImageUtils.getGradientDrawable(selectedColor, cornerRadius.toFloat())
            loadingStyle = typedArray.getInt(R.styleable.LoadingButton_loading_button_style, LoadingStyle.STYLE_ANDROID)
            loadingButtonState = typedArray.getInt(R.styleable.LoadingButton_loading_button_state, LOADING_STATE_NORMAL)
            this.stateListDrawable = StateListDrawable()
            this.stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            this.stateListDrawable.addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
            setState(loadingButtonState)
            tv.text = text
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
            tv.setTextColor(textColor)
            if (loadingStyle == LoadingStyle.STYLE_IOS) {
                pb.indeterminateDrawable = UIXResUtils.getDrawable(R.drawable.uix_anim_loading)
            }
            typedArray.recycle()
        }
    }

    fun setState(@LoadingButtonState state: Int) {
        loadingButtonState = state
        when (state) {
            LOADING_STATE_NORMAL -> {
                pb.visibility = View.GONE
                isClickable = true
                ll.background = stateListDrawable
                tv.setTextColor(textColor)
            }
            LOADING_STATE_LOADING -> {
                pb.visibility = View.VISIBLE
                isClickable = false
                ll.background = normalDrawable
                tv.setTextColor(textColor)
            }
            LOADING_STATE_DISABLE -> {
                pb.visibility = View.GONE
                isClickable = false
                ll.background = disableDrawable
                tv.setTextColor(textDisableColor)
            }
        }
    }

    fun setLoading(loading: Boolean) {
        if (loading) {
            setState(LOADING_STATE_LOADING)
        } else {
            setState(LOADING_STATE_NORMAL)
        }
    }

    fun setText(text: String) {
        this.text = text
        tv.text = text
    }

    fun setEllipsize(ellipsize: TextUtils.TruncateAt) {
        tv.ellipsize = ellipsize
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) {
            setState(LOADING_STATE_NORMAL)
        } else {
            setState(LOADING_STATE_DISABLE)
        }
    }

    object GlobalConfig {
        /** 按钮正常时的文字颜色 */
        @ColorInt
        var textColor: Int                      = Color.BLACK
        /** 按钮禁用时的文字颜色 */
        @ColorInt
        var textDisableColor: Int               = Color.BLACK
        /** 按钮正常时的颜色 */
        @ColorInt
        var normalColor: Int                    = Color.TRANSPARENT
        /** 按钮按下时的颜色距离正常时颜色的差值，值越大，按下时颜色越黑 */
        @FloatRange(from = 0.0, to = 1.0)
        var fraction: Float                     = .1f
        /** 按钮选中时的颜色 */
        @ColorInt
        var selectedColor: Int                  = Color.TRANSPARENT
        /** 按钮禁用时的颜色 */
        @ColorInt
        var disableColor: Int                   = Color.TRANSPARENT
        /** 按钮的圆角，单位 dp */
        @Px
        var cornerRadius: Int                   = 30
    }
}