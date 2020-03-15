package me.shouheng.uix.widget.dialog.title

import android.content.Context
import android.view.Gravity
import android.view.View
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 对话框标题的基类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 09:46
 */
class SimpleTitle private constructor(): IDialogTitle {

    private var title: CharSequence? = null
    private var titleStyle: TextStyleBean = GlobalConfig.titleStyle

    override fun getView(ctx: Context): View {
        val tv = View.inflate(ctx, R.layout.uix_dialog_title_simple, null) as NormalTextView
        tv.text = title
        tv.setStyle(titleStyle, GlobalConfig.titleStyle)
        return tv
    }

    class Builder {
        private var title: CharSequence? = null
        private var titleStyle: TextStyleBean = GlobalConfig.titleStyle

        fun setTitle(title: CharSequence): Builder {
            this.title = title
            return this
        }

        fun setTitleStyle(titleStyle: TextStyleBean): Builder {
            this.titleStyle = titleStyle
            return this
        }

        fun build(): SimpleTitle {
            val simpleTitle = SimpleTitle()
            simpleTitle.title = title
            simpleTitle.titleStyle = titleStyle
            return simpleTitle
        }
    }

    companion object {

        fun get(title: CharSequence): SimpleTitle = Builder().setTitle(title).build()

        fun builder(): Builder = Builder()
    }

    object GlobalConfig {
        var titleStyle = TextStyleBean().apply { gravity=Gravity.CENTER }
    }
}