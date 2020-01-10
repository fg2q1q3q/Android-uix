package me.shouheng.uix.listener

import android.view.View
import me.shouheng.uix.UIXConfig

/**
 * Created by WngShhng on 2018/10/25.
 */
abstract class NoDoubleClickListener : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > UIXConfig.minClickDelayTime) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    protected abstract fun onNoDoubleClick(v: View)
}
