package me.shouheng.uix.dialog.footer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.view.View
import android.widget.TextView
import me.shouheng.uix.R
import me.shouheng.uix.UIXConfig
import me.shouheng.uix.config.BottomButtonPosition
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_MIDDLE
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.config.BottomButtonStyle
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.content.IDialogContent
import me.shouheng.uix.dialog.title.IDialogTitle
import me.shouheng.uix.utils.UIXUtils

/**
 * 两个按钮的对话框底部
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-15 11:44
 */
class SimpleFooter private constructor(): IDialogFooter {

    @BottomButtonStyle
    private var bottomStyle: Int? = BUTTON_STYLE_DOUBLE

    private var leftText: CharSequence? = null
    private var middleText: CharSequence? = null
    private var rightText: CharSequence? = null

    private var leftTextSize: Float = 16f
    private var middleTextSize: Float = 16f
    private var rightTextSize: Float = 16f

    private var leftTextColor: Int? = null
    private var middleTextColor: Int? = null
    private var rightTextColor: Int? = null

    private var dividerColor: Int? = null
    private var onClickListener: OnClickListener? = null

    private lateinit var tvLeft: TextView
    private lateinit var tvMiddle: TextView
    private lateinit var tvRight: TextView
    private lateinit var v1: View
    private lateinit var v2: View
    private lateinit var h: View

