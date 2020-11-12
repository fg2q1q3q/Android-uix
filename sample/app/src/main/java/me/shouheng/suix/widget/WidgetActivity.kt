package me.shouheng.suix.widget

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityWidgetsBinding
import me.shouheng.uix.common.anno.LoadingButtonState
import me.shouheng.uix.common.glide.CornersTransformation
import me.shouheng.uix.common.listener.onDebouncedClick
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ktx.drawableOf
import me.shouheng.utils.ktx.logd
import me.shouheng.utils.ktx.tint
import me.shouheng.utils.ui.ImageUtils
import me.shouheng.utils.ui.ViewUtils
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * 控件
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-26 14:51
 */
class WidgetActivity : CommonActivity<EmptyViewModel, ActivityWidgetsBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_widgets

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.etPsd.setVisibleDrawable(drawableOf(R.drawable.uix_eye_open_48).tint(Color.BLUE))
        binding.etPsd.setInvisibleDrawable(drawableOf(R.drawable.uix_eye_close_48).tint(Color.GREEN))
        binding.btn.onDebouncedClick {
            binding.btn.setState(LoadingButtonState.LOADING_STATE_LOADING)
            binding.btn.setText("加载中，长按试一下……")
        }
        binding.btn.onDebouncedClick {
            binding.btn.setText("已禁用")
            binding.btn.setState(LoadingButtonState.LOADING_STATE_DISABLE)
            true
        }
        binding.cv.mTips = "发送验证码"
        binding.cv.mTotalSecond = 10
        binding.cv.onDebouncedClick {
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
        val transformation = CornersTransformation(ViewUtils.dp2px(8f), 0)
        Glide.with(this)
                .load(R.drawable.ic_bg_sample)
                .apply(RequestOptions.centerCropTransform().transform(transformation))
                .into(binding.card.bg)
        try {
            binding.card.viewColor.setBackgroundColor(Color.parseColor("#D81B60"))
            val colors = intArrayOf(Color.parseColor("#D81B60"), Color.TRANSPARENT)
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
            binding.card.viewGradient.background = gradientDrawable
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.fab.onDebouncedClick {
            logd("Fab Clicked")
            binding.fab.isExpanded = !binding.fab.isEnabled
        }
    }
}