package me.shouheng.uix.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import me.shouheng.uix.R
import me.shouheng.uix.config.DialogPosition
import me.shouheng.uix.config.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.config.DialogPosition.Companion.POS_CENTER
import me.shouheng.uix.config.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.config.DialogStyle
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_MATCH
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_WRAP
import me.shouheng.uix.dialog.content.IDialogContent
import me.shouheng.uix.dialog.footer.IDialogFooter
import me.shouheng.uix.dialog.listener.OnDismissListener
import me.shouheng.uix.dialog.listener.OnShowListener
import me.shouheng.uix.dialog.title.IDialogTitle
import me.shouheng.uix.utils.UIXUtils

/**
 * 对话框
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
    private var dialogDarkStyle: Boolean = false

    private var outCancelable = false
    private var backCancelable = true

    private var onDismissListener: OnDismissListener? = null
    private var onShowListener: OnShowListener? = null

    private var fixedHeight = 0
    private var dialogMargin = UIXUtils.dp2px(15f)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(context!!,
                if (dialogStyle == STYLE_WRAP) R.style.BeautyDialogWrap
                else R.style.BeautyDialog
        ).create()

        val content = View.inflate(context, R.layout.uix_dialog_layout, null)
        if (dialogStyle != DialogStyle.STYLE_WRAP_WITHOUT_BG) {
            content.setBackgroundResource(
                    if (dialogDarkStyle) R.drawable.uix_bg_dialog_center_dark
                    else R.drawable.uix_bg_dialog_center
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
            lp.height = MATCH_PARENT
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
                dialog.window?.setWindowAnimations(R.style.dialogTopAnimation)
            }
            POS_CENTER -> {
                dialog.window?.setGravity(Gravity.CENTER)
                dialog.window?.setWindowAnimations(R.style.dialogCenterAnimation)
            }
            POS_BOTTOM -> {
                dialog.window?.setGravity(Gravity.BOTTOM)
                dialog.window?.setWindowAnimations(R.style.dialogBottomAnimation)
            }
        }

        dialog.setOnDismissListener { onDismissListener?.onOnDismiss(this) }
        dialog.setOnShowListener { onShowListener?.onShow(this) }

        dialog.setCanceledOnTouchOutside(outCancelable)
        dialog.setCancelable(backCancelable)

        dialog.setView(content, dialogMargin, dialogMargin, dialogMargin, dialogMargin)

        return dialog
    }

    class Builder {

        private var iDialogTitle: IDialogTitle? = null
        private var iDialogContent: IDialogContent? = null
        private var iDialogFooter: IDialogFooter? = null

        private var dialogPosition: Int = POS_CENTER
        private var dialogStyle: Int = STYLE_MATCH
        private var dialogDarkStyle: Boolean = false

        private var outCancelable = false
        private var backCancelable = true

        private var onDismissListener: OnDismissListener? = null
        private var onShowListener: OnShowListener? = null

        private var fixedHeight = 0
        private var dialogMargin = UIXUtils.dp2px(15f)

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

        fun setFixedHeight(fixedHeight: Int): Builder {
            this.fixedHeight = fixedHeight
            return this
        }

        fun setDialogMargin(dialogMargin: Int): Builder {
            this.dialogMargin = dialogMargin
            return this
        }

        fun setDarkDialog(darkDialog: Boolean): Builder {
            this.dialogDarkStyle = darkDialog
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
            return dialog
        }
    }
}