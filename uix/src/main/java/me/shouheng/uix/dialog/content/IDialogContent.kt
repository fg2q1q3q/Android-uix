package me.shouheng.uix.dialog.content

import android.content.Context
import android.view.View
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.footer.IDialogFooter
import me.shouheng.uix.dialog.title.IDialogTitle

/**
 * 对话框内容的抽象接口
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:14
 */
interface IDialogContent {

    /**
     * 获取控件
     */
    fun getView(ctx: Context): View

    /**
     * 传递 Dialog 给当前的控件，以便当前控件内部使用
     */
    fun setDialog(dialog: BeautyDialog) { }

    /**
     * 设置对话框的标题
     */
    fun setDialogTitle(dialogTitle: IDialogTitle?) { }

    /**
     * 设置对话框底部
     */
    fun setDialogFooter(dialogFooter: IDialogFooter?) { }
}