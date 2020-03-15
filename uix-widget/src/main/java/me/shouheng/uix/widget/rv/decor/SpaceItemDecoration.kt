package me.shouheng.uix.widget.rv.decor

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceItemDecoration : RecyclerView.ItemDecoration {

    private var leftDp: Int = 0
    private var upDp: Int = 0
    private var rightDp: Int = 0
    private var downDp: Int = 0

    constructor(dp: Int) {
        this.leftDp = dp
        this.upDp = dp
        this.rightDp = dp
        this.downDp = dp
    }

    constructor(leftDp: Int, upDp: Int, rightDp: Int, downDp: Int) {
        this.leftDp = leftDp
        this.upDp = upDp
        this.rightDp = rightDp
        this.downDp = downDp
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(leftDp, upDp, rightDp, downDp)
    }
}
