package me.shouheng.suix.widget

import android.os.Bundle
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityLayout2Binding
import me.shouheng.suix.layout.SlidingActivity
import me.shouheng.utils.ktx.start
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

class LayoutActivity2 : CommonActivity<EmptyViewModel, ActivityLayout2Binding>() {

    override fun getLayoutResId(): Int = R.layout.activity_layout2

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnSpl.setOnClickListener {
            start(SlidingActivity::class.java)
        }
    }
}

