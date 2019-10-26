package me.shouheng.uix.items

import android.support.annotation.ColorInt


class DividerItem(private val id: Int,
                  var editable: Boolean = false,
                  @ColorInt var bgColor: Int? = null) : IItem {

    private val type: Int = IItem.ItemType.TYPE_DIVIDER

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class TextItem(private val id: Int,
               var editable: Boolean = true,
               var title: String? = null,
               var foot: String? = null) : IItem {

    private val type: Int = IItem.ItemType.TYPE_TEXT

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class ImageItem(private val id: Int,
                var editable: Boolean = true,
                var title: String? = null,
                var imageLoader: Tools? = null) : IItem {

    private val type: Int = IItem.ItemType.TYPE_IMAGE

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}

class SwitchItem(private val id: Int,
                 var editable: Boolean = true,
                 var title: String? = null,
                 var enable: Boolean = false,
                 var onCheckStateChangeListener: OnCheckStateChangeListener? = null) : IItem {

    private val type: Int = IItem.ItemType.TYPE_SWITCH

    override fun getId() = id

    override fun editable() = editable

    override fun getItemType() = type
}