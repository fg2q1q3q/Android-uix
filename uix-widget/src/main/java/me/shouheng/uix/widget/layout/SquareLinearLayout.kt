package me.shouheng.uix.widget.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * 正方形的 RelativeLayout 布局
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019/10/1 11:47
 */
class SquareLinearLayout : LinearLayout {

    constructor(context: Context, attrs: AttributeSet, defStyle:Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context): super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec))
        val width = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY)
        super.onMeasure(width, width)
    }
}