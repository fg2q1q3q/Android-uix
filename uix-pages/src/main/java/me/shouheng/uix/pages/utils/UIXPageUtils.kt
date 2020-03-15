package me.shouheng.uix.pages.utils

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.zhihu.matisse.internal.utils.PathUtils.getPath
import me.shouheng.uix.common.utils.UIXLogUtils
import me.shouheng.uix.common.utils.UIXResUtils
import me.shouheng.uix.pages.R

object UIXPageUtils {

    fun getThumbnailUri(mContext: Context, uri: Uri): Uri {
        val mimeType = UIXResUtils.getMimeType(mContext, uri)
        if (!TextUtils.isEmpty(mimeType)) {
            val type = mimeType!!.split("/")[0]
            val subtype = mimeType.split("/")[1]
            UIXLogUtils.d(mimeType)
            return when (type) {
                "image", "video" -> {
                    uri
                }
                "audio" -> {
                    return Uri.parse("android.resource://" + mContext.packageName + "/" + R.raw.uix_play)
                }
                else -> {
                    return getThumbnailUri(mContext, uri, subtype)
                }
            }
        } else {
            val path = getPath(mContext, uri)
            val extension = if (TextUtils.isEmpty(path)) getFileExtension(uri.toString()) else getFileExtension(path)
            return getThumbnailUri(mContext, uri, extension)
        }
    }

    private fun getThumbnailUri(mContext: Context, uri: Uri, extension: String): Uri {
        return when (extension) {
            "mp3", "wav", "aac", "wma" -> { // audio
                Uri.parse("android.resource://" + mContext.packageName + "/" + R.raw.uix_play)
            }
            "avi", "mov", "wmv", "3gp", "rmvb", "flv", "mpeg", "mp4" -> {
                uri
            }
            else -> {
                Uri.parse("android.resource://" + mContext.packageName + "/" + R.raw.uix_files)
            }
        }
    }

    private fun getFileExtension(fileName: String): String {
        if (TextUtils.isEmpty(fileName)) return ""
        var extension = ""
        val index = fileName.lastIndexOf(".")
        if (index != -1) extension = fileName.substring(index, fileName.length)
        return extension
    }
}