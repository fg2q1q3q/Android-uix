package me.shouheng.uix.page.model

import android.graphics.Color
import me.shouheng.uix.page.ImageLoader

class AboutSectionItem(private val id: Int,
                       var title: CharSequence? = null,
                       var bgColor: Int = Color.TRANSPARENT): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_SECTION

    override fun getId() = id

    override fun getItemType() = type
}

class AboutTextItem(private val id: Int,
                    var text: CharSequence? = null): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_TEXT

    override fun getId() = id

    override fun getItemType() = type
}

class AboutUserItem(private val id: Int,
                       var name: CharSequence? = null,
                       var desc: CharSequence? = null,
                       var imageLoader: ImageLoader? = null): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_USER

    override fun getId() = id

    override fun getItemType() = type
}

