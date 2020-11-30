package me.shouheng.uix.widget.rv

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Get a quick adapter.
 *
 * @param itemLayout the item layout
 * @param converter  how the data is set to its layout
 * @param data       the data
 */
fun <ITEM> getAdapter(@LayoutRes itemLayout:Int,
                      converter: (helper: BaseViewHolder, item: ITEM) -> Unit,
                      data: List<ITEM>): Adapter<ITEM>
        = Adapter(itemLayout, converter, data)

class Adapter<ITEM>(
        @LayoutRes private val layout: Int,
        private val converter: (helper: BaseViewHolder, item: ITEM) -> Unit,
        val list: List<ITEM>
): BaseQuickAdapter<ITEM, BaseViewHolder>(layout, list) {
    override fun convert(helper: BaseViewHolder, item: ITEM) {
        converter(helper, item)
    }
}

/** Make given view gone if satisfy given condition defined by [goneIf]. */
fun BaseViewHolder.goneIf(@IdRes id: Int, goneIf: Boolean) {
    this.getView<View>(id).visibility = if (goneIf) View.GONE else View.VISIBLE
}

/** Add click listener to views group. */
fun BaseViewHolder.addOnClickListeners(@IdRes vararg ids: Int) {
    ids.forEach { addOnClickListener(it) }
}
