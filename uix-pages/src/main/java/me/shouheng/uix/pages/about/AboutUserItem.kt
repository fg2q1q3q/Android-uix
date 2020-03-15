package me.shouheng.uix.pages.about

import android.os.Parcel
import android.os.Parcelable
import me.shouheng.uix.common.bean.TextStyleBean

class AboutUserItem(private val id: Int,
                    var name: CharSequence? = null,
                    var nameStyle: TextStyleBean = TextStyleBean(),
                    var desc: CharSequence? = null,
                    var descStyle: TextStyleBean = TextStyleBean()): IAboutItem {

    private val type: Int = IAboutItem.ItemType.TYPE_USER

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readSerializable() as TextStyleBean,
            parcel.readString(),
            parcel.readSerializable() as TextStyleBean)

    override fun getId() = id

    override fun getItemType() = type

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name?.toString())
        parcel.writeSerializable(nameStyle)
        parcel.writeString(desc?.toString())
        parcel.writeSerializable(descStyle)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AboutUserItem> {
        override fun createFromParcel(parcel: Parcel): AboutUserItem {
            return AboutUserItem(parcel)
        }

        override fun newArray(size: Int): Array<AboutUserItem?> {
            return arrayOfNulls(size)
        }
    }
}

