package me.shouheng.uix.page.model

import android.support.annotation.ColorInt
import android.view.Gravity
import me.shouheng.uix.page.CustomTextViewCallback
import me.shouheng.uix.page.ImageLoader
import me.shouheng.uix.page.OnCheckStateChangeListener


class SettingDividerItem(private val id: Int,
                         var editable: Boolean = false,
                         @ColorInt var bgColor: Int? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_DIVIDER

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingTextItem(private val id: Int,
                      var editable: Boolean = true,
                      val lineColor: Int? = null,
                      var title: CharSequence? = null,
                      var foot: CharSequence? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_TEXT

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingLongTextItem(private val id: Int,
                          var editable: Boolean = true,
                          val lineColor: Int? = null,
                          var title: CharSequence? = null,
                          var subTitle: CharSequence? = null,
                          var gravity: Int = Gravity.START) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_LONG_TEXT

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingImageItem(private val id: Int,
                       var editable: Boolean = true,
                       val lineColor: Int? = null,
                       var title: CharSequence? = null,
                       var imageLoader: ImageLoader? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_IMAGE

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingSwitchItem(private val id: Int,
                        var editable: Boolean = true,
                        val lineColor: Int? = null,
                        var title: CharSequence? = null,
                        var enable: Boolean = false,
                        var onCheckStateChangeListener: OnCheckStateChangeListener? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_SWITCH

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingDescItem(private val id: Int,
                      var editable: Boolean = true,
                      val lineColor: Int? = null,
                      var desc: CharSequence? = null,
                      var callback: CustomTextViewCallback? = null): ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_DESC

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type

}