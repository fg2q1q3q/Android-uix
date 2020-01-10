package me.shouheng.uix.page.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.R
import me.shouheng.uix.button.SwitchButton
import me.shouheng.uix.page.model.*
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_DESC
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_DIVIDER
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_IMAGE
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_LONG_TEXT
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_SWITCH
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.utils.UIXUtils

/**
 * 设置或者用户信息界面的条目
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:16
 */
class SettingItemAdapter(list: List<ISettingItem>,
                         private val lineColor: Int = Color.TRANSPARENT,
                         private val moreDrawable: Drawable? = null,
                         private val itemBackground: Int = Color.TRANSPARENT)
    : BaseMultiItemQuickAdapter<ISettingItem, BaseViewHolder>(list) {
    init {
        addItemType(TYPE_TEXT, R.layout.uix_item_setting_text)
        addItemType(TYPE_IMAGE, R.layout.uix_item_setting_image)
        addItemType(TYPE_DIVIDER, R.layout.uix_item_setting_divider)
        addItemType(TYPE_SWITCH, R.layout.uix_item_setting_switch)
        addItemType(TYPE_DESC, R.layout.uix_item_setting_desc)
        addItemType(TYPE_LONG_TEXT, R.layout.uix_item_setting_long_text)
    }
    override fun convert(helper: BaseViewHolder, item: ISettingItem) {
        helper.itemView.setBackgroundColor(itemBackground)
        when(item.itemType) {
            TYPE_DIVIDER -> {
                item as SettingDividerItem
                helper.itemView.setBackgroundColor(item.bgColor?:Color.TRANSPARENT)
            }
            TYPE_TEXT -> {
                item as SettingTextItem
                helper.setText(R.id.tv_title, item.title)
                helper.setText(R.id.tv_foot, item.foot)
                helper.getView<View>(R.id.iv_more)!!.visibility =
                        if (item.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable
                        ?:UIXUtils.getDrawable(R.drawable.uix_chevron_right_black_24dp))
            }
            TYPE_IMAGE -> {
                item as SettingImageItem
                item.imageLoader?.load(helper.getView(R.id.iv_image))
                helper.setText(R.id.tv_title, item.title)
                helper.getView<View>(R.id.iv_more)!!.visibility =
                        if (item.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable
                        ?:UIXUtils.getDrawable(R.drawable.uix_chevron_right_black_24dp))
            }
            TYPE_SWITCH -> {
                item as SettingSwitchItem
                helper.setText(R.id.tv_title, item.title)
                val sb = helper.getView(R.id.sb) as SwitchButton
                sb.isChecked = item.enable
                sb.setOnCheckedChangeListener { _, isChecked ->
                    item.onCheckStateChangeListener?.onStateChange(sb, isChecked)
                }
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
            }
            TYPE_DESC -> {
                item as SettingDescItem
                val tv = helper.getView<TextView>(R.id.tv)
                tv.text = item.desc
                item.callback?.custom(tv)
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
            }
            TYPE_LONG_TEXT -> {
                item as SettingLongTextItem
                helper.setText(R.id.tv_title, item.title)
                helper.setText(R.id.tv_foot, item.subTitle)
                helper.getView<TextView>(R.id.tv_foot).gravity = item.gravity
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
            }
        }
    }
}
