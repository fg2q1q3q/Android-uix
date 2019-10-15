package me.shouheng.uix.text

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * 倒计时控件
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-04 11:50
 */
class CountdownView : AppCompatTextView, Runnable {

    /** 倒计时秒数  */
    private var mTotalSecond = 60
    /** 当前秒数  */
    private var mCurrentSecond: Int = 0
    /** 记录原有的文本  */
    private var mRecordText: CharSequence? = null
    /** 标记是否重置了倒计控件  */
    private var mFlag: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 设置倒计时总秒数
     */
    fun setTotalTime(totalTime: Int) {
        this.mTotalSecond = totalTime
    }

    /**
     * 重置倒计时控件
     */
    fun resetState() {
        mFlag = true
    }

    fun start() {
        mFlag = false
        isEnabled = false
        mCurrentSecond = mTotalSecond
        mRecordText = text
        post(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 设置点击的属性
        isClickable = true
    }

    override fun onDetachedFromWindow() {
        // 移除延迟任务，避免内存泄露
        removeCallbacks(this)
        super.onDetachedFromWindow()
    }

    override fun performClick(): Boolean {
        val click = super.performClick()
        mRecordText = text
        isEnabled = false
        mCurrentSecond = mTotalSecond
        post(this)
        return click
    }

    @SuppressLint("SetTextI18n")
    override fun run() {
        if (mCurrentSecond == 0 || mFlag) {
            text = mRecordText
            isEnabled = true
            mFlag = false
        } else {
            mCurrentSecond--
            text = "$mCurrentSecond $TIME_UNIT"
            postDelayed(this, 1000)
        }
    }

    companion object {
        /** 秒数单位文本  */
        private const val TIME_UNIT = "S"
    }
}