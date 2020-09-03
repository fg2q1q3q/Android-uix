package me.shouheng.suix.pop

import android.os.Bundle
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityPopBinding
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * Pop window 示例
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-04-18 22:50
 */
class PopActivity : CommonActivity<EmptyViewModel, ActivityPopBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_pop

    override fun doCreateView(savedInstanceState: Bundle?) {}
}
