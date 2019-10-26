package me.shouheng.uix.items

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.R
import me.shouheng.uix.button.SwitchButton
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_DIVIDER
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_IMAGE
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_SWITCH
import me.shouheng.uix.utils.UIXUtils

/**
 * 设置或者用户信息界面的条目
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:16
 */
class ItemAdapter(list: List<IItem>,
                  private val lineColor: Int = Color.TRANSPARENT,
                  private val moreDrawable: Drawable? = null,
                  private val itemBackground: Int = Color.TRANSPARENT) : BaseMultiItemQuickAdapter<IItem, BaseViewHolder>(list) {

    init {
        addItemType(TYPE_TEXT, R.layout.uix_item_setting_text)
        addItemType(TYPE_IMAGE, R.layout.uix_item_setting_image)
        addItemType(TYPE_DIVIDER, R.layout.uix_item_setting_divider)
        addItemType(TYPE_SWITCH, R.layout.uix_item_setting_switch)
    }

    override fun convert(helper: BaseViewHolder?, item: IItem?) {
        helper?.itemView?.setBackgroundColor(itemBackground)
        when(item!!.itemType) {
            TYPE_DIVIDER -> {
                item as DividerItem
                helper?.itemView?.setBackgroundColor(item.bgColor?:Color.TRANSPARENT)
            }
            TYPE_TEXT -> {
                item as TextItem
                helper?.setText(R.id.tv_title, item.title)
                helper?.setText(R.id.tv_foot, item.foot)
                helper?.getView<View>(R.id.iv_more)!!.visibility =
                        if (item.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable
                        ?:UIXUtils.getDrawable(R.drawable.ic_chevron_right_black_24dp))
            }
            TYPE_IMAGE -> {
                item as ImageItem
                item.imageLoader?.load(helper!!.getView(R.id.iv_image))
                helper?.setText(R.id.tv_title, item.title)
                helper?.getView<View>(R.id.iv_more)!!.visibility =
                        if (item.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable
                        ?:UIXUtils.getDrawable(R.drawable.ic_chevron_right_black_24dp))
            }
            TYPE_SWITCH -> {
                item as SwitchItem
                helper?.setText(R.id.tv_title, item.title)
                val sb = helper!!.getView(R.id.sb) as SwitchButton
                sb.isChecked = item.enable
                sb.setOnCheckedChangeListener { _, isChecked ->
                    item.onCheckStateChangeListener?.onStateChange(sb, isChecked)
                }
                helper.setBackgroundColor(R.id.line, lineColor)
            }
        }
    }
}
