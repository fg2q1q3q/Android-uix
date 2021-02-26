package me.shouheng.uix.widget.dialog.footer

import android.content.Context
import android.view.View
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.title.IDialogTitle

/**
 * Dialog footer interace
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:15
 */
interface IDialogFooter {

    /** Get the dialog footer view */
    fun getView(ctx: Context): View

    /** Override to get the dialog */
    fun setDialog(dialog: BeautyDialog) { }

    /** Override to get the dialog title */
    fun setDialogTitle(dialogTitle: IDialogTitle?) { }

    /** Override to get the dialog content */
    fun setDialogContent(dialogContent: IDialogContent?) { }
}
