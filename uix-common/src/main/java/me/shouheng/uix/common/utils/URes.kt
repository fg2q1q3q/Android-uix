package me.shouheng.uix.common.utils

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.text.TextUtils
import android.webkit.MimeTypeMap
import me.shouheng.uix.common.UIX

/**
 * UIX Res 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object URes {

    fun getDrawable(@DrawableRes id: Int): Drawable {
        return UIX.getApp().resources.getDrawable(id)
    }

    fun getColor(@ColorRes id: Int): Int {
        return UIX.getApp().resources.getColor(id)
    }

    fun getString(@StringRes id: Int): String {
        return UIX.getApp().resources.getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return UIX.getApp().resources.getString(id, *formatArgs)
    }

    fun getAssets(): AssetManager {
        return UIX.getApp().resources.assets
    }

    fun format(@StringRes resId: Int, vararg arg: Any): String {
        return try {
            String.format(UIX.getApp().getString(resId), *arg)
        } catch (e: Exception) {
            UIX.getApp().getString(resId)
        }
    }

    fun getMimeType(ctx: Context, uri: Uri): String? {
        var mimeType = ctx.contentResolver.getType(uri)
        if (TextUtils.isEmpty(mimeType)) mimeType = getMimeType(uri.toString())
        return mimeType
    }

    private fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            val mime = MimeTypeMap.getSingleton()
            type = mime.getMimeTypeFromExtension(extension)
        }
        return type
    }
}
