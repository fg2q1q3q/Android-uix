package me.shouheng.uix.widget.dialog.content

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 列表类型的内容
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-15 19:06
 */
class SimpleList private constructor(): IDialogContent {

    private var list: List<Item> = emptyList()
    private var showIcon = true
    private var onItemClickListener: OnItemClickListener? = null
    private lateinit var dialog: BeautyDialog
    private var textStyle = GlobalConfig.textStyle

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_list_simple, null)
        val rv = layout.findViewById<RecyclerView>(R.id.rv)
        val adapter = Adapter(list, textStyle, showIcon)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(ctx)
        adapter.setOnItemClickListener { _, _, pos -> onItemClickListener?.onItemClick(dialog, adapter.data[pos]) }
        return layout
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    data class Item(val id: Int, var content: CharSequence?, var icon: Drawable?, var gravity: Int? = null)

    class Adapter(list: List<Item>,
                  private var textStyle: TextStyleBean,
                  private var showIcon: Boolean = true):
            BaseQuickAdapter<Item, BaseViewHolder>(R.layout.uix_dialog_content_list_simple_item, list) {

        override fun convert(helper: BaseViewHolder?, item: Item?) {
            val tv = helper?.getView<TextView>(R.id.tv) as NormalTextView
            tv.text = item!!.content
            tv.setStyle(textStyle, GlobalConfig.textStyle)
            if (item.gravity != null) tv.gravity = item.gravity!!
            if (item.icon != null) helper.setImageDrawable(R.id.iv, item.icon!!)
            if (showIcon) helper.getView<View>(R.id.iv).visibility = View.VISIBLE
        }
    }

    interface OnItemClickListener {

        fun onItemClick(dialog: BeautyDialog, item: Item)
    }

    class Builder {
        private var list: List<Item> = emptyList()
        private var showIcon = true
        private var textStyle = GlobalConfig.textStyle
        private var onItemClickListener: OnItemClickListener? = null

        fun setList(list: List<Item>): Builder {
            this.list = list
            return this
        }

        fun setTextStyle(textStyle: TextStyleBean): Builder {
            this.textStyle = textStyle
            return this
        }

        fun setShowIcon(showIcon: Boolean): Builder {
            this.showIcon = showIcon
            return this
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener): Builder {
            this.onItemClickListener = onItemClickListener
            return this
        }

        fun build(): SimpleList {
            val simpleList = SimpleList()
            simpleList.list = list
            simpleList.textStyle = textStyle
            simpleList.showIcon = showIcon
            simpleList.onItemClickListener = onItemClickListener
            return simpleList
        }
    }

    companion object {

        fun get(items: List<Item>): SimpleList = Builder().setList(items).build()

        fun builder(): Builder = Builder()
    }

    object GlobalConfig {
        var textStyle = TextStyleBean()
    }
}