package me.shouheng.uix.image

import android.content.Context
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.IncapableCause
import com.zhihu.matisse.internal.entity.Item
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils
import me.shouheng.uix.R
import me.shouheng.uix.utils.UIXUtils
import java.util.*

class GifSizeFilter(private val mMinWidth: Int, private val mMinHeight: Int, private val mMaxSize: Int) : Filter() {

    public override fun constraintTypes(): Set<MimeType> {
        return HashSet<MimeType>().apply {
            add(MimeType.GIF)
        }
    }

    override fun filter(context: Context, item: Item): IncapableCause? {
        if (!needFiltering(context, item))
            return null

        val size = PhotoMetadataUtils.getBitmapBound(context.contentResolver, item.contentUri)
        return if (mMaxSize == 0) {
            IncapableCause(
                IncapableCause.TOAST,
                UIXUtils.getString(R.string.uix_not_allow_gif)
            )
        } else if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
            IncapableCause(
                IncapableCause.TOAST, UIXUtils.format(R.string.uix_error_gif, mMinWidth,
                    PhotoMetadataUtils.getSizeInMB(mMaxSize.toLong()).toString())
            )
        } else null
    }

}
