package me.shouheng.suix.dialog

import android.content.Context
import android.view.View
import me.shouheng.suix.R
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.content.IDialogContent
import me.shouheng.uix.dialog.footer.IDialogFooter
import me.shouheng.uix.dialog.title.IDialogTitle

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 17:51
 */
class SampleContent : IDialogContent {

    override fun getView(ctx: Context): View = View.inflate(ctx, R.layout.layout_dialog_content_sample, null)

}