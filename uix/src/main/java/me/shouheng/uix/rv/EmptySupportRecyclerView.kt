package me.shouheng.uix.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class EmptySupportRecyclerView : RecyclerView {

    private var emptyView: View? = null

    private var iEmptyView: IEmptyView? = null

    private var emptyCount = 0

    private val observer = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            showEmptyView()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            showEmptyView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            showEmptyView()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private fun showEmptyView() {
        val adapter = adapter
        if (adapter != null && emptyView != null) {
            if (adapter.itemCount == emptyCount) {
                emptyView?.visibility = View.VISIBLE
                iEmptyView?.show()
            } else {
                emptyView?.visibility = View.GONE
                iEmptyView?.hide()
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer)
            observer.onChanged()
        }
    }

    /**
     * 设置列表为空时展示的控件，这里的 emptyCount 用来指定，
     * 当 adapter 中当数据为多少当时候表示列表为空（因如果为 adapter 设置了头部或底部当情况）
     */
    fun setEmptyView(emptyView: View, emptyCount: Int = 0) {
        this.emptyView = emptyView
        this.emptyCount = emptyCount
    }

    fun setEmptyView(iEmptyView: IEmptyView, emptyCount: Int = 0) {
        this.iEmptyView = iEmptyView
        this.emptyCount = emptyCount
    }
}
