package me.shouheng.uix.utils

import android.content.Context
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.DisplayMetrics
import android.view.WindowManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import me.shouheng.uix.UIX
import me.shouheng.uix.bean.AddressBean
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream

/**
 * UIX 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UIXUtils {

    private var sBufferSize = 8192
    private var addresses: List<AddressBean>? = null

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

    /*------------------------------------ io region ------------------------------------*/

    fun is2Bytes(`is`: InputStream?): ByteArray {
        if (`is` == null) return ByteArray(0)
        var os: ByteArrayOutputStream? = null
        return try {
            os = ByteArrayOutputStream()
            val b = ByteArray(sBufferSize)
            var len: Int = `is`.read(b, 0, sBufferSize)
            while (len != -1) {
                os.write(b, 0, len)
                len = `is`.read(b, 0, sBufferSize)
            }
            os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            ByteArray(0)
        } finally {
            safeCloseAll(`is`, os)
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
}