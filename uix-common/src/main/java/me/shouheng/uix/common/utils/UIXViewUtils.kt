package me.shouheng.uix.common.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import me.shouheng.uix.common.UIX

/**
 * UIX View 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UIXViewUtils {

    private const val FLAG_IMMERSIVE = (View.SYSTEM_UI_FLAG_IMMERSIVE // 与SYSTEM_UI_FLAG_HIDE_NAVIGATION组合使用，没有设置的话在隐藏导航栏后将没有交互
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏虚拟按键(导航栏)
            or View.SYSTEM_UI_FLAG_FULLSCREEN) // Activity全屏显示，且状态栏被隐藏覆盖掉

    @TargetApi(17)
    private val FLAG_VISIBILITY_17 = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            or View.SYSTEM_UI_FLAG_FULLSCREEN)

    @TargetApi(19)
    private val FLAG_VISIBILITY_19 = (FLAG_VISIBILITY_17 // hide status bar
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_IMMERSIVE)


    fun dp2px(dpValue: Float): Int {
        val scale = UIX.getApp().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        val scale = UIX.getApp().resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = UIX.getApp().resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale = UIX.getApp().resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        val wm = UIX.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }

    fun getScreenHeight(): Int {
        val wm = UIX.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    fun getStatusBarHeight(): Int {
        val resId = UIX.getApp().resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) UIX.getApp().resources.getDimensionPixelOffset(resId) else 0
    }

    fun getRootView(context: Activity): View {
        return (context.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }

    fun getSystemVisibility(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FLAG_VISIBILITY_19
        } else {
            FLAG_VISIBILITY_17
        }
    }

    fun exit(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            addFlags(activity.window.decorView, FLAG_IMMERSIVE)
        }
    }

    private fun addFlags(decorView: View, flags: Int) {
        decorView.systemUiVisibility = decorView.systemUiVisibility or flags
    }

    fun enter(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            clearFlags(activity.window.decorView, FLAG_IMMERSIVE)
        }
    }

    private fun clearFlags(view: View, flags: Int) {
        view.systemUiVisibility = view.systemUiVisibility and flags.inv()
        // & ~flags 清除view.getSystemUiVisibility()中的flags
    }
}
