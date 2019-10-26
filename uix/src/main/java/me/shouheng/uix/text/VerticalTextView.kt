package me.shouheng.uix.text

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.TextView

/**
 * 在 [TextView] 的基础上支持文字竖排
 *
 * 默认将文字竖排显示, 可使用 [.setVerticalMode] 来开启/关闭竖排功能
 */
class VerticalTextView : AppCompatTextView {

    /**
     * 是否将文字显示成竖排
     */
    var isVerticalMode = true
        set(verticalMode) {
            field = verticalMode
            requestLayout()
        }

    private var mLineCount: Int = 0 // 行数
    private var mLineWidths: FloatArray? = null // 下标: 行号; 数组内容: 该行的宽度(由该行最宽的字符决定)
    private var mLineBreakIndex: IntArray? = null // 下标: 行号; 数组内容: 该行最后一个字符的下标

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {}

    @SuppressLint("DrawAllocation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (isVerticalMode) {
            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            val heightSize = MeasureSpec.getSize(heightMeasureSpec)

            var width = (paddingLeft + paddingRight).toFloat()
            var height = (paddingTop + paddingBottom).toFloat()
            val chars = text.toString().toCharArray()
            val paint = paint
            val fontMetricsInt = paint.fontMetricsInt

            val lineMaxBottom = (if (heightMode == MeasureSpec.UNSPECIFIED) Integer.MAX_VALUE else heightSize) - paddingBottom

            var currentLineHeight = paddingTop.toFloat()
            var lineMaxHeight = currentLineHeight
            var lineIndex = 0
            mLineCount = 0
            mLineWidths = FloatArray(chars.size + 1) // 加1是为了处理高度不够放下一个字的情况,needBreakLine会一直为true直到最后一个字
            mLineBreakIndex = IntArray(chars.size + 1)
            // 从右向左,从上向下布局
            var step = 1
            run {
                var i = 0
                while (i < chars.size) {
                    val codePoint = Character.codePointAt(chars, i)
                    step = Character.charCount(codePoint)
                    // rotate
                    val needRotate = !isCJKCharacter(codePoint)
                    // char height
                    val charHeight: Float
                    val charWidth: Float
                    if (needRotate) {
                        charWidth = (fontMetricsInt.descent - fontMetricsInt.ascent).toFloat()
                        charHeight = paint.measureText(chars, i, step)
                    } else {
                        charWidth = paint.measureText(chars, i, step)
                        charHeight = (fontMetricsInt.descent - fontMetricsInt.ascent).toFloat()
                    }

                    // is need break line
                    val needBreakLine = currentLineHeight + charHeight > lineMaxBottom && i > 0 // i > 0 是因为即使在第一列高度不够,也不用换下一列
                    if (needBreakLine) {
                        // break line
                        if (lineMaxHeight < currentLineHeight) {
                            lineMaxHeight = currentLineHeight
                        }
                        mLineBreakIndex?.set(lineIndex, i - step)
                        width += mLineWidths!![lineIndex]
                        lineIndex++
                        // reset
                        currentLineHeight = paddingTop + charHeight
                    } else {
                        currentLineHeight += charHeight
                        if (lineMaxHeight < currentLineHeight) {
                            lineMaxHeight = currentLineHeight
                        }
                    }

                    if (mLineWidths!![lineIndex] < charWidth) {
                        mLineWidths!![lineIndex] = charWidth
                    }
                    // last column width
                    if (i + step >= chars.size) {
                        width += mLineWidths!![lineIndex]
                        height = lineMaxHeight + paddingBottom
                    }
                    i += step
                }
            }
            if (chars.isNotEmpty()) {
                mLineCount = lineIndex + 1
                mLineBreakIndex!![lineIndex] = chars.size - step
            }

            // 计算 lineSpacing
            if (mLineCount > 1) {
                val lineSpacingCount = mLineCount - 1
                for (i in 0 until lineSpacingCount) {
                    width += mLineWidths!![i] * (lineSpacingMultiplier - 1) + lineSpacingExtra
                }
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize.toFloat()
            } else if (heightMode == MeasureSpec.AT_MOST) {
                height = height.coerceAtMost(heightSize.toFloat())
            }
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize.toFloat()
            } else if (widthMode == MeasureSpec.AT_MOST) {
                width = width.coerceAtMost(widthSize.toFloat())
            }

            setMeasuredDimension(width.toInt(), height.toInt())
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onDraw(canvas: Canvas) {
        if (!isVerticalMode) {
            super.onDraw(canvas)
        } else {
            if (mLineCount == 0) {
                return
            }

            val paint = paint
            paint.color = currentTextColor
            paint.drawableState = drawableState
            val fontMetricsInt = paint.fontMetricsInt
            val chars = text.toString().toCharArray()

            canvas.save()

            var curLine = 0
            var curLineX = width.toFloat() - paddingRight.toFloat() - mLineWidths!![curLine]
            var curX = curLineX
            var curY = paddingTop.toFloat()
            var step: Int
            var i = 0
            while (i < chars.size) {
                val codePoint = Character.codePointAt(chars, i)
                step = Character.charCount(codePoint)
                val needRotate = !isCJKCharacter(codePoint)
                val saveCount = canvas.save()
                if (needRotate) {
                    canvas.rotate(90f, curX, curY)
                }
                // draw
                val textX = curX
                val textBaseline = if (needRotate)
                    curY - (mLineWidths!![curLine] - (fontMetricsInt.bottom - fontMetricsInt.top)) / 2 - fontMetricsInt.descent.toFloat()
                else
                    curY - fontMetricsInt.ascent
                canvas.drawText(chars, i, step, textX, textBaseline, paint)
                canvas.restoreToCount(saveCount)

                // if break line
                val hasNextChar = i + step < chars.size
                if (hasNextChar) {
                    //                boolean breakLine = needBreakLine(i, mLineCharsCount, curLine);
                    val nextCharBreakLine = i + 1 > mLineBreakIndex!![curLine]
                    if (nextCharBreakLine && curLine + 1 < mLineWidths!!.size) {
                        // new line
                        curLine++
                        curLineX -= mLineWidths!![curLine] * lineSpacingMultiplier + lineSpacingExtra
                        curX = curLineX
                        curY = paddingTop.toFloat()
                    } else {
                        // move to next char
                        curY += if (needRotate) {
                            paint.measureText(chars, i, step)
                        } else {
                            (fontMetricsInt.descent - fontMetricsInt.ascent).toFloat()
                        }
                    }
                }
                i += step
            }

            canvas.restore()
        }
    }

    // This method is copied from moai.ik.helper.CharacterHelper.isCJKCharacter(char input)
    private fun isCJKCharacter(input: Int): Boolean {
        val ub = Character.UnicodeBlock.of(input)

        return (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                //全角数字字符和日韩字符
                || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                //韩文字符集
                || ub === Character.UnicodeBlock.HANGUL_SYLLABLES
                || ub === Character.UnicodeBlock.HANGUL_JAMO
                || ub === Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                //日文字符集
                || ub === Character.UnicodeBlock.HIRAGANA //平假名

                || ub === Character.UnicodeBlock.KATAKANA //片假名

                || ub === Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS)
        //其他的CJK标点符号，可以不做处理
        //|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        //|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
    }

}