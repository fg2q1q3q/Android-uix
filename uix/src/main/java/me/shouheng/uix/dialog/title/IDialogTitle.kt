package me.shouheng.uix.dialog.title

import android.view.View
import me.shouheng.uix.dialog.BeautyDialog

/**
 * 对话框顶部的抽象接口
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:14
 */
interface IDialogTitle {

    /**
     * 获取控件
     */
    fun getView(): View

    /**
     * 传递 Dialog 给当前的控件，以便当前控件内部使用
     */
    fun setDialog(dialog: BeautyDialog)
}