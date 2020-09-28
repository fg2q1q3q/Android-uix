package me.shouheng.uix.widget.dialog.content

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.dialog.BeautyDialog

class SimpleGrid<T> private constructor(private val itemLayout:Int,
                                        private val converter: ViewHolderConverter<T>): IDialogContent {
    private lateinit var dialog: BeautyDialog
    private var onItemClickListener: OnItemClickListener<T>? = null
    private var list: List<T> = emptyList()
    private var spanCount = 4

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_list_simple, null)
        val rv = layout.findViewById<RecyclerView>(R.id.rv)
        val adapter = Adapter(itemLayout, converter, list)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(ctx, spanCount)
        adapter.setOnItemClickListener { _, _, pos -> onItemClickListener?.onItemClick(dialog, adapter.data[pos]) }
        return layout
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    class Adapter<ITEM>(val layout: Int,
                        private val converter: ViewHolderConverter<ITEM>,
                        val list: List<ITEM>): BaseQuickAdapter<ITEM, BaseViewHolder>(layout, list) {
        override fun convert(helper: BaseViewHolder, item: ITEM) {
            converter.convert(helper, item)
        }
    }

    interface ViewHolderConverter<ITEM> {
        fun convert(helper: BaseViewHolder, item: ITEM)
    }

    interface OnItemClickListener<ITEM> {

        fun onItemClick(dialog: BeautyDialog, item: ITEM)
    }

    class Builder<T> constructor(@LayoutRes val itemLayout:Int,
                                 private val converter: ViewHolderConverter<T>) {
        private var list: List<T> = emptyList()
        private var onItemClickListener: OnItemClickListener<T>? = null
        private var spanCount = 4

        fun setSpanCount(spanCount: Int): Builder<T> {
            this.spanCount = spanCount
            return this
        }

        fun setList(list: List<T>): Builder<T> {
            this.list = list
            return this
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>): Builder<T> {
            this.onItemClickListener = onItemClickListener
            return this
        }

        fun build(): SimpleGrid<T> {
            val simpleList = SimpleGrid(itemLayout, converter)
            simpleList.list = list
            simpleList.spanCount = spanCount
            simpleList.onItemClickListener = onItemClickListener
            return simpleList
        }
    }
}