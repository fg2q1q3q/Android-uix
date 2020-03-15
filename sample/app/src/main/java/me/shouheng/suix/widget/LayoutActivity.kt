package me.shouheng.suix.widget

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityLayoutBinding

@ActivityConfiguration(layoutResId = R.layout.activity_layout)
class LayoutActivity : CommonActivity<ActivityLayoutBinding, EmptyViewModel>() {
    override fun doCreateView(savedInstanceState: Bundle?) {

    }
}

