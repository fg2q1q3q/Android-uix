package me.shouheng.uix.recyclerview

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * 自定义的 Recycler 滚动监听
 */
abstract class CustomRecyclerScrollViewListener : RecyclerView.OnScrollListener() {

    private var scrollDist = 0

    private var isVisible = true

    private val MINIMUM = 20f

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isVisible && scrollDist > MINIMUM) {
            Log.d("OskarSchindler", "Hide $scrollDist")
            hide()
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -MINIMUM) {
            Log.d("OskarSchindler", "Show $scrollDist")
            show()
            scrollDist = 0
            isVisible = true
        }
        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            Log.d("OskarSchindler", "Add Up $scrollDist")
            scrollDist += dy
        }
    }

    abstract fun show()

    abstract fun hide()
}
