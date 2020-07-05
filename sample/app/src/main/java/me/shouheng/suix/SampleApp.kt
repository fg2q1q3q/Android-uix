package me.shouheng.suix

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Process
import android.support.v4.content.ContextCompat
import me.shouheng.mvvm.MVVMs
import me.shouheng.mvvm.comn.ContainerActivity
import me.shouheng.uix.common.UIX
import me.shouheng.uix.common.anno.UIXConfiguration
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UIXViewUtils
import me.shouheng.uix.pages.CrashReportActivity
import me.shouheng.uix.pages.web.WebviewFragment
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.L
import kotlin.system.exitProcess

const val COMMAND_LAUNCH_WEBVIEW = 0x00001

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:44
 */
@UIXConfiguration
class SampleApp: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MVVMs.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MVVMs.onCreate(this)
        UIX.init(this)
        customUIX()
        customCrash()
        customContainer()
    }

    private fun customCrash() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            CrashHelper.init(this, "") { crashInfo, e ->
                L.e(crashInfo)
                CrashReportActivity.Companion.Builder(this)
                        .setTitle(crashInfo)
                        .setImage(R.drawable.uix_crash_error_image)
                        .setRestartActivity(MainActivity::class.java)
                        .setTitle("发生了崩溃，抱歉！")
                        .setMessage(crashInfo)
                        .setTitleStyle(TextStyleBean().apply {
                            textColor = Color.BLACK
                            textSize = 20f
                            typeFace = Typeface.BOLD
                        })
                        .setButtonStyle(TextStyleBean().apply {
                            textSize = 18f
                            textColor = Color.WHITE
                        })
                        .setContent("错误的提示信息等等……")
                        .launch()
                Process.killProcess(Process.myPid())
                exitProcess(1)
            }
        }
    }

    private fun customContainer() {
        ContainerActivity.registerCommandHandler { activity, command, containerLayoutId, _ ->
            when(command) {
                COMMAND_LAUNCH_WEBVIEW -> {
                    val fragment = WebviewFragment.Builder()
                            .setDarkTheme(true)
                            .setIndicatorColor(Color.BLUE)
                            .setUrl("https://weibo.com/5401152113/profile?rightmod=1&wvr=6&mod=personinfo")
                            .build()
                    activity.supportFragmentManager.beginTransaction().replace(containerLayoutId, fragment).commit()
                }
            }
        }
    }

    private fun customUIX() {
        NormalButton.GlobalConfig.textColor = Color.WHITE
        NormalButton.GlobalConfig.normalColor = ResUtils.getColor(R.color.green)
        NormalButton.GlobalConfig.cornerRadius = UIXViewUtils.dp2px(5f)
    }
}