package me.shouheng.uix.widget.text

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import me.shouheng.utils.ktx.dp
import me.shouheng.utils.ui.ImageUtils
import kotlin.math.max

class DotNumberView : AppCompatTextView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun showNumber(num: String, @ColorInt circleColor: Int) {
        text = num
        background = ImageUtils.getDrawable(circleColor, 30f.dp().toFloat())
        post {
            val size = max(width, height)
            layoutParams = layoutParams.apply {
                width = size
                height = size
            }
            requestLayout()
        }
    }
}