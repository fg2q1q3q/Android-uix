package me.shouheng.uix.rv.listener

import android.support.v7.widget.RecyclerView
import android.util.Log
import me.shouheng.uix.UIXConfig

/**
 * 自定义的 Recycler 滚动监听，用来处理 FAB 等的隐藏和显示事件
 */
abstract class CustomRecyclerScrollViewListener : RecyclerView.OnScrollListener() {

    private var scrollDist = 0

    private var isVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isVisible && scrollDist > UIXConfig.rvScrollJudgeHeight) {
            Log.d("CRVScrollViewListener", "Hide $scrollDist")
            hide()
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -UIXConfig.rvScrollJudgeHeight) {
            Log.d("CRVScrollViewListener", "Show $scrollDist")
            show()
            scrollDist = 0
            isVisible = true
        }
        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            Log.d("CRVScrollViewListener", "Add Up $scrollDist")
            scrollDist += dy
        }
    }

    abstract fun show()

    abstract fun hide()
}
