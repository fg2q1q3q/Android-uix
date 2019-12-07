package me.shouheng.uix.dialog.content

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.Size
import android.view.Gravity
import android.view.View
import android.widget.TextView
import me.shouheng.uix.R

/**
 * 对话框内容的基类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 09:46
 */
class SimpleContent private constructor(): IDialogContent {

    private var content: CharSequence? = null
    @ColorInt private var contentColor: Int? = null
    @Size private var contentSize: Float = 16f
    private var typeFace: Int = Typeface.NORMAL
    private var gravity: Int = Gravity.CENTER

    override fun getView(ctx: Context): View {
        val tv = View.inflate(ctx, R.layout.uix_dialog_content_simple, null) as TextView
        tv.text = content
        tv.textSize = contentSize
        tv.gravity = gravity
        if (contentColor != null) tv.setTextColor(contentColor!!)
        tv.setTypeface(null, typeFace)
        return tv
    }

    class Builder {
        private var content: CharSequence? = null
        @ColorInt private var contentColor: Int? = null
        @Size private var contentSize: Float = 16f
        private var typeFace: Int = Typeface.NORMAL
        private var gravity: Int = Gravity.CENTER

        fun setContent(content: CharSequence): Builder {
            this.content = content
            return this
        }

        fun setContentColor(@ColorInt contentColor: Int): Builder {
            this.contentColor = contentColor
            return this
        }

        fun setContentSize(contentSize: Float): Builder {
            this.contentSize = contentSize
            return this
        }

        fun setContentTypeface(typeFace: Int): Builder {
            this.typeFace = typeFace
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun build(): SimpleContent {
            val simpleContent = SimpleContent()
            simpleContent.content = content
            simpleContent.contentColor = contentColor
            simpleContent.contentSize = contentSize
            simpleContent.typeFace = typeFace
            simpleContent.gravity = gravity
            return simpleContent
        }
    }
}