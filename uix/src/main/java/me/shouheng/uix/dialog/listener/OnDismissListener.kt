package me.shouheng.uix.dialog.listener

import me.shouheng.uix.dialog.BeautyDialog

/**
 * 对话框消息的回调
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:35
 */
interface OnDismissListener {

    /**
     * 回调方法
     */
    fun onOnDismiss(dialog: BeautyDialog)
}