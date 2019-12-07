package me.shouheng.uix.dialog.content

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import me.shouheng.uix.R
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.footer.IDialogFooter
import me.shouheng.uix.dialog.title.IDialogTitle
import me.shouheng.uix.text.ClearEditText

/**
 * 编辑框
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-14 11:24
 */
class SimpleEditor private constructor(): IDialogContent, TextWatcher {

    private var content: String? = null
    private var hint: String? = null
    private var textColor: Int? = null
    private var hintTextColor: Int? = null
    private var lengthTextColor: Int? = null
    private var bottomLineColor: Int? = null
    private var textSize: Float = 16f
    private var singleLine = false
    private var numeric = false
    private var inputRegex: String? = null
    private var maxLength: Int? = null
    private var maxLines: Int? = null
    private var showLength = true
    private var clearDrawable: Drawable? = null

    private lateinit var et: ClearEditText
    private lateinit var tv: TextView

    private var dialogTitle: IDialogTitle? = null
    private var dialogFooter: IDialogFooter? = null

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_edit_simple, null)
        et = layout.findViewById(R.id.et)
        tv = layout.findViewById(R.id.tv)
        val v = layout.findViewById<View>(R.id.v)
        et.addTextChangedListener(this)
        et.setText(content)
        et.hint = hint
        if (textColor != null) et.setTextColor(textColor!!)
        if (hintTextColor != null) et.setHintTextColor(hintTextColor!!)
        if (lengthTextColor != null) tv.setTextColor(lengthTextColor!!)
        if (bottomLineColor != null) v.setBackgroundColor(bottomLineColor!!)
        et.textSize = textSize
        et.isSingleLine = singleLine
        if (numeric) et.addInputType(EditorInfo.TYPE_CLASS_NUMBER)
        if (inputRegex != null) et.inputRegex = inputRegex
        if (maxLength != null) et.addFilters(InputFilter.LengthFilter(maxLength!!))
        if (maxLines != null) et.maxLines = maxLines!!
        if (!showLength) tv.visibility = View.GONE
        if (clearDrawable != null) et.setClearDrawable(clearDrawable!!)
        return layout
    }

    override fun setDialog(dialog: BeautyDialog) {}

    fun getContent(): Editable? {
        return et.text
    }

    override fun afterTextChanged(s: Editable?) {
        if (showLength) {
            val text = if (maxLength != null) "${et.text?.length?:0}/${maxLength}" else "${et.text?.length?:0}"
            tv.text = text
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun setDialogTitle(dialogTitle: IDialogTitle?) {
        this.dialogTitle = dialogTitle
    }

    override fun setDialogFooter(dialogFooter: IDialogFooter?) {
        this.dialogFooter = dialogFooter
    }

    class Builder {
        private var content: String? = null
        private var hint: String? = null
        private var textColor: Int? = null
        private var hintTextColor: Int? = null
        private var lengthTextColor: Int? = null
        private var bottomLineColor: Int? = null
        private var textSize: Float = 16f
        private var singleLine = false
        private var numeric = false
        private var inputRegex: String? = null
        private var maxLength: Int? = null
        private var maxLines: Int? = null
        private var showLength = true
        private var clearDrawable: Drawable? = null

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setHint(hint: String): Builder {
            this.hint = hint
            return this
        }

        fun setTextColor(textColor: Int): Builder {
            this.textColor = textColor
            return this
        }

        fun setHintTextColor(hintTextColor: Int): Builder {
            this.hintTextColor = hintTextColor
            return this
        }

        fun setClearDrawable(clearDrawable: Drawable): Builder {
            this.clearDrawable = clearDrawable
            return this
        }

        fun setLengthTextColor(lengthTextColor: Int): Builder {
            this.lengthTextColor = lengthTextColor
            return this
        }

        fun setBottomLineColor(bottomLineColor: Int): Builder {
            this.bottomLineColor = bottomLineColor
            return this
        }

        fun setTextSize(textSize: Float): Builder {
            this.textSize = textSize
            return this
        }

        fun setSingleLine(singleLine: Boolean): Builder {
            this.singleLine = singleLine
            return this
        }

        fun setNumeric(numeric: Boolean): Builder {
            this.numeric = numeric
            return this
        }

        fun setInputRegex(inputRegex: String): Builder {
            this.inputRegex = inputRegex
            return this
        }

        fun setMaxLength(maxLength: Int): Builder {
            this.maxLength = maxLength
            return this
        }

        fun setMaxLines(maxLines: Int): Builder {
            this.maxLines = maxLines
            return this
        }

        fun setShowLength(showLength: Boolean): Builder {
            this.showLength = showLength
            return this
        }

        fun build(): SimpleEditor {
            val editor = SimpleEditor()
            editor.content = content
            editor.hint = hint
            editor.textColor = textColor
            editor.hintTextColor = hintTextColor
            editor.lengthTextColor = lengthTextColor
            editor.bottomLineColor = bottomLineColor
            editor.textSize = textSize
            editor.singleLine = singleLine
            editor.numeric = numeric
            editor.inputRegex = inputRegex
            editor.maxLength = maxLength
            editor.maxLines = maxLines
            editor.showLength = showLength
            editor.clearDrawable = clearDrawable
            return editor
        }
    }
}