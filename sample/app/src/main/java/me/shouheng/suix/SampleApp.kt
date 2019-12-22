package me.shouheng.suix

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import me.shouheng.mvvm.MVVMs
import me.shouheng.uix.UIX
import me.shouheng.uix.page.CrashActivity
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.LogUtils
import kotlin.system.exitProcess
import android.os.Process

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:44
 */
class SampleApp: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MVVMs.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MVVMs.onCreate(this)
        UIX.init(this)
        customCrash()
    }

    private fun customCrash() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            CrashHelper.init(this, "") { crashInfo, e ->
                LogUtils.e(crashInfo)
                LogUtils.e(e)
                ActivityUtils.open(CrashActivity::class.java)
                        .put(CrashActivity.EXTRA_KEY_CRASH_INFO, crashInfo)
                        .put(CrashActivity.EXTRA_KEY_CRASH_IMAGE, R.drawable.uix_crash_error_image)
                        .put(CrashActivity.EXTRA_KEY_RESTART_ACTIVITY, MainActivity::class.java)
                        .put(CrashActivity.EXTRA_KEY_CRASH_TIPS, "发生了崩溃，抱歉！")
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .launch(this)
                Process.killProcess(Process.myPid())
                exitProcess(1)
            }
        }
    }
}