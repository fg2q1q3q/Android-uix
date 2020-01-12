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
    var mTotalSecond = 60
    /** 当前秒数  */
    private var mCurrentSecond: Int = 0
    /** 没有进入倒计时状态时的提示文字，比如："发送"  */
    var mTips: CharSequence? = null
        set(value) {
            field = value
            text = mTips
        }
    private var reset = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 重置倒计时控件
     */
    fun reset() {
        mCurrentSecond = mTotalSecond
        text = mTips
        isEnabled = true
        reset = true
    }

    /**
     * 开始倒计时，开始之后将无法再次点击
     */
    fun start() {
        isEnabled = false
        post(this)
    }

    override fun onDetachedFromWindow() {
        // 移除延迟任务，避免内存泄露
        removeCallbacks(this)
        super.onDetachedFromWindow()
    }

    @SuppressLint("SetTextI18n")
    override fun run() {
        if (mCurrentSecond == 0 || reset) {
            text = mTips
            isEnabled = true
            reset = false
            mCurrentSecond = mTotalSecond
            return
        }
        text = "${mCurrentSecond--} $TIME_UNIT"
        postDelayed(this, 1000)
    }

    companion object {
        /** 秒数单位文本  */
        private const val TIME_UNIT = "S"
    }
}