package me.shouheng.uix.dialog.title

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.Size
import android.view.Gravity
import android.view.View
import android.widget.TextView
import me.shouheng.uix.R

/**
 * 对话框标题的基类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 09:46
 */
class SimpleTitle private constructor(): IDialogTitle {

    private var title: String? = null
    @ColorInt private var titleColor: Int? = null
    @Size private var titleSize: Float = 16f
    private var typeFace: Int = Typeface.NORMAL
    private var gravity: Int = Gravity.CENTER

    override fun getView(ctx: Context): View {
        val tv = View.inflate(ctx, R.layout.uix_dialog_title_simple, null) as TextView
        tv.text = title
        tv.textSize = titleSize
        tv.gravity = gravity
        if (titleColor != null) tv.setTextColor(titleColor!!)
        tv.setTypeface(null, typeFace)
        return tv
    }

    class Builder {
        private var title: String? = null
        @ColorInt private var titleColor: Int? = null
        @Size private var titleSize: Float = 16f
        private var typeFace: Int = Typeface.NORMAL
        private var gravity: Int = Gravity.CENTER

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setTitleColor(@ColorInt titleColor: Int): Builder {
            this.titleColor = titleColor
            return this
        }

        fun setTitleSize(titleSize: Float): Builder {
            this.titleSize = titleSize
            return this
        }

        fun setTitleTypeface(typeFace: Int): Builder {
            this.typeFace = typeFace
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun build(): SimpleTitle {
            val simpleTitle = SimpleTitle()
            simpleTitle.title = title
            simpleTitle.titleColor = titleColor
            simpleTitle.titleSize = titleSize
            simpleTitle.typeFace = typeFace
            simpleTitle.gravity = gravity
            return simpleTitle
        }
    }
}