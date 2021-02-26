package me.shouheng.uix.pages.setting

import android.support.annotation.ColorInt

class SettingDividerItem(
        private val id: Int,
        var editable: Boolean = false,
        var loading: Boolean = false,
        @ColorInt var bgColor: Int? = null
) : ISettingItem {

    override fun getId() = id

    override fun editable() = editable

    override fun editable(editable: Boolean) {
        this.editable = editable
    }

    override fun loading(): Boolean = loading

    override fun loading(loading: Boolean) {
        this.loading = loading
    }

    override fun getItemType() = ISettingItem.ItemType.TYPE_DIVIDER
}