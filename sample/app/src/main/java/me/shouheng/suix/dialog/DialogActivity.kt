package me.shouheng.suix.dialog

import android.os.Bundle
import android.view.View
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityDialogBinding
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.Constants
import me.shouheng.uix.dialog.content.IDialogContent
import me.shouheng.uix.dialog.title.IDialogTitle

/**
 * dialog
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 15:21
 */
@ActivityConfiguration(layoutResId = R.layout.activity_dialog)
class DialogActivity : CommonActivity<ActivityDialogBinding, EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnNormal.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogStyle(Constants.STYLE_WRAP)
                    .setDialogTitle(SampleTitle(this@DialogActivity))
                    .setDialogContent(SampleContent(this@DialogActivity))
                    .setDialogBottom(SampleFooter(this@DialogActivity))
                    .build().show(supportFragmentManager, "normal")
        }
        binding.btnNormalTop.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogTitle(SampleTitle(this@DialogActivity))
                    .setDialogContent(SampleContent(this@DialogActivity))
                    .setDialogBottom(SampleFooter(this@DialogActivity))
                    .setDialogPosition(Constants.POS_TOP)
                    .build().show(supportFragmentManager, "normal_top")
        }
        binding.btnNormalBottom.setOnClickListener {
            BeautyDialog.Builder()
                    .setDarkDialog(true)
                    .setDialogTitle(SampleTitle(this@DialogActivity))
                    .setDialogContent(SampleContent(this@DialogActivity))
                    .setDialogBottom(SampleFooter(this@DialogActivity))
                    .setDialogPosition(Constants.POS_BOTTOM)
                    .build().show(supportFragmentManager, "normal_bottom")
        }
    }
}