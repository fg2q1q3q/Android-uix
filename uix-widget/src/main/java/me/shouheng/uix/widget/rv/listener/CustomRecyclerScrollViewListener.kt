package me.shouheng.uix.widget.rv.listener

import android.support.v7.widget.RecyclerView
import me.shouheng.uix.common.utils.ULog

/**
 * 自定义的 Recycler 滚动监听，用来处理 FAB 等的隐藏和显示事件
 */
abstract class CustomRecyclerScrollViewListener : RecyclerView.OnScrollListener() {

    private var scrollDist = 0

    private var isVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (isVisible && scrollDist > rvScrollJudgeHeight) {
            ULog.d("CRVScrollViewListener Hide")
            hide()
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -rvScrollJudgeHeight) {
            ULog.d("CRVScrollViewListener Show")
            show()
            scrollDist = 0
            isVisible = true
        }
        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            ULog.d("CRVScrollViewListener Add Up")
            scrollDist += dy
        }
    }

    abstract fun show()

    abstract fun hide()
    
    companion object {
        /**
         * RecyclerView 判定为上下滚动的距离
         */
        var rvScrollJudgeHeight                     = 20
    }
}
