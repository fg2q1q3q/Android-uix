package me.shouheng.uix.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.uix_dialog_content_edit_simple.view.*

class EmptySupportRecyclerView : RecyclerView {

    private var emptyView: IEmptyView? = null

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
            if (adapter.itemCount == 0) {
                emptyView?.show()
            } else {
                emptyView?.hide()
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

    fun <V> setEmptyView(emptyView: V) where V: IEmptyView {
        this.emptyView = emptyView
    }
}
