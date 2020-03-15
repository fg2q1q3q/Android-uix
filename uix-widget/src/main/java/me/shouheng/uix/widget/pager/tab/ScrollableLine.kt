package me.shouheng.uix.widget.pager.tab

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-01 00:07
 */
class ScrollableLine : View {

    private var mIndicatorLineRadius: Float = 0.toFloat()

    private var mIndicatorLineHeight: Int = 0

    private var mRectF: RectF? = null

    private var mPaint: Paint? = null

    private var mIndicatorStartX: Float = 0.toFloat()

    private var mIndicatorEndX: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        initScrollableLine(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initScrollableLine(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initScrollableLine(context)
    }

    private fun initScrollableLine(context: Context) {
        mPaint = Paint()
        mRectF = RectF()
    }

    fun setIndicatorStyle(indicatorLineHeight: Int, indicatorLineRadius: Int) {
        this.mIndicatorLineHeight = indicatorLineHeight
        this.mIndicatorLineRadius = indicatorLineRadius.toFloat()
    }

    fun updateScrollLineWidth(indicatorStartX: Float, indicatorEndX: Float, indicatorColor: Int) {
        this.mIndicatorStartX = indicatorStartX
        this.mIndicatorEndX = indicatorEndX
        mPaint!!.color = indicatorColor
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRectF!!.set(mIndicatorStartX, 0f, mIndicatorEndX, mIndicatorLineHeight.toFloat())
        canvas.drawRoundRect(mRectF!!, mIndicatorLineRadius, mIndicatorLineRadius, mPaint!!)
    }
}
