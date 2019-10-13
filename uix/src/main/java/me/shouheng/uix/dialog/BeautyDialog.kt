package me.shouheng.uix.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import me.shouheng.uix.R

/**
 * 对话框
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-12 18:35
 */
class BeautyDialog : DialogFragment() {

    private var iDialogTitle: IDialogTitle? = null

    private var iDialogContent: IDialogContent? = null

    private var iDialogBottom: IDialogBottom? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val content = View.inflate(context, R.layout.uix_dialog_layout, null)
        val dialog = AlertDialog.Builder(context!!).create()
        return super.onCreateDialog(savedInstanceState)
    }

    class Builder() {

        private var iDialogTitle: IDialogTitle? = null

        private var iDialogContent: IDialogContent? = null

        private var iDialogBottom: IDialogBottom? = null

        fun setDialogTitle(iDialogTitle: IDialogTitle): Builder {
            this.iDialogTitle = iDialogTitle;
            return this
        }

        fun setDialogContent(iDialogContent: IDialogContent): Builder {
            this.iDialogContent = iDialogContent;
            return this
        }

        fun setDialogBottom(iDialogBottom: IDialogBottom): Builder {
            this.iDialogBottom = iDialogBottom;
            return this
        }

        fun build(): BeautyDialog {
            val dialog = BeautyDialog()
            dialog.iDialogTitle = iDialogTitle
            dialog.iDialogContent = iDialogContent
            dialog.iDialogBottom = iDialogBottom
            return dialog
        }
    }
}

/**
 * 对话框顶部的抽象接口
 */
interface IDialogTitle {

    /**
     * 获取控件
     */
    fun getView(): View
}

/**
 * 对话框内容的抽象接口
 */
interface IDialogContent {

    /**
     * 获取控件
     */
    fun getView(): View
}

/**
 * 对话框地步的抽象接口
 */
interface IDialogBottom {

    /**
     * 获取控件
     */
    fun getView(): View
}