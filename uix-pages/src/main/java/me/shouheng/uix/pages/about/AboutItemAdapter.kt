package me.shouheng.uix.pages.about

import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.pages.R
import me.shouheng.uix.pages.about.IAboutItem.ItemType.Companion.TYPE_SECTION
import me.shouheng.uix.pages.about.IAboutItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.pages.about.IAboutItem.ItemType.Companion.TYPE_USER
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 关于页面的列表的适配器
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 13:27
 */
class AboutItemAdapter(list: List<IAboutItem>,
                       private val backgroundColor: Int?,
                       private val foregroundColor: Int?,
                       private val fragmentInteraction: AboutFragment.FragmentInteraction?)
    : BaseMultiItemQuickAdapter<IAboutItem, BaseViewHolder>(list) {
    init {
        addItemType(TYPE_SECTION, R.layout.uix_item_about_section)
        addItemType(TYPE_TEXT, R.layout.uix_item_about_text)
        addItemType(TYPE_USER, R.layout.uix_item_about_user)
    }
    override fun convert(helper: BaseViewHolder, item: IAboutItem) {
        helper.itemView.setBackgroundColor(foregroundColor?:Color.TRANSPARENT)
        when(item.itemType) {
            TYPE_SECTION -> {
                item as AboutSectionItem
                helper.itemView.setBackgroundColor(backgroundColor?:Color.TRANSPARENT)
                helper.setText(R.id.category, item.title)
                helper.getView<NormalTextView>(R.id.category)!!.setStyle(item.titleStyle)
            }
            TYPE_TEXT -> {
                item as AboutTextItem
                helper.setText(R.id.content, item.text)
                helper.getView<NormalTextView>(R.id.content)!!.setStyle(item.textStyle)
                helper.getView<NormalTextView>(R.id.content).movementMethod = LinkMovementMethod.getInstance()
                val ll = helper.getView<LinearLayout>(R.id.ll)
                ll.removeAllViews()
                val views = fragmentInteraction?.loadSubViews(item.getId())
                if (views != null) ll.addView(views)
                ll.visibility = if (views != null) View.VISIBLE else View.GONE
            }
            TYPE_USER -> {
                item as AboutUserItem
                fragmentInteraction?.loadImage(item.getId(), helper.getView(R.id.avatar))
                helper.setText(R.id.name, item.name)
                helper.getView<NormalTextView>(R.id.name)!!.setStyle(item.nameStyle)
                helper.setText(R.id.desc, item.desc)
                helper.getView<NormalTextView>(R.id.desc)!!.setStyle(item.descStyle)
                val ll = helper.getView<LinearLayout>(R.id.ll)
                ll.removeAllViews()
                val views = fragmentInteraction?.loadSubViews(item.getId())
                if (views != null) ll.addView(views)
                ll.visibility = if (views != null) View.VISIBLE else View.GONE
            }
        }
    }
}
