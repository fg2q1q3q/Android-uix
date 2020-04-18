package me.shouheng.suix.widget

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityLayout2Binding
import me.shouheng.suix.layout.SlidingActivity

@ActivityConfiguration(layoutResId = R.layout.activity_layout2)
class LayoutActivity2 : CommonActivity<ActivityLayout2Binding, EmptyViewModel>() {
    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnSpl.setOnClickListener {
            startActivity(SlidingActivity::class.java)
        }
    }
}

