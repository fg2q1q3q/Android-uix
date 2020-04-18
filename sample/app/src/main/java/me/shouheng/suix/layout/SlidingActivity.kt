package me.shouheng.suix.layout

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivitySlidingBinding

@ActivityConfiguration(layoutResId = R.layout.activity_sliding)
class SlidingActivity : CommonActivity<ActivitySlidingBinding, EmptyViewModel>() {
    override fun doCreateView(savedInstanceState: Bundle?) {
    }
}

