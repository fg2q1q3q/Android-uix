package me.shouheng.uix.dialog.content

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.R
import me.shouheng.uix.dialog.BeautyDialog

/**
 * 列表类型的内容
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-15 19:06
 */
class SimpleList private constructor(): IDialogContent {

    private var list: List<Item> = emptyList()
    private var textColor: Int? = null
    private var textSize = 16F
    private var gravity: Int = Gravity.START
    private var showIcon = true
    private var onItemClickListener: OnItemClickListener? = null
    private lateinit var dialog: BeautyDialog

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_list_simple, null)
        val rv = layout.findViewById<RecyclerView>(R.id.rv)
        val adapter = Adapter(list, textColor, textSize, gravity, showIcon)
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
                  private var textColor: Int?,
                  private var textSize: Float = 16F,
                  private var gravity: Int = Gravity.START,
                  private var showIcon: Boolean = true):
            BaseQuickAdapter<Item, BaseViewHolder>(R.layout.uix_dialog_content_list_simple_item, list) {

        override fun convert(helper: BaseViewHolder?, item: Item?) {
            val tv = helper?.getView<TextView>(R.id.tv)!!
            tv.text = item!!.content
            tv.textSize = textSize
            tv.gravity = item.gravity?:gravity
            if (textColor != null) tv.setTextColor(textColor!!)
            if (item.icon != null) helper.setImageDrawable(R.id.iv, item.icon!!)
            if (showIcon) helper.getView<View>(R.id.iv).visibility = View.VISIBLE
        }
    }

    interface OnItemClickListener {

        fun onItemClick(dialog: BeautyDialog, item: Item)
    }

    class Builder {
        private var list: List<Item> = emptyList()
        private var textColor: Int? = null
        private var textSize = 16F
        private var gravity: Int = Gravity.START
        private var showIcon = true
        private var onItemClickListener: OnItemClickListener? = null

        fun setList(list: List<Item>): Builder {
            this.list = list
            return this
        }

        fun setTextColor(@ColorInt textColor: Int): Builder {
            this.textColor = textColor
            return this
        }

        fun setTextSize(textSize: Float): Builder {
            this.textSize = textSize
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
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
            simpleList.textColor = textColor
            simpleList.textSize = textSize
            simpleList.gravity = gravity
            simpleList.showIcon = showIcon
            simpleList.onItemClickListener = onItemClickListener
            return simpleList
        }
    }

    companion object {

        fun get(items: List<Item>): SimpleList = Builder().setList(items).build()

        fun builder(): Builder = Builder()
    }
}