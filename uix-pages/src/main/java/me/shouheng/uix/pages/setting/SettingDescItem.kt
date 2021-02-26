package me.shouheng.uix.pages.setting

import me.shouheng.uix.common.bean.TextStyleBean

class SettingDescItem(
        private val id: Int,
        var editable: Boolean = true,
        var loading: Boolean = false,
        val lineColor: Int? = null,
        var desc: CharSequence? = null,
        var descStyle: TextStyleBean = TextStyleBean()
): ISettingItem {

    override fun getId() = id

    override fun editable() = editable

    override fun editable(editable: Boolean) {
        this.editable = editable
    }

    override fun loading(): Boolean = loading

    override fun loading(loading: Boolean) {
        this.loading = loading
    }

    override fun getItemType() = ISettingItem.ItemType.TYPE_DESC

}