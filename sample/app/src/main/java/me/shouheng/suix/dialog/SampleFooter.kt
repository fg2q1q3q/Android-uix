package me.shouheng.suix.dialog

import android.content.Context
import android.view.View
import me.shouheng.suix.R
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.footer.IDialogFooter
import me.shouheng.uix.dialog.title.IDialogTitle

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 17:51
 */
class SampleFooter(private val ctx: Context?) : IDialogFooter {

    override fun getView(): View = View.inflate(ctx, R.layout.layout_dialog_bottom_sample, null)

    override fun setDialog(dialog: BeautyDialog) {}

}