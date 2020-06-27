package me.shouheng.uix.widget.rv.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * RV 滚动监听加载更多数据
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-12-28 23:01
 */
abstract class DataLoadScrollListener(private val lm: LinearLayoutManager)
    : RecyclerView.OnScrollListener() {

    /**
     * 当前是否正在处于加载状态
     */
    var loading: Boolean = false

    /**
     * 是水平的布局还是垂直的布局
     */
    var horizontal: Boolean = false

    init {
        horizontal = lm.orientation == LinearLayoutManager.HORIZONTAL
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItem = lm.findLastVisibleItemPosition()
        val totalItemCount = lm.itemCount
        if (lastVisibleItem + 1 == totalItemCount
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