package me.shouheng.suix.comn

import android.os.Build
import android.text.Html
import android.text.Spanned

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
}