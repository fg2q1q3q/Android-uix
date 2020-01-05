package me.shouheng.uix.page.model

import android.graphics.Color
import android.graphics.Typeface
import me.shouheng.uix.page.GetSubViewListener
import me.shouheng.uix.page.ImageLoader

class AboutSectionItem(private val id: Int,
                       var title: CharSequence? = null,
                       var bgColor: Int = Color.TRANSPARENT,
                       var titleTextColor: Int? = null,
                       var titleTypeface: Int = Typeface.NORMAL,
                       var titleTextSize: Float = 14f): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_SECTION

    override fun getId() = id

    override fun getItemType() = type
}

class AboutTextItem(private val id: Int,
                    var text: CharSequence? = null,
                    var textColor: Int? = null,
                    var typeface: Int = Typeface.NORMAL,
                    var textSize: Float = 16f,
                    var getSubViewListener: GetSubViewListener? = null): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_TEXT

    override fun getId() = id

    override fun getItemType() = type
}

class AboutUserItem(private val id: Int,
                    var name: CharSequence? = null,
                    var nameTextColor: Int? = null,
                    var nameTextSize: Float = 16f,
                    var nameTypeface: Int = Typeface.NORMAL,
                    var desc: CharSequence? = null,
                    var descTextColor: Int? = null,
                    var descTextSize: Float = 16f,
                    var descTypeface: Int = Typeface.NORMAL,
                    var imageLoader: ImageLoader? = null): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_USER

    override fun getId() = id

    override fun getItemType() = type
}

