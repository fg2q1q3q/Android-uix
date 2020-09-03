package me.shouheng.uix.pages.image

import android.content.Context
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.IncapableCause
import com.zhihu.matisse.internal.entity.Item
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils
import me.shouheng.uix.common.utils.URes
import me.shouheng.uix.pages.R
import java.util.*

class GifSizeFilter(private val mMinWidth: Int,
                    private val mMinHeight: Int,
                    private val mMaxSize: Int /*bytes*/) : Filter() {

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
                URes.getString(R.string.uix_not_allow_gif)
            )
        } else if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
            IncapableCause(
                IncapableCause.TOAST, URes.format(R.string.uix_error_gif, mMinWidth,
                    PhotoMetadataUtils.getSizeInMB(mMaxSize.toLong()).toString())
            )
        } else null
    }

}
