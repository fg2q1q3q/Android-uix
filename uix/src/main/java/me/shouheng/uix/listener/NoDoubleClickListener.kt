package me.shouheng.uix.listener

import android.view.View
import me.shouheng.uix.config.Config.MIN_CLICK_DELAY_TIME

/**
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
}