    private lateinit var dialog: BeautyDialog
    private var dialogContent: IDialogContent? = null
    private var dialogTitle: IDialogTitle? = null

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_footer_simple, null)

        tvLeft = layout.findViewById(R.id.tv_left)
        tvMiddle = layout.findViewById(R.id.tv_middle)
        tvRight = layout.findViewById(R.id.tv_right)
        v1 = layout.findViewById(R.id.v1)
        v2 = layout.findViewById(R.id.v2)
        h = layout.findViewById(R.id.h)

        tvLeft.text = leftText
        tvMiddle.text = middleText
        tvRight.text = rightText

        tvLeft.textSize = leftTextSize
        tvMiddle.textSize = middleTextSize
        tvRight.textSize = rightTextSize

        tvLeft.setOnClickListener { onClickListener?.onClick(dialog, BUTTON_POS_LEFT, dialogTitle, dialogContent) }
        tvMiddle.setOnClickListener { onClickListener?.onClick(dialog, BUTTON_POS_MIDDLE, dialogTitle, dialogContent) }
        tvRight.setOnClickListener { onClickListener?.onClick(dialog, BUTTON_POS_RIGHT, dialogTitle, dialogContent) }

        val cornerRadius = dialog.dialogCornerRadius
        val normalColor = if (dialog.dialogDarkStyle) UIXConfig.Dialog.darkBGColor else UIXConfig.Dialog.lightBGColor
        val selectedColor = UIXUtils.computeColor(normalColor, if (dialog.dialogDarkStyle) Color.WHITE else Color.BLACK, UIXConfig.Button.fraction)
        tvLeft.background = StateListDrawable().apply {
            val normalDrawable = UIXUtils.getGradientDrawable(normalColor, 0f, 0f, cornerRadius.toFloat(), 0f)
            val selectedDrawable = UIXUtils.getGradientDrawable(selectedColor, 0f, 0f, cornerRadius.toFloat(), 0f)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }
        tvMiddle.background = StateListDrawable().apply {
            val radius = if (bottomStyle == BUTTON_STYLE_SINGLE) cornerRadius.toFloat() else 0f
            val normalDrawable = UIXUtils.getGradientDrawable(normalColor, 0f, 0f, radius, radius)
            val selectedDrawable = UIXUtils.getGradientDrawable(selectedColor, 0f, 0f, radius, radius)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }
        tvRight.background = StateListDrawable().apply {
            val normalDrawable = UIXUtils.getGradientDrawable(normalColor, 0f, 0f, 0f, cornerRadius.toFloat())
            val selectedDrawable = UIXUtils.getGradientDrawable(selectedColor, 0f, 0f, 0f, cornerRadius.toFloat())
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }

        if (leftTextColor != null) tvLeft.setTextColor(leftTextColor!!)
        if (middleTextColor != null) tvMiddle.setTextColor(middleTextColor!!)
        if (rightTextColor != null) tvRight.setTextColor(rightTextColor!!)

        // 优先使用构建者模式中传入的颜色，如果没有再使用全局配置的颜色，还是没有就使用默认颜色
        val finalDividerColor =
                if (dividerColor == null) {
                    if (UIXConfig.Dialog.dividerColor == null)
                        selectedColor
                    else UIXConfig.Dialog.dividerColor!!
                } else dividerColor!!
        v1.setBackgroundColor(finalDividerColor)
        v2.setBackgroundColor(finalDividerColor)
        h.setBackgroundColor(finalDividerColor)

        when(bottomStyle) {
            BUTTON_STYLE_SINGLE -> {
                tvLeft.visibility = View.GONE
                tvRight.visibility = View.GONE
                v1.visibility = View.GONE
                v2.visibility = View.GONE
            }
            BUTTON_STYLE_DOUBLE -> {
                tvMiddle.visibility = View.GONE
                v2.visibility = View.GONE
            }
            else -> { /* do nothing */ }
        }

        return layout
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    override fun setDialogTitle(dialogTitle: IDialogTitle?) {
        this.dialogTitle = dialogTitle
    }

    override fun setDialogContent(dialogContent: IDialogContent?) {
        this.dialogContent = dialogContent
    }

    /**
     * 对话框按钮的单击事件
     */
    interface OnClickListener {

        /**
         * 对话框的按钮的单击事件
         */
        fun onClick(dialog: BeautyDialog,
                    @BottomButtonPosition buttonPos: Int,
                    dialogTitle: IDialogTitle?,
                    dialogContent: IDialogContent?)
    }

    class Builder {

        @BottomButtonStyle
        private var bottomStyle: Int? = BUTTON_STYLE_DOUBLE

        private var leftText: CharSequence? = null
        private var middleText: CharSequence? = null
        private var rightText: CharSequence? = null

        private var leftTextSize: Float = 16f
        private var middleTextSize: Float = 16f
        private var rightTextSize: Float = 16f

        private var leftTextColor: Int? = null
        private var middleTextColor: Int? = null
        private var rightTextColor: Int? = null

        private var dividerColor: Int? = null
        private var onClickListener: OnClickListener? = null

        fun setBottomStyle(@BottomButtonStyle bottomStyle: Int): Builder {
            this.bottomStyle = bottomStyle
            return this
        }

        fun setLeftText(leftText: CharSequence): Builder {
            this.leftText = leftText
            return this
        }

        fun setMiddleText(middleText: CharSequence): Builder {
            this.middleText = middleText
            return this
        }

        fun setRightText(rightText: CharSequence): Builder {
            this.rightText = rightText
            return this
        }

        fun setLeftTextSize(leftTextSize: Float): Builder {
            this.leftTextSize = leftTextSize
            return this
        }

        fun setMiddleTextSize(middleTextSize: Float): Builder {
            this.middleTextSize = middleTextSize
            return this
        }

        fun setRightTextSize(rightTextSize: Float): Builder {
            this.rightTextSize = rightTextSize
            return this
        }

        fun setLeftTextColor(@ColorInt leftTextColor: Int): Builder {
            this.leftTextColor = leftTextColor
            return this
        }

        fun setMiddleTextColor(@ColorInt middleTextColor: Int): Builder {
            this.middleTextColor = middleTextColor
            return this
        }

        fun setRightTextColor(@ColorInt rightTextColor: Int): Builder {
            this.rightTextColor = rightTextColor
            return this
        }

        fun setDividerColor(@ColorInt dividerColor: Int): Builder {
            this.dividerColor = dividerColor
            return this
        }

        fun setOnClickListener(onClickListener: OnClickListener): Builder {
            this.onClickListener = onClickListener
            return this
        }

        fun build(): SimpleFooter {
            val bottom = SimpleFooter()
            bottom.bottomStyle = bottomStyle
            bottom.leftText = leftText
            bottom.middleText = middleText
            bottom.rightText = rightText
            bottom.leftTextSize = leftTextSize
            bottom.middleTextSize = middleTextSize
            bottom.rightTextSize = rightTextSize
            bottom.leftTextColor = leftTextColor
            bottom.middleTextColor = middleTextColor
            bottom.rightTextColor = rightTextColor
            bottom.dividerColor = dividerColor
            bottom.onClickListener = onClickListener
            return bottom
        }
    }
}