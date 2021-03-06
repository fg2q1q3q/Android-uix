package me.shouheng.uix.common.utils

import android.util.Log

/**
 * UIX Log 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object ULog {

    var debug: Boolean = true

    fun d(s: String) {
        if (debug) {
            Log.d("UIX", s)
        }
    }

    fun d(o: Any) {
        if (debug) {
            Log.d("UIX", "" + o)
        }
    }

    fun e(s: String) {
        if (debug) {
            Log.e("UIX", s)
        }
    }

    fun e(o: Any) {
        if (debug) {
            Log.e("UIX", "" + o)
        }
    }
}
