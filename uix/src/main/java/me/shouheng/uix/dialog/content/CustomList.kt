package me.shouheng.uix.dialog.content

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import me.shouheng.uix.R
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.rv.EmptySupportRecyclerView
import me.shouheng.uix.rv.EmptyView
import me.shouheng.uix.rv.IEmptyView

/**
 * 支持自定义的列表
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-21 13:59
 */
class CustomList private constructor(): IDialogContent {

    private var emptyView: IEmptyView? = null
    private var adapter: RecyclerView.Adapter<*>? = null

    private lateinit var dialog: BeautyDialog

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_list_custom, null)
        val rv = layout.findViewById<EmptySupportRecyclerView>(R.id.rv)
        val container = layout.findViewById<ViewGroup>(R.id.fl_container)
        if (emptyView != null) {
            container.addView(emptyView!!.getView(), ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
            rv.setEmptyView(emptyView!!)
        }
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(ctx)
        return layout
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    fun getDialog(): BeautyDialog {
        return dialog
    }

    fun showLoading() {
        emptyView?.show()
        emptyView?.showLoading()
    }

    fun showEmpty() {
        emptyView?.show()
        emptyView?.showEmpty()
    }

    fun hideEmptyView() {
        emptyView?.hide()
    }

    class Builder (context: Context) {
        private var emptyView: IEmptyView? = EmptyView.Builder(context).build()
        private var adapter: RecyclerView.Adapter<*>? = null

        fun setEmptyView(emptyView: IEmptyView): Builder {
            this.emptyView = emptyView
            return this
        }

        fun setAdapter(adapter: RecyclerView.Adapter<*>):Builder {
            this.adapter = adapter
            return this
        }

        fun build(): CustomList {
            val customList = CustomList()
            customList.adapter = adapter
            customList.emptyView = emptyView
            return customList
        }
    }
}