package me.shouheng.uix.pages.about

import android.os.Parcel
import android.os.Parcelable
import me.shouheng.uix.common.bean.TextStyleBean

class AboutSectionItem(private val id: Int,
                       var title: CharSequence? = null,
                       var titleStyle: TextStyleBean = TextStyleBean()): IAboutItem {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(title?.toString())
        dest?.writeSerializable(titleStyle)
    }

    override fun describeContents(): Int = 0

    private val type: Int = IAboutItem.ItemType.TYPE_SECTION

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readSerializable() as TextStyleBean)

    override fun getId() = id

    override fun getItemType() = type

    companion object CREATOR : Parcelable.Creator<AboutSectionItem> {
        override fun createFromParcel(parcel: Parcel): AboutSectionItem {
            return AboutSectionItem(parcel)
        }

        override fun newArray(size: Int): Array<AboutSectionItem?> {
            return arrayOfNulls(size)
        }
    }
}