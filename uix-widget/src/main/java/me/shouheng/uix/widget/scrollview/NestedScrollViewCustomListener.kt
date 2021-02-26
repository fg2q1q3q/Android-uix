package me.shouheng.uix.widget.scrollview

import android.support.v4.widget.NestedScrollView

abstract class NestedScrollViewCustomListener : NestedScrollView.OnScrollChangeListener {

    var scrollDist = 0
    var isVisible = true

    override fun onScrollChange(p0: NestedScrollView?, p1: Int, p2: Int, p3: Int, p4: Int) {
        val dy = p2 - p4
        if (isVisible && scrollDist > rvScrollJudgeHeight) {
            hide()
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -rvScrollJudgeHeight) {
            show()
            scrollDist = 0
            isVisible = true
        }
        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            scrollDist += dy
        }
    }

    abstract fun hide()

    abstract fun show()

    companion object {
        /** 判定为上下滚动的距离 */
        var rvScrollJudgeHeight                     = 20
    }
}