package me.shouheng.suix.layout

import android.os.Bundle
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivitySlidingBinding
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

class SlidingActivity : CommonActivity<EmptyViewModel, ActivitySlidingBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_sliding

    override fun doCreateView(savedInstanceState: Bundle?) {
    }
}

