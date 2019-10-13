package me.shouheng.suix

import android.app.Application
import android.content.Context
import me.shouheng.mvvm.MVVMs
import me.shouheng.uix.UIX

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
    }
}