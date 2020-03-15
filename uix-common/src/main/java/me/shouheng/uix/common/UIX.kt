package me.shouheng.uix.common

import android.app.Application
import me.shouheng.uix.common.utils.UIXLogUtils

/**
 * 用来初始化 UI 库，主要用来初始化 application 对象
 *
 * @sample
 *
 * ```kotlin
 * class SampleApp: Application() {
 *
 *     override fun onCreate() {
 *         super.onCreate()
 *         UIX.init(this)
 *     }
 * }
 * ```
 *
 * @see UIXConfig 对框架整体进行个性化配置
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 12:01
 */
object UIX {

    private var app: Application? = null

//    private val resolvers: MutableList<UIXConfigurationResolver> = mutableListOf()

//    init {
//        val loader = ServiceLoader.load<UIXConfigurationResolver>(UIXConfigurationResolver::class.java!!)
//        val iterator = loader.iterator()
//        while (iterator.hasNext()) {
//           resolvers.add(iterator.next())
//        }
//    }

    fun getApp(): Application {
        checkNotNull(app) { "Sorry, you must call UIX#init() method at first!" }
        return app!!
    }

    fun init(app: Application) {
        UIX.app = app
//        if (app.javaClass.isAnnotationPresent(UIXConfiguration::class.java)) {
//            val configuration = app.javaClass.getAnnotation(UIXConfiguration::class.java)
//            resolvers.forEach { it.onGetConfiguration(configuration!!) }
//        } else {
//            throw IllegalStateException("Your application should use @UIXConfiguration")
//        }
    }

    fun setDebug(debug: Boolean) {
        UIXLogUtils.debug = debug
    }
}
