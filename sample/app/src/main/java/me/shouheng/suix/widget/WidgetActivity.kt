package me.shouheng.suix.widget

import android.graphics.Color
import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityWidgetsBinding
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ui.ImageUtils

/**
 * 控件
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-26 14:51
 */
@ActivityConfiguration(layoutResId = R.layout.activity_widgets)
class WidgetActivity : CommonActivity<ActivityWidgetsBinding, EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.etPsd.setVisibleDrawable(ImageUtils.tintDrawable(ResUtils.getDrawable(R.drawable.uix_eye_open_48), Color.BLUE))
        binding.etPsd.setInvisibleDrawable(ImageUtils.tintDrawable(ResUtils.getDrawable(R.drawable.uix_eye_close_48), Color.GREEN))
    }
}