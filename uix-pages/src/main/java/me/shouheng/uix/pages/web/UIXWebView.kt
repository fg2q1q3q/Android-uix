package me.shouheng.uix.pages.web

import android.content.Context
import android.util.AttributeSet
import com.just.agentweb.LollipopFixedWebView

/**
 * @author Jeff
 * @time 2020/11/21 15:12
 */
class UIXWebView : LollipopFixedWebView {

    var callback: OnScrollChangeCallback? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, privateBrowsing: Boolean) : super(context, attrs, defStyleAttr, privateBrowsing) {}

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        callback?.onScrollChanged(l, t, oldl, oldt)
    }
}