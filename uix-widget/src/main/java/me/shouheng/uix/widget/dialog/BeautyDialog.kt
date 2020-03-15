package me.shouheng.uix.widget.dialog

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import me.shouheng.uix.common.anno.DialogPosition
import me.shouheng.uix.common.anno.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.common.anno.DialogPosition.Companion.POS_CENTER
import me.shouheng.uix.common.anno.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.common.anno.DialogStyle
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_MATCH
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_WRAP
import me.shouheng.uix.common.utils.UIXImageUtils
import me.shouheng.uix.common.utils.UIXResUtils
import me.shouheng.uix.common.utils.UIXViewUtils
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.footer.IDialogFooter
import me.shouheng.uix.widget.dialog.listener.OnDismissListener
import me.shouheng.uix.widget.dialog.listener.OnShowListener
import me.shouheng.uix.widget.dialog.title.IDialogTitle

/**
 * 对话框
 *
 * 示例程序：
 *
 * @sample
 *
 * ```java
 *     BeautyDialog.Builder()
 *             .setDialogTitle(SimpleTitle.Builder()
 *                     .setTitle("普通编辑对话框")
 *                     .build())
 *             .setDialogContent(SimpleEditor.Builder()
 *                     .setClearDrawable(ResUtils.getDrawable(R.drawable.ic_cancel_black_24dp))
 *                     .build())
 *             .setDialogBottom(SimpleFooter.Builder()
 *                     .setBottomStyle(BUTTON_THREE)
 *                     .setLeftText("Left")
 *                     .setMiddleText("Middle")
 *                     .setRightText("Right")
 *                     .build())
 *             .build().show(supportFragmentManager, "editor")
 * ```
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-12 18:35
 */
class BeautyDialog : DialogFragment() {

    private var iDialogTitle: IDialogTitle? = null
    private var iDialogContent: IDialogContent? = null
    private var iDialogFooter: IDialogFooter? = null

    private var dialogPosition: Int = POS_CENTER
    private var dialogStyle: Int = STYLE_MATCH
    var dialogDarkStyle: Boolean = false
        private set

    private var outCancelable = GlobalConfig.outCancelable
    private var backCancelable = GlobalConfig.backCancelable
    private var customBackground = false

    private var onDismissListener: OnDismissListener? = null
    private var onShowListener: OnShowListener? = null

    private var fixedHeight = 0
    private var dialogMargin = GlobalConfig.margin
    private var dialogBackground: Drawable? = null
    var dialogCornerRadius = GlobalConfig.cornerRadius
        private set

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(context!!,
                if (dialogStyle == STYLE_WRAP) R.style.BeautyDialogWrap
                else R.style.BeautyDialog
        ).create()

        val content = View.inflate(context, R.layout.uix_dialog_layout, null)
        if (customBackground) {
            content.background = dialogBackground
        } else {
            content.background = UIXImageUtils.getGradientDrawable(
                    if (dialogDarkStyle) GlobalConfig.darkBGColor else GlobalConfig.lightBGColor,
                    UIXViewUtils.dp2px(dialogCornerRadius.toFloat()).toFloat()
            )
        }
        val llTitle = content.findViewById<LinearLayout>(R.id.ll_title)
        val llContent = content.findViewById<LinearLayout>(R.id.ll_content)
        val llFooter = content.findViewById<LinearLayout>(R.id.ll_footer)

        iDialogTitle?.setDialog(this)
        iDialogContent?.setDialog(this)
        iDialogFooter?.setDialog(this)
        iDialogTitle?.setDialogContent(iDialogContent)
        iDialogTitle?.setDialogFooter(iDialogFooter)
        iDialogContent?.setDialogTitle(iDialogTitle)
        iDialogContent?.setDialogFooter(iDialogFooter)
        iDialogFooter?.setDialogTitle(iDialogTitle)
        iDialogFooter?.setDialogContent(iDialogContent)

        if (iDialogTitle != null) {
            val titleView = iDialogTitle!!.getView(context!!)
            llTitle.addView(titleView)
            val lp = titleView.layoutParams
            lp.height = WRAP_CONTENT
            titleView.layoutParams = lp
        } else {
            llTitle.visibility = View.GONE
        }
        if (iDialogContent != null) {
            val contentView = iDialogContent!!.getView(context!!)
            llContent.addView(contentView)
            val lp = contentView.layoutParams
            lp.height = if (fixedHeight == 0) MATCH_PARENT else fixedHeight
            contentView.layoutParams = lp
        } else {
            llContent.visibility = View.GONE
        }
        if (iDialogFooter != null) {
            val footerView = iDialogFooter!!.getView(context!!)
            llFooter.addView(footerView)
            val lp = footerView.layoutParams
            lp.height = WRAP_CONTENT
            footerView.layoutParams = lp
        } else {
            llFooter.visibility = View.GONE
        }

        when(dialogPosition) {
            POS_TOP -> {
                dialog.window?.setGravity(Gravity.TOP)
                dialog.window?.setWindowAnimations(R.style.DialogTopAnimation)
            }
            POS_CENTER -> {
                dialog.window?.setGravity(Gravity.CENTER)
                dialog.window?.setWindowAnimations(R.style.DialogCenterAnimation)
            }
            POS_BOTTOM -> {
                dialog.window?.setGravity(Gravity.BOTTOM)
                dialog.window?.setWindowAnimations(R.style.DialogBottomAnimation)
            }
        }

        dialog.setOnDismissListener { onDismissListener?.onOnDismiss(this) }
        dialog.setOnShowListener { onShowListener?.onShow(this) }

        dialog.setCanceledOnTouchOutside(outCancelable)
        dialog.setCancelable(backCancelable)

        val margin = UIXViewUtils.dp2px(dialogMargin.toFloat())
        dialog.setView(content, margin, margin, margin, margin)

        return dialog
    }

    class Builder {

        private var iDialogTitle: IDialogTitle? = null
        private var iDialogContent: IDialogContent? = null
        private var iDialogFooter: IDialogFooter? = null

        private var dialogPosition: Int = POS_CENTER
        private var dialogStyle: Int = STYLE_MATCH
        private var dialogDarkStyle: Boolean = false

        private var outCancelable = GlobalConfig.outCancelable
        private var backCancelable = GlobalConfig.backCancelable
        private var customBackground = false

        private var onDismissListener: OnDismissListener? = null
        private var onShowListener: OnShowListener? = null

        private var fixedHeight = 0
        private var dialogMargin: Float = GlobalConfig.margin
        private var dialogBackground: Drawable? = null
        private var dialogCornerRadius: Float = GlobalConfig.cornerRadius

        fun setDialogTitle(iDialogTitle: IDialogTitle): Builder {
            this.iDialogTitle = iDialogTitle
            return this
        }

        fun setDialogContent(iDialogContent: IDialogContent): Builder {
            this.iDialogContent = iDialogContent
            return this
        }

        fun setDialogBottom(iDialogFooter: IDialogFooter): Builder {
            this.iDialogFooter = iDialogFooter
            return this
        }

        fun setDialogPosition(@DialogPosition dialogPosition: Int): Builder {
            this.dialogPosition = dialogPosition
            return this
        }

        fun setDialogStyle(@DialogStyle dialogStyle: Int): Builder {
            this.dialogStyle = dialogStyle
            return this
        }

        fun setOutCancelable(outCancelable: Boolean): Builder {
            this.outCancelable = outCancelable
            return this
        }

        fun setBackCancelable(backCancelable: Boolean): Builder {
            this.backCancelable = backCancelable
            return this
        }

        fun setOnDismissListener(onDismissListener: OnDismissListener): Builder {
            this.onDismissListener = onDismissListener
            return this
        }

        fun setOnShowListener(onShowListener: OnShowListener): Builder {
            this.onShowListener = onShowListener
            return this
        }

        /**
         * 对话框"内容"的固定高度，单位 px
         */
        fun setFixedHeight(@Px fixedHeight: Int): Builder {
            this.fixedHeight = fixedHeight
            return this
        }

        /**
         * 对话框边距，单位 dp
         */
        fun setDialogMargin(dialogMargin: Float): Builder {
            this.dialogMargin = dialogMargin
            return this
        }

        fun setDarkDialog(darkDialog: Boolean): Builder {
            this.dialogDarkStyle = darkDialog
            return this
        }

        /**
         * 设置对话框的背景：
         * 1. 如果不调用这个方法将根据主题使用默认的背景，否则将会直接使用设置的背景，即使是 null.
         * 2. 当传入的参数为 null 的时候对话框将不使用任何背景。
         */
        fun setDialogBackground(dialogBackground: Drawable?): Builder {
            this.dialogBackground = dialogBackground
            customBackground = true
            return this
        }

        /**
         * 对话框的默认背景的边角的大小，单位：dp
         */
        fun setDialogCornerRadius(dialogCornerRadius: Float): Builder {
            this.dialogCornerRadius = dialogCornerRadius
            return this
        }

        fun build(): BeautyDialog {
            val dialog = BeautyDialog()
            dialog.iDialogTitle = iDialogTitle
            dialog.iDialogContent = iDialogContent
            dialog.iDialogFooter = iDialogFooter
            dialog.dialogPosition = dialogPosition
            dialog.dialogStyle = dialogStyle
            dialog.dialogDarkStyle = dialogDarkStyle
            dialog.outCancelable = outCancelable
            dialog.backCancelable = backCancelable
            dialog.onDismissListener = onDismissListener
            dialog.onShowListener = onShowListener
            dialog.fixedHeight = fixedHeight
            dialog.dialogMargin = dialogMargin
            dialog.dialogBackground = dialogBackground
            dialog.customBackground = customBackground
            dialog.dialogCornerRadius = dialogCornerRadius
            return dialog
        }
    }

    object GlobalConfig {
        /** 对话框边距，单位 dp */
        @Px
        var margin: Float                       = 20f
        /** 对话框圆角，单位 dp */
        @Px
        var cornerRadius: Float                 = 15f
        /** 对话框明主题背景色 */
        @ColorInt
        var lightBGColor: Int                   = UIXResUtils.getColor(R.color.uix_default_light_bg_color)
        /** 对话框暗主题背景色 */
        @ColorInt
        var darkBGColor: Int                    = UIXResUtils.getColor(R.color.uix_default_dark_bg_color)
        /** 点击对话框外部是否可以取消对话框的全局配置 */
        var outCancelable                       = true
        /** 点击返回按钮是否可以取消对话框的全局配置 */
        var backCancelable                      = true
    }
}