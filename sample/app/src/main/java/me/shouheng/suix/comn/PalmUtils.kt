package me.shouheng.suix.comn

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.text.Html
import android.text.Spanned
import me.shouheng.suix.BuildConfig
import java.io.File


/**
 * 各种工具的收集地方，如果具备通用性，则考虑将其以到 AndroidUtils 中
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019/12/4 22:17
 */
object PalmUtils {

    fun getTextFromHtml(content: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
        else Html.fromHtml(content)
    }

    fun getUriFromFile(context: Context, file: File): Uri {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}