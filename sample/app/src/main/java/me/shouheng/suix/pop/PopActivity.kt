package me.shouheng.suix.pop

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityPopBinding

/**
 * Pop window 示例
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-04-18 22:50
 */
@ActivityConfiguration(layoutResId = R.layout.activity_pop)
class PopActivity : CommonActivity<ActivityPopBinding, EmptyViewModel>() {
    override fun doCreateView(savedInstanceState: Bundle?) {

    }
}
