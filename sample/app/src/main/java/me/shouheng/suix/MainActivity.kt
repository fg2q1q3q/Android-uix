package me.shouheng.suix

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.databinding.ActivityMainBinding

/**
 * Main
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-12 15:40
 */
@ActivityConfiguration(layoutResId = R.layout.activity_main)
class MainActivity : CommonActivity<ActivityMainBinding, EmptyViewModel>() {
    override fun doCreateView(savedInstanceState: Bundle?) {

    }
}