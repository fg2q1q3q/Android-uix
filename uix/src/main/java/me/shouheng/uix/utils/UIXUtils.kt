package me.shouheng.uix.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.MimeTypeMap
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.just.agentweb.LogUtils
import com.zhihu.matisse.internal.utils.PathUtils.*
import me.shouheng.uix.R
import me.shouheng.uix.UIX
import me.shouheng.uix.bean.AddressBean
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.lang.Long.parseLong


/**
 * UIX 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UIXUtils {

    private var sBufferSize = 8192
    private var addresses: List<AddressBean>? = null
    var debug: Boolean = true
    private val FLAG_IMMERSIVE = (View.SYSTEM_UI_FLAG_IMMERSIVE // 与SYSTEM_UI_FLAG_HIDE_NAVIGATION组合使用，没有设置的话在隐藏导航栏后将没有交互
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏虚拟按键(导航栏)
            or View.SYSTEM_UI_FLAG_FULLSCREEN) // Activity全屏显示，且状态栏被隐藏覆盖掉
    @TargetApi(17)
    private val FLAG_VISIBILITY_17 = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
    @TargetApi(19)
    private val FLAG_VISIBILITY_19 = (FLAG_VISIBILITY_17 // hide status bar
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_IMMERSIVE)

    var EXTENSION_3GP = ".3gp"
    var EXTENSION_MP4 = ".mp4"
    var EXTENSION_PDF = ".pdf"

    var MIME_TYPE_IMAGE = "image/jpeg";
    var MIME_TYPE_AUDIO = "audio/amr";
    var MIME_TYPE_VIDEO = "video/mp4";
    var MIME_TYPE_SKETCH = "image/png";
    var MIME_TYPE_FILES = "file/*";
    var MIME_TYPE_HTML = "text/html";

    /*------------------------------------ image region ------------------------------------*/

    fun tintDrawable(@DrawableRes drawableRes: Int, @ColorInt color: Int): Drawable {
        val drawable = UIX.getApp().resources.getDrawable(drawableRes)
        return tintDrawable(drawable, color)
    }

    /**
     * 对 Drawable 进行着色
     *
     * @param drawable 要着色的 Drawable
     * @param color    颜色
     * @return         着色之后的 Drawable
     */
    fun tintDrawable(drawable: Drawable, @ColorInt color: Int): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable.mutate())
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color))
        return wrappedDrawable
    }

    /*------------------------------------ view region ------------------------------------*/

    fun dp2px(dpValue: Float): Int {
        val scale = UIX.getApp().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        val scale = UIX.getApp().resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = UIX.getApp().resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale = UIX.getApp().resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        val wm = UIX.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }

    fun getScreenHeight(): Int {
        val wm = UIX.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    fun getStatusBarHeight(): Int {
        val resId = UIX.getApp().resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) UIX.getApp().resources.getDimensionPixelOffset(resId) else 0
    }

    fun getRootView(context: Activity): View {
        return (context.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }

    fun getSystemVisibility(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FLAG_VISIBILITY_19
        } else {
            FLAG_VISIBILITY_17
        }
    }

    fun exit(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            addFlags(activity.window.decorView, FLAG_IMMERSIVE)
        }
    }

    private fun addFlags(decorView: View, flags: Int) {
        decorView.systemUiVisibility = decorView.systemUiVisibility or flags
    }

    fun enter(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            clearFlags(activity.window.decorView, FLAG_IMMERSIVE)
        }
    }

    private fun clearFlags(view: View, flags: Int) {
        view.systemUiVisibility = view.systemUiVisibility and flags.inv()
        // & ~flags 清除view.getSystemUiVisibility()中的flags
    }

    /*------------------------------------ resource region ------------------------------------*/

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

    fun getThumbnailUri(mContext: Context, uri: Uri): Uri {
        val mimeType = getMimeType(mContext, uri)
        if (!TextUtils.isEmpty(mimeType)) {
            val type = mimeType!!.split("/")[0]
            val subtype = mimeType.split("/")[1]
            d(mimeType)
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

    /*------------------------------------ io region ------------------------------------*/

    fun is2Bytes(ins: InputStream?): ByteArray {
        if (ins == null) return ByteArray(0)
        var os: ByteArrayOutputStream? = null
        return try {
            os = ByteArrayOutputStream()
            val b = ByteArray(sBufferSize)
            var len: Int = ins.read(b, 0, sBufferSize)
            while (len != -1) {
                os.write(b, 0, len)
                len = ins.read(b, 0, sBufferSize)
            }
            os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            ByteArray(0)
        } finally {
            safeCloseAll(ins, os)
        }
    }

    private fun safeCloseAll(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            try {
                closeable?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /*------------------------------------ biz region ------------------------------------*/

    fun getAddressList(): List<AddressBean> {
        if (addresses != null) return addresses!!
        val ins = getAssets().open("province.json")
        val bytes = is2Bytes(ins)
        val content = String(bytes)
        val list = ArrayList<AddressBean>()
        val gson = Gson()
        try {
            val array = JsonParser().parse(content).asJsonArray
            for (jsonElement in array) {
                list.add(gson.fromJson(jsonElement, AddressBean::class.java))
            }
            addresses = list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    /*------------------------------------ log region ------------------------------------*/

    fun d(s: String) {
        if (debug) {
            Log.d("UIX", s)
        }
    }

    fun d(o: Any) {
        if (debug) {
            Log.d("UIX", "" + o)
        }
    }
}