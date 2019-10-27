package me.shouheng.uix.page.model

import android.support.annotation.ColorInt
import me.shouheng.uix.page.OnCheckStateChangeListener
import me.shouheng.uix.page.ImageLoader


class SettingDividerSettingItem(private val id: Int,
                                var editable: Boolean = false,
                                @ColorInt var bgColor: Int? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_DIVIDER

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingTextSettingItem(private val id: Int,
                             var editable: Boolean = true,
                             var title: String? = null,
                             var foot: String? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_TEXT

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingImageSettingItem(private val id: Int,
                              var editable: Boolean = true,
                              var title: String? = null,
                              var imageLoader: ImageLoader? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_IMAGE

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SettingSwitchSettingItem(private val id: Int,
                               var editable: Boolean = true,
                               var title: String? = null,
                               var enable: Boolean = false,
                               var onCheckStateChangeListener: OnCheckStateChangeListener? = null) : ISettingItem {

    private val type: Int = ISettingItem.ItemType.TYPE_SWITCH

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}