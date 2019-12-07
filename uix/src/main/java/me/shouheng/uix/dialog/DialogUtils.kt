package me.shouheng.uix.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import me.shouheng.uix.R
import me.shouheng.uix.config.EmptyLoadingStyle
import me.shouheng.uix.utils.UIXUtils

/**
 * 提供一些便捷的对话框工具方法
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 18:16
 */
object DialogUtils {

    /**
     * 显示加载对话框
     */
    fun showLoading(context: Context,
                    @StringRes msgResId: Int,
                    isAnimation: Boolean = true,
                    cancelable: Boolean = false,
                    imageId: Int = 0): Dialog {
        return showLoading(context, UIXUtils.getString(msgResId), cancelable, isAnimation, imageId)
    }

    /**
     * 显示加载对话框
     */
    fun showLoading(context: Context,
                    msg: String,
                    cancelable: Boolean = false,
                    isAnimation: Boolean = true,
                    imageId: Int = 0,
                    @EmptyLoadingStyle loadingStyle: Int = EmptyLoadingStyle.STYLE_ANDROID): Dialog {
        val v = LayoutInflater.from(context).inflate(R.layout.uix_dialog_loading, null)
        val layout = v.findViewById(R.id.loading_dialog_view) as LinearLayout
        val spaceshipImage = v.findViewById(R.id.img) as ImageView
        val pb = v.findViewById<ProgressBar>(R.id.pb)
        val tipTextView = v.findViewById(R.id.tipTextView) as TextView
        val isAndroidStyle = loadingStyle == EmptyLoadingStyle.STYLE_ANDROID
        pb.visibility = if (isAndroidStyle) View.VISIBLE else View.GONE
        spaceshipImage.visibility = if (!isAndroidStyle) View.VISIBLE else View.GONE

        if (imageId != 0) spaceshipImage.setImageResource(imageId)
        if (isAnimation) {
            val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.uix_dialog_loading)
            spaceshipImage.startAnimation(hyperspaceJumpAnimation)
        }
        tipTextView.text = msg
        tipTextView.visibility = if (TextUtils.isEmpty(msg)) View.GONE else View.VISIBLE

        val loadingDialog = Dialog(context, if (cancelable) R.style.Dialog_Loading_Cancelable else R.style.Dialog_Loading)
        loadingDialog.setCancelable(cancelable)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        loadingDialog.setContentView(layout, params)
        return loadingDialog
    }

    /**
     * 隐藏对话框
     */
    fun hideDialog(dialog: Dialog?) {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}