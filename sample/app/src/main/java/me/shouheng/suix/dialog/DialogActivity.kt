package me.shouheng.suix.dialog

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityDialogBinding

/**
 * dialog
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 15:21
 */
@ActivityConfiguration(layoutResId = R.layout.activity_dialog)
class DialogActivity : CommonActivity<ActivityDialogBinding, EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnNormal.setOnClickListener {  }
    }
}