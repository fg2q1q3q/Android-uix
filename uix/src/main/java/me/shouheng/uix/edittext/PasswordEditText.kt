package me.shouheng.uix.edittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.shouheng.uix.R
import me.shouheng.uix.utils.UIXUtils

/**
 * 密码编辑器
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-03 23:42
 */
class PasswordEditText : RegexEditText, View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private var mCurrentDrawable: Drawable? = null
    private var mVisibleDrawable: Drawable? = null
    private var mInvisibleDrawable: Drawable? = null

    private var mOnTouchListener: OnTouchListener? = null
    private var mOnFocusChangeListener: OnFocusChangeListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize(context: Context, attrs: AttributeSet?) {
        super.initialize(context, attrs)

        val array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText)
        val imageTintColor = array.getColor(R.styleable.PasswordEditText_visibility_image_tint_color, -1)
        array.recycle()

        // Wrap the drawable so that it can be tinted pre Lollipop
        val dp24 = UIXUtils.dp2px(24f)
        val visibleDrawable = ContextCompat.getDrawable(context, R.drawable.ic_eye_open_48)!!
        val invisibleDrawable = ContextCompat.getDrawable(context, R.drawable.ic_eye_close_48)!!
        mVisibleDrawable = DrawableCompat.wrap(
                if (imageTintColor == -1) visibleDrawable
                else UIXUtils.tintDrawable(visibleDrawable, imageTintColor)
        )
        mVisibleDrawable!!.setBounds(0, 0, dp24, dp24)

        mInvisibleDrawable = DrawableCompat.wrap(
                if (imageTintColor == -1) invisibleDrawable
                else UIXUtils.tintDrawable(invisibleDrawable, imageTintColor)
        )
        mInvisibleDrawable!!.setBounds(0, 0, dp24, dp24)

        mCurrentDrawable = mVisibleDrawable

        // 密码不可见
        addInputType(TYPE_INVISIBLE)
        if (inputRegex == null) {
            // 密码输入规则
            inputRegex = REGEX_NONNULL
        }

        setDrawableVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        super.addTextChangedListener(this)
    }

    private fun setDrawableVisible(visible: Boolean) {
        if (mCurrentDrawable!!.isVisible == visible) {
            return
        }

        mCurrentDrawable!!.setVisible(visible, false)
        val drawables = compoundDrawables
        setCompoundDrawables(drawables[0], drawables[1], if (visible) mCurrentDrawable else null, drawables[3])
    }

    private fun refreshDrawableStatus() {
        val drawables = compoundDrawables
        setCompoundDrawables(drawables[0], drawables[1], mCurrentDrawable, drawables[3])
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        mOnTouchListener = onTouchListener
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus && text != null) {
            setDrawableVisible(text!!.isNotEmpty())
        } else {
            setDrawableVisible(false)
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener!!.onFocusChange(view, hasFocus)
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x.toInt()
        if (mCurrentDrawable!!.isVisible && x > width - paddingRight - mCurrentDrawable!!.intrinsicWidth) {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (mCurrentDrawable === mVisibleDrawable) {
                    mCurrentDrawable = mInvisibleDrawable
                    // 密码可见
                    removeInputType(TYPE_INVISIBLE)
                    addInputType(TYPE_VISIBLE)
                    refreshDrawableStatus()
                } else if (mCurrentDrawable === mInvisibleDrawable) {
                    mCurrentDrawable = mVisibleDrawable
                    // 密码不可见
                    removeInputType(TYPE_VISIBLE)
                    addInputType(TYPE_INVISIBLE)
                    refreshDrawableStatus()
                }
                val editable = text
                if (editable != null) {
                    setSelection(editable.toString().length)
                }
            }
            return true
        }
        return mOnTouchListener != null && mOnTouchListener!!.onTouch(view, motionEvent)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isFocused) {
            setDrawableVisible(s.isNotEmpty())
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable) {}

    companion object {
        private const val TYPE_VISIBLE      = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        private const val TYPE_INVISIBLE    = InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
}