package me.shouheng.uix.items

import android.widget.ImageView
import me.shouheng.uix.button.SwitchButton

/**
 * 图片加载
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-26 21:22
 */
interface Tools {

    /**
     * 加载图片
     */
    fun load(imageView: ImageView)
}

/**
 * Switch 按钮状态变化时的回调
 */
interface OnCheckStateChangeListener {

    fun onStateChange(switchButton: SwitchButton, checked: Boolean)
}