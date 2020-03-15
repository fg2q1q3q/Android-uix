package me.shouheng.uix.pages.about

import android.os.Parcel
import android.os.Parcelable
import me.shouheng.uix.common.bean.TextStyleBean

class AboutTextItem(private val id: Int,
                    var text: CharSequence? = null,
                    var textStyle: TextStyleBean = TextStyleBean()): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_TEXT

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readSerializable() as TextStyleBean)

    override fun getId() = id

    override fun getItemType() = type

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text?.toString())
        parcel.writeSerializable(textStyle)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AboutTextItem> {
        override fun createFromParcel(parcel: Parcel): AboutTextItem {
            return AboutTextItem(parcel)
        }

        override fun newArray(size: Int): Array<AboutTextItem?> {
            return arrayOfNulls(size)
        }
    }
}