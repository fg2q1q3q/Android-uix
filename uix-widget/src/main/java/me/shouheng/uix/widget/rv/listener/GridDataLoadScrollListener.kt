package me.shouheng.uix.widget.rv.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-08-06 10:29
 */
abstract class StaggerGridDataLoadScrollListener(private val sglm: StaggeredGridLayoutManager)
    : RecyclerView.OnScrollListener() {

    /** 当前是否正在处于加载状态 */
    var loading: Boolean = false

    /** 是水平的布局还是垂直的布局 */
    var horizontal: Boolean = false

    init {
        horizontal = sglm.orientation == LinearLayoutManager.HORIZONTAL
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItems = sglm.findLastVisibleItemPositions(null)
        val totalItemCount = sglm.itemCount
        if ( lastVisibleItems.any { it + 1 == totalItemCount }
            && (horizontal && dx > 0 || !horizontal && dy > 0)
            && !loading) {
            recyclerView.post {
                loadMore()
                loading = true
            }
        }
    }

    /**
     * 加载更多数据，加载完毕之后记得手动将 loading 置为 false
     */
    abstract fun loadMore()
}