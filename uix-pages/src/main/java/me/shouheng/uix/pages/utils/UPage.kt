package me.shouheng.uix.pages.utils

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import com.zhihu.matisse.internal.utils.PathUtils.getPath
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.common.utils.URes
import me.shouheng.uix.pages.R

object UPage {

    private val VIDEO_EXTENSIONS = listOf("avi", "mov", "wmv", "3gp", "rmvb", "flv", "mpeg", "mp4", "mkv", "asf")

    private val IMAGE_EXTENSIONS = listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")

    private val AUDIO_EXTENSIONS = listOf("mp3", "wav", "aac", "wma")

    private val GIF_EXTENSIONS = listOf("gif")

    private const val ANDROID_RESOURCE_URI_PREFIX = "android.resource://"

    fun isVideo(context: Context, uri: Uri): Boolean {
        val realPath = getRealPath(context, uri)
        val mimeType = URes.getMimeType(context, uri)
        return (mimeType != null && mimeType.contains("video", true))
                || VIDEO_EXTENSIONS.any { realPath?.endsWith(it, true) == true }
    }

    fun isGif(context: Context, uri: Uri): Boolean {
        val realPath = getRealPath(context, uri)
        return GIF_EXTENSIONS.any { realPath?.endsWith(it, true) == true }
    }

    fun getThumbnailUri(context: Context, uri: Uri): Uri {
        if (isFromNetwork(uri)) return uri
        val mimeType = URes.getMimeType(context, uri)
        return if (!TextUtils.isEmpty(mimeType)) {
            ULog.d("$mimeType")
            val type = mimeType!!.split("/")[0]
            val subtype = mimeType.split("/")[1]
            when (type) {
                "image", "video" -> uri
                "audio" -> Uri.parse( "$ANDROID_RESOURCE_URI_PREFIX${context.packageName}/${R.raw.uix_play}")
                else -> getThumbnailUri(context, uri, subtype)
            }
        } else {
            val realPath = getRealPath(context, uri)
            val extension = getFileExtension(realPath?:uri.toString())
            getThumbnailUri(context, uri, extension)
        }
    }

    fun scaleUpDown(view: View, duration: Long): ScaleAnimation? {
        val animation = ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f)
        animation.repeatCount = -1
        animation.repeatMode = Animation.RESTART
        animation.interpolator = LinearInterpolator()
        animation.duration = duration
        view.startAnimation(animation)
        return animation
    }

    private fun isFromNetwork(uri: Uri) = uri.scheme?.startsWith("http") == true

    private fun getThumbnailUri(context: Context, uri: Uri, extension: String): Uri {
        val isImage = IMAGE_EXTENSIONS.any { TextUtils.equals(extension.toLowerCase(), it.toLowerCase()) }
        if (isImage) return uri
        val isAudio = AUDIO_EXTENSIONS.any { TextUtils.equals(extension.toLowerCase(), it.toLowerCase()) }
        if (isAudio) return Uri.parse("$ANDROID_RESOURCE_URI_PREFIX${context.packageName}/${R.raw.uix_play}")
        val isVideo = VIDEO_EXTENSIONS.any { TextUtils.equals(extension.toLowerCase(), it.toLowerCase()) }
        if (isVideo) return uri
        return Uri.parse("$ANDROID_RESOURCE_URI_PREFIX${context.packageName}/${R.raw.uix_files}")
    }

    private fun getFileExtension(path: String): String {
        if (TextUtils.isEmpty(path)) return ""
        var extension = ""
        val index = path.lastIndexOf(".")
        if (index != -1) extension = path.substring(index, path.length)
        return extension
    }

    private fun getRealPath(context: Context, uri: Uri): String? {
        try {
            return if (isFromNetwork(uri)) uri.toString() else getPath(context, uri)
        } catch (ex: Exception) {
            ULog.e(ex)
        }
        return null
    }
}