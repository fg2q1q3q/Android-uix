package me.shouheng.uix.page.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.R
import me.shouheng.uix.page.model.AboutSectionItem
import me.shouheng.uix.page.model.AboutTextItem
import me.shouheng.uix.page.model.AboutUserItem
import me.shouheng.uix.page.model.IAboutItem
import me.shouheng.uix.page.model.IAboutItem.ItemType.Companion.TYPE_SECTION
import me.shouheng.uix.page.model.IAboutItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.page.model.IAboutItem.ItemType.Companion.TYPE_USER

/**
 * 关于页面的列表的适配器
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 13:27
 */
class AboutItemAdapter(list: List<IAboutItem>, private val itemBackground: Int = Color.TRANSPARENT)
    : BaseMultiItemQuickAdapter<IAboutItem, BaseViewHolder>(list) {
    init {
        addItemType(TYPE_SECTION, R.layout.uix_item_about_section)
        addItemType(TYPE_TEXT, R.layout.uix_item_about_text)
        addItemType(TYPE_USER, R.layout.uix_item_about_user)
    }
    override fun convert(helper: BaseViewHolder, item: IAboutItem) {
        helper.itemView.setBackgroundColor(itemBackground)
        when(item.itemType) {
            TYPE_SECTION -> {
                item as AboutSectionItem
                helper.itemView.setBackgroundColor(item.bgColor)
                helper.setText(R.id.category, item.title)
                if (item.titleTextColor != null) {
                    helper.setTextColor(R.id.category, item.titleTextColor!!)
                }
                helper.getView<TextView>(R.id.category)!!.textSize = item.titleTextSize
                helper.getView<TextView>(R.id.category)!!.setTypeface(null, item.titleTypeface)
            }
            TYPE_TEXT -> {
                item as AboutTextItem
                helper.setText(R.id.content, item.text)
                if (item.textColor != null) {
                    helper.setTextColor(R.id.content, item.textColor!!)
                }
                helper.getView<TextView>(R.id.content)!!.textSize = item.textSize
                helper.getView<TextView>(R.id.content)!!.setTypeface(null, item.typeface)
            }
            TYPE_USER -> {
                item as AboutUserItem
                item.imageLoader?.load(helper.getView(R.id.avatar))
                helper.setText(R.id.name, item.name)
                if (item.nameTextColor != null) {
                    helper.setTextColor(R.id.name, item.nameTextColor!!)
                }
                helper.getView<TextView>(R.id.name)!!.textSize = item.nameTextSize
                helper.getView<TextView>(R.id.name)!!.setTypeface(null, item.nameTypeface)
                helper.setText(R.id.desc, item.desc)
                if (item.descTextColor != null) {
                    helper.setTextColor(R.id.desc, item.descTextColor!!)
                }
                helper.getView<TextView>(R.id.desc)!!.textSize = item.descTextSize
                helper.getView<TextView>(R.id.desc)!!.setTypeface(null, item.descTypeface)
            }
        }
    }
}
