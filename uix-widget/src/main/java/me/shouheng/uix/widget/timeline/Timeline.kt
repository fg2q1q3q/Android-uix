package me.shouheng.uix.widget.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.Dimension
import android.util.AttributeSet
import android.view.View
import me.shouheng.uix.widget.R

class Timeline @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @Dimension private var atomSize = 24
    @Dimension private var lineSize = 12
    private var startLine: Drawable? = null
    private var finishLine: Drawable? = null
    private var atomDrawable: Drawable? = null

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Timeline)
        atomSize = typedArray.getDimensionPixelSize(R.styleable.Timeline_atom_size, atomSize)
        lineSize = typedArray.getDimensionPixelSize(R.styleable.Timeline_line_size, lineSize)
        startLine = typedArray.getDrawable(R.styleable.Timeline_start_line)
        finishLine = typedArray.getDrawable(R.styleable.Timeline_finish_line)
        atomDrawable = typedArray.getDrawable(R.styleable.Timeline_atom)
        typedArray.recycle()
        startLine?.callback = this
        finishLine?.callback = this
        atomDrawable?.callback = this
    }

    override fun onDraw(canvas: Canvas) {
        startLine?.draw(canvas)
        finishLine?.draw(canvas)
        atomDrawable?.draw(canvas)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = paddingLeft + paddingRight
        var h = paddingTop + paddingBottom
        if (atomDrawable != null) {
            w += atomSize
            h += atomSize
        }
        w = w.coerceAtLeast(measuredWidth)
        h = h.coerceAtLeast(measuredHeight)
        val width = resolveSizeAndState(w, widthMeasureSpec, 0)
        val height = resolveSizeAndState(h, heightMeasureSpec, 0)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initDrawableSize()
    }

    private fun initDrawableSize() {
        val pLeft = paddingLeft
        val pRight = paddingRight
        val pTop = paddingTop
        val pBottom = paddingBottom
        val width = width
        val height = height
        val cWidth = width - pLeft - pRight
        val cHeight = height - pTop - pBottom
        val bounds: Rect
        bounds = if (atomDrawable != null) {
            val atomSize = atomSize.coerceAtMost(cWidth.coerceAtMost(cHeight))
            atomDrawable?.setBounds(pLeft, pTop, pLeft + atomSize, pTop + atomSize)
            atomDrawable!!.bounds
        } else {
            Rect(pLeft, pTop, pLeft + cWidth, pTop + cHeight)
        }
        val halfLineSize = lineSize shr 1
        val lineLeft = bounds.centerX() - halfLineSize
        startLine?.setBounds(lineLeft, 0, lineLeft + lineSize, bounds.top)
        finishLine?.setBounds(lineLeft, bounds.bottom, lineLeft + lineSize, height)
    }

    fun setLineSize(lineSize: Int) {
        if (this.lineSize != lineSize) {
            this.lineSize = lineSize
            initDrawableSize()
            invalidate()
        }
    }

    fun setAtomSize(atomSize: Int) {
        if (this.atomSize != atomSize) {
            this.atomSize = atomSize
            initDrawableSize()
            invalidate()
        }
    }

    fun setStartLine(startLine: Drawable) {
        if (this.startLine !== startLine) {
            this.startLine = startLine
            this.startLine?.callback = this
            initDrawableSize()
            invalidate()
        }
    }

    fun setFinishLine(finishLine: Drawable) {
        if (this.finishLine !== finishLine) {
            this.finishLine = finishLine
            this.finishLine?.callback = this
            initDrawableSize()
            invalidate()
        }
    }

    fun setAtomDrawable(atomDrawable: Drawable) {
        if (this.atomDrawable !== atomDrawable) {
            this.atomDrawable = atomDrawable
            this.atomDrawable?.callback = this
            initDrawableSize()
            invalidate()
        }
    }
}