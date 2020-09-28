package me.shouheng.uix.common.listener

import android.view.View

/**
 * Avoid to click twice in short time.
 *
 * Created by WngShhng on 2018/10/25.
 */
abstract class NoDoubleClickListener : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    protected abstract fun onNoDoubleClick(v: View)

    companion object {
        var MIN_CLICK_DELAY_TIME                       = 500L
    }
}

/**
 * Add function to View to avoid click twice
 */
fun View.onDebouncedClick(click: (view: View) -> Unit) {
    setOnClickListener(object : NoDoubleClickListener() {
        override fun onNoDoubleClick(v: View) {
            click(v)
        }
    })
}