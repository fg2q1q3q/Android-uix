package me.shouheng.uix.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import me.shouheng.uix.R

/**
 * 支持宽高比的布局
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-12-22 13:29
 */
class RatioFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    /** 宽高比  */
    private val mSizeRatio: Float

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout)
        mSizeRatio = array.getFloat(R.styleable.RatioFrameLayout_sizeRatio, 0f)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = widthMeasureSpec
        var h = heightMeasureSpec
        if (mSizeRatio != 0f) {
            val widthSpecMode = MeasureSpec.getMode(w)
            val widthSpecSize = MeasureSpec.getSize(w)

            val heightSpecMode = MeasureSpec.getMode(h)
            val heightSpecSize = MeasureSpec.getSize(h)

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode != MeasureSpec.EXACTLY) {
                // 如果当前宽度是写死的，但是高度不写死
                h = MeasureSpec.makeMeasureSpec((widthSpecSize / mSizeRatio).toInt(), MeasureSpec.EXACTLY)
            } else if (heightSpecMode == MeasureSpec.EXACTLY && widthSpecMode != MeasureSpec.EXACTLY) {
                // 如果当前高度是写死的，但是宽度不写死
                w = MeasureSpec.makeMeasureSpec((heightSpecSize * mSizeRatio).toInt(), MeasureSpec.EXACTLY)
            }
        }
        super.onMeasure(w, h)
    }
}
