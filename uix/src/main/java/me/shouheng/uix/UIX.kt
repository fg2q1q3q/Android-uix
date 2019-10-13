package me.shouheng.uix

import android.app.Application

/**
 * 用来初始化 UI 库，主要用来初始化 application 对象
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 12:01
 */
object UIX {

    private var app: Application? = null

    fun getApp(): Application {
        checkNotNull(app) { "Sorry, you must call UtilsApp#init() method at first!" }
        return app!!
    }

    fun init(app: Application) {
        UIX.app = app
    }
}