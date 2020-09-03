package me.shouheng.suix.widget

import android.os.Bundle
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityLayoutBinding
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

class LayoutActivity : CommonActivity<EmptyViewModel, ActivityLayoutBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_layout

    override fun doCreateView(savedInstanceState: Bundle?) {}
}

