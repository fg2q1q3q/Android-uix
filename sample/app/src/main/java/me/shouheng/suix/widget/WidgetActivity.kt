package me.shouheng.suix.widget

import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityWidgetsBinding

/**
 * 控件
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-26 14:51
 */
@ActivityConfiguration(layoutResId = R.layout.activity_widgets)
class WidgetActivity : CommonActivity<ActivityWidgetsBinding, EmptyViewModel>() {
    override fun doCreateView(savedInstanceState: Bundle?) {

    }
}