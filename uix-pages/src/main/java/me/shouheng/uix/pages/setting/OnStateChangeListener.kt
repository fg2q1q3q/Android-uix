package me.shouheng.uix.pages.setting

import me.shouheng.uix.widget.button.SwitchButton

/**
 * Switch 按钮状态变化时的回调
 */
interface OnStateChangeListener {

    fun onStateChange(switchButton: SwitchButton, checked: Boolean)
}