package me.shouheng.uix.pages.setting

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.common.utils.URes
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.R
import me.shouheng.uix.pages.setting.ISettingItem.ItemType.Companion.TYPE_DESC
import me.shouheng.uix.pages.setting.ISettingItem.ItemType.Companion.TYPE_DIVIDER
import me.shouheng.uix.pages.setting.ISettingItem.ItemType.Companion.TYPE_IMAGE
import me.shouheng.uix.pages.setting.ISettingItem.ItemType.Companion.TYPE_LONG_TEXT
import me.shouheng.uix.pages.setting.ISettingItem.ItemType.Companion.TYPE_SWITCH
import me.shouheng.uix.pages.setting.ISettingItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.widget.button.SwitchButton
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 设置或者用户信息界面的条目
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:16
 */
class SettingItemAdapter(list: List<ISettingItem>,
                         /** 用来统一更换所有分割线的颜色 */
                         private val lineColor: Int = Color.TRANSPARENT,
                         /** 用来统一更换所有的 > 按钮的图标 */
                         private val moreDrawable: Drawable? = null,
                         /** 用来统一更换所有条目的背景颜色 */
                         private val itemBackground: Int = Color.TRANSPARENT)
    : BaseMultiItemQuickAdapter<ISettingItem, BaseViewHolder>(list) {
    private val defaultMoreDrawable = URes.getDrawable(R.drawable.ic_more)
    init {
        addItemType(TYPE_TEXT, R.layout.uix_item_setting_text)
        addItemType(TYPE_IMAGE, R.layout.uix_item_setting_image)
        addItemType(TYPE_DIVIDER, R.layout.uix_item_setting_divider)
        addItemType(TYPE_SWITCH, R.layout.uix_item_setting_switch)
        addItemType(TYPE_DESC, R.layout.uix_item_setting_desc)
        addItemType(TYPE_LONG_TEXT, R.layout.uix_item_setting_long_text)
    }
    private val screenWidth = UView.getScreenWidth()
    private val dp20 = UView.dp2px(20f)
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
                helper.getView<NormalTextView>(R.id.tv_title).setStyle(item.titleStyle)
                helper.getView<NormalTextView>(R.id.tv_foot).setStyle(item.footStyle)
                helper.getView<View>(R.id.iv_more)!!.visibility = if (item.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable ?:defaultMoreDrawable)
                helper.getView<View>(R.id.pb).visibility = if (item.loading) View.VISIBLE else View.GONE
            }
            TYPE_IMAGE -> {
                item as SettingImageItem
                item.imageLoader?.load(helper.getView(R.id.iv_image))
                helper.setText(R.id.tv_title, item.title)
                helper.getView<NormalTextView>(R.id.tv_title).setStyle(item.titleStyle)
                helper.getView<View>(R.id.iv_more)!!.visibility = if (item.editable) View.VISIBLE else View.GONE
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
                helper.setImageDrawable(R.id.iv_more, moreDrawable ?:defaultMoreDrawable)
                helper.getView<View>(R.id.pb).visibility = if (item.loading) View.VISIBLE else View.GONE
            }
            TYPE_SWITCH -> {
                item as SettingSwitchItem
                helper.setText(R.id.tv_title, item.title)
                helper.getView<NormalTextView>(R.id.tv_title).setStyle(item.titleStyle)
                val sb = helper.getView(R.id.sb) as SwitchButton
                sb.isChecked = item.enable
                sb.setOnCheckedChangeListener { _, isChecked ->
                    item.onCheckStateChangeListener?.onStateChange(sb, isChecked)
                }
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
                helper.getView<View>(R.id.pb).visibility = if (item.loading) View.VISIBLE else View.GONE
            }
            TYPE_DESC -> {
                item as SettingDescItem
                helper.setText(R.id.tv, item.desc)
                helper.getView<NormalTextView>(R.id.tv).setStyle(item.descStyle)
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
            }
            TYPE_LONG_TEXT -> {
                item as SettingLongTextItem
                helper.setText(R.id.tv_title, item.title)
                helper.setText(R.id.tv_foot, item.subTitle)
                val ll = helper.getView<LinearLayout>(R.id.ll)
                val tvTitle = helper.getView<NormalTextView>(R.id.tv_title)
                tvTitle.setStyle(item.titleStyle)
                val tvFoot = helper.getView<NormalTextView>(R.id.tv_foot)
                tvFoot.setStyle(item.subTitleStyle)
                if (tvTitle.width + tvFoot.width + dp20 < screenWidth) {
                    ll.orientation = LinearLayout.HORIZONTAL
                } else {
                    ll.orientation = LinearLayout.VERTICAL
                }
                helper.setBackgroundColor(R.id.line, item.lineColor?:lineColor)
                helper.getView<View>(R.id.pb).visibility = if (item.loading) View.VISIBLE else View.GONE
            }
        }
    }
}
