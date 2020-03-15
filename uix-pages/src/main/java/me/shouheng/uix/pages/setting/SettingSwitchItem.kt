package me.shouheng.uix.pages.setting

import me.shouheng.uix.common.bean.TextStyleBean

class SettingSwitchItem(private val id: Int,
                        var editable: Boolean = true,
                        var loading: Boolean = false,
                        val lineColor: Int? = null,
                        var title: CharSequence? = null,
                        var enable: Boolean = false,
                        var titleStyle: TextStyleBean = TextStyleBean(),
                        var onCheckStateChangeListener: OnStateChangeListener? = null) : ISettingItem {

    override fun getId() = id

    override fun editable() = editable

    override fun editable(editable: Boolean) {
        this.editable = editable
    }

    override fun loading(): Boolean = loading

    override fun loading(loading: Boolean) {
        this.loading = loading
    }

    override fun getItemType() = ISettingItem.ItemType.TYPE_SWITCH
}