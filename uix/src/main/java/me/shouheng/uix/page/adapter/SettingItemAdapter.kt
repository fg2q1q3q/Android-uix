package me.shouheng.uix.page.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.R
import me.shouheng.uix.button.SwitchButton
import me.shouheng.uix.page.model.*
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_DIVIDER
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_IMAGE
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.page.model.ISettingItem.ItemType.Companion.TYPE_SWITCH
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
    }
    override fun convert(helper: BaseViewHolder?, settingItem: ISettingItem?) {
        helper?.itemView?.setBackgroundColor(itemBackground)
        when(settingItem!!.itemType) {
            TYPE_DIVIDER -> {
                settingItem as SettingDividerSettingItem
                helper?.itemView?.setBackgroundColor(settingItem.bgColor?:Color.TRANSPARENT)
            }
            TYPE_TEXT -> {
                settingItem as SettingTextSettingItem
                helper?.setText(R.id.tv_title, settingItem.title)
                helper?.setText(R.id.tv_foot, settingItem.foot)
                helper?.getView<View>(R.id.iv_more)!!.visibility =
                        if (settingItem.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable
                        ?:UIXUtils.getDrawable(R.drawable.uix_chevron_right_black_24dp))
            }
            TYPE_IMAGE -> {
                settingItem as SettingImageSettingItem
                settingItem.imageLoader?.load(helper!!.getView(R.id.iv_image))
                helper?.setText(R.id.tv_title, settingItem.title)
                helper?.getView<View>(R.id.iv_more)!!.visibility =
                        if (settingItem.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable
                        ?:UIXUtils.getDrawable(R.drawable.uix_chevron_right_black_24dp))
            }
            TYPE_SWITCH -> {
                settingItem as SettingSwitchSettingItem
                helper?.setText(R.id.tv_title, settingItem.title)
                val sb = helper!!.getView(R.id.sb) as SwitchButton
                sb.isChecked = settingItem.enable
                sb.setOnCheckedChangeListener { _, isChecked ->
                    settingItem.onCheckStateChangeListener?.onStateChange(sb, isChecked)
                }
                helper.setBackgroundColor(R.id.line, lineColor)
            }
        }
    }
}
