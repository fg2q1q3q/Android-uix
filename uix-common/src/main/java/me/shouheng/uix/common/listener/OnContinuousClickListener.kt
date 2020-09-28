package me.shouheng.uix.common.listener

import android.view.View

/**
 * 连续点击事件监听
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-04-18 13:32
 */
abstract class OnContinuousClickListener : View.OnClickListener {

    private var lastClickTime: Long = 0

    private var continuousClickCount = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > /*将点击的次数清零的时间*/2000) {
            continuousClickCount = 0
        } else {
            continuousClickCount++
        }
        onContinuousClick(continuousClickCount)
        lastClickTime = currentTime
    }

    protected abstract fun onContinuousClick(continuousClickCount: Int)
}

/**
 * Add function to View for continuous click.
 */
fun View.onContinuousClick(click: (continuousClickCount: Int) -> Unit) {
    setOnClickListener(object : OnContinuousClickListener() {
        override fun onContinuousClick(continuousClickCount: Int) {
            click(continuousClickCount)
        }
    })
}