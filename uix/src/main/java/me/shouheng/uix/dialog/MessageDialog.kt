package me.shouheng.uix.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import me.shouheng.uix.R
import me.shouheng.uix.UIXConfig
import me.shouheng.uix.config.EmptyLoadingStyle
import me.shouheng.uix.utils.UIXUtils

/**
 * 加载对话框
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2020-01-12 17:56
 */
class MessageDialog private constructor(builder: Builder) {

    private val message: CharSequence?
    private val textColor: Int
    private val textSize: Float
    private val typeFace: Int
    @EmptyLoadingStyle
    private val loadingStyle: Int
    private val cancelable: Boolean
    private val icon: Drawable?
    @ColorInt
    private val bgColor: Int
    private val isAnimation: Boolean
    private val borderRadiusInDp: Float

    init {
        message = builder.message
        textColor = builder.textColor
        textSize = builder.textSize
        typeFace = builder.typeFace
        loadingStyle = builder.loadingStyle
        cancelable = builder.cancelable
        isAnimation = builder.isAnimation
        icon = builder.icon
        bgColor = builder.bgColor
        borderRadiusInDp = builder.borderRadiusInDp
    }

    private fun build(context: Context): Dialog {
        val v = LayoutInflater.from(context).inflate(R.layout.uix_message_dialog, null)
        val ll = v.findViewById(R.id.loading_dialog_view) as LinearLayout
        val img = v.findViewById(R.id.img) as ImageView
        val pb = v.findViewById(R.id.pb) as ProgressBar
        val tv = v.findViewById(R.id.tipTextView) as TextView

        ll.background = UIXUtils.getGradientDrawable(bgColor, UIXUtils.dp2px(borderRadiusInDp).toFloat())

        if (icon != null) img.setImageDrawable(icon)
        else img.visibility = View.GONE

        pb.visibility = if (isAnimation) View.VISIBLE else View.GONE
        if (loadingStyle == EmptyLoadingStyle.STYLE_IOS) {
            pb.indeterminateDrawable = UIXUtils.getDrawable(R.drawable.uix_anim_loading)
        }

        tv.text = message
        tv.textSize = textSize
        tv.setTextColor(textColor)
        tv.setTypeface(null, typeFace)

        val dlg = Dialog(context, if (cancelable) R.style.Dialog_Loading_Cancelable else R.style.Dialog_Loading)
        dlg.setCancelable(cancelable)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        dlg.setContentView(ll, params)
        return dlg
    }

    class Builder {
        var message: CharSequence?      = null
            private set
        var textColor: Int              = UIXConfig.Dialog.msgTextColor
            private set
        var textSize: Float             = UIXConfig.Dialog.msgTextSize
            private set
        var typeFace: Int               = UIXConfig.Dialog.msgTypeFace
            private set
        var loadingStyle: Int           = UIXConfig.Dialog.msgLoadingStyle
            private set
        var cancelable: Boolean         = UIXConfig.Dialog.msgCancelable
            private set
        var icon: Drawable?             = null
            private set
        var bgColor: Int                = UIXConfig.Dialog.msgBgColor
            private set
        var isAnimation: Boolean        = UIXConfig.Dialog.msgAnimation
            private set
        var borderRadiusInDp: Float     = UIXConfig.Dialog.msgBgBorderRadiusInDp
            private set

        fun withMessage(param: CharSequence): Builder {
            message = param
            return this
        }

        fun withTextColor(param: Int): Builder {
            textColor = param
            return this
        }

        fun withTextSize(param: Float): Builder {
            textSize = param
            return this
        }

        fun withTypeFace(param: Int): Builder {
            typeFace = param
            return this
        }

        fun withLoadingStyle(@EmptyLoadingStyle param: Int): Builder {
            loadingStyle = param
            return this
        }

        fun withCancelable(param: Boolean): Builder {
            cancelable = param
            return this
        }

        fun withAnimation(param: Boolean): Builder {
            isAnimation = param
            return this
        }

        fun withIcon(param: Drawable?): Builder {
            icon = param
            return this
        }

        fun withBgColor(@ColorInt param: Int): Builder {
            bgColor = param
            return this
        }

        fun withBorderRadiusInDp(param: Float): Builder {
            borderRadiusInDp = param
            return this
        }

        fun build(context: Context): Dialog {
            return MessageDialog(this).build(context)
        }
    }

    companion object {

        fun showLoading(context: Context,
                        msg: String,
                        cancelable: Boolean = false,
                        isAnimation: Boolean = true,
                        icon: Drawable? = null,
                        @EmptyLoadingStyle loadingStyle: Int = EmptyLoadingStyle.STYLE_IOS): Dialog =
                Builder()
                        .withMessage(msg)
                        .withCancelable(cancelable)
                        .withAnimation(isAnimation)
                        .withIcon(icon)
                        .withLoadingStyle(loadingStyle)
                        .build(context)

        fun showLoading(context: Context,
                        @StringRes msgResId: Int,
                        cancelable: Boolean = false,
                        isAnimation: Boolean = true,
                        @DrawableRes iconResId: Int? = null,
                        @EmptyLoadingStyle loadingStyle: Int = EmptyLoadingStyle.STYLE_IOS): Dialog =
                Builder()
                        .withMessage(UIXUtils.getString(msgResId))
                        .withCancelable(cancelable)
                        .withAnimation(isAnimation)
                        .withLoadingStyle(loadingStyle)
                        .apply {
                            if (iconResId != null) {
                                this.withIcon(UIXUtils.getDrawable(iconResId))
                            }
                        }
                        .build(context)

        fun builder(): Builder = Builder()

        fun builder(msg: String,
                    cancelable: Boolean = false,
                    isAnimation: Boolean = true,
                    icon: Drawable? = null,
                    @EmptyLoadingStyle loadingStyle: Int = EmptyLoadingStyle.STYLE_IOS): Builder =
                Builder()
                        .withMessage(msg)
                        .withCancelable(cancelable)
                        .withAnimation(isAnimation)
                        .withIcon(icon)
                        .withLoadingStyle(loadingStyle)

        fun builder(@StringRes msgResId: Int,
                    cancelable: Boolean = false,
                    isAnimation: Boolean = true,
                    @DrawableRes iconResId: Int? = null,
                    @EmptyLoadingStyle loadingStyle: Int = EmptyLoadingStyle.STYLE_IOS): Builder =
                Builder()
                        .withMessage(UIXUtils.getString(msgResId))
                        .withCancelable(cancelable)
                        .withAnimation(isAnimation)
                        .withLoadingStyle(loadingStyle)
                        .apply {
                            if (iconResId != null) {
                                this.withIcon(UIXUtils.getDrawable(iconResId))
                            }
                        }

        fun hide(dialog: Dialog) {
            if (dialog.isShowing) dialog.dismiss()
        }
    }
}
