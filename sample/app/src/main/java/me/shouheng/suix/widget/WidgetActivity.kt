package me.shouheng.suix.widget

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityWidgetsBinding
import me.shouheng.uix.common.anno.LoadingButtonState
import me.shouheng.uix.widget.button.NormalButton
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

    override fun onCreate(savedInstanceState: Bundle?) {
        // 全局设置
        NormalButton.GlobalConfig.disableColor = Color.GRAY
        NormalButton.GlobalConfig.textDisableColor = Color.WHITE
        NormalButton.GlobalConfig.normalColor = ResUtils.getAttrColor(this, R.attr.colorPrimary)
        NormalButton.GlobalConfig.selectedColor = ResUtils.getAttrColor(this, R.attr.colorPrimaryDark)
        super.onCreate(savedInstanceState)
    }

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.etPsd.setVisibleDrawable(ImageUtils.tintDrawable(ResUtils.getDrawable(R.drawable.uix_eye_open_48), Color.BLUE))
        binding.etPsd.setInvisibleDrawable(ImageUtils.tintDrawable(ResUtils.getDrawable(R.drawable.uix_eye_close_48), Color.GREEN))
        binding.btn.setOnClickListener {
            binding.btn.setState(LoadingButtonState.LOADING_STATE_LOADING)
            binding.btn.setText("加载中，长按试一下……")
        }
        binding.btn.setOnLongClickListener {
            binding.btn.setText("已禁用")
            binding.btn.setState(LoadingButtonState.LOADING_STATE_DISABLE)
            true
        }
        binding.cv.mTips = "发送验证码"
        binding.cv.mTotalSecond = 10
        binding.cv.setOnClickListener {
            binding.cv.start()
            if (it.tag != null) {
                it.tag = null
                Handler().postDelayed({
                    binding.cv.reset()
                    toast("触发倒计时控件重置")
                }, 3000)
            } else {
                it.tag = ""
            }
        }
    }
}