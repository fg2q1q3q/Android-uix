package me.shouheng.uix.widget.dialog.footer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import me.shouheng.uix.common.anno.BottomButtonPosition
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_MIDDLE
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.common.anno.BottomButtonStyle
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UColor
import me.shouheng.uix.common.utils.UImage
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.uix.widget.databinding.UixDialogFooterSimpleBinding
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.title.IDialogTitle
import me.shouheng.utils.ktx.gone

/**
 * Simple dialog footer
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-15 11:44
 */
class SimpleFooter private constructor(): ViewBindingDialogFooter<UixDialogFooterSimpleBinding>() {

    private lateinit var dialog: BeautyDialog
    private var dialogContent: IDialogContent? = null
    private var dialogTitle: IDialogTitle? = null

    @BottomButtonStyle
    private var bottomStyle: Int? = BUTTON_STYLE_DOUBLE

    private var leftText: CharSequence? = null
    private var leftTextStyle = GlobalConfig.leftTextStyle
    private var middleText: CharSequence? = null
    private var middleTextStyle = GlobalConfig.middleTextStyle
    private var rightText: CharSequence? = null
    private var rightTextStyle = GlobalConfig.rightTextStyle

    private var dividerColor: Int? = null
    private var clickListener: ((dialog: BeautyDialog,
                                 position: Int,
                                 title: IDialogTitle?,
                                 content: IDialogContent?) -> Unit)? = null
    private var onClickListener: OnClickListener? = null

    override fun doCreateView(ctx: Context) {
        binding.tvLeft.text = leftText
        binding.tvMiddle.text = middleText
        binding.tvRight.text = rightText

        binding.tvLeft.setStyle(leftTextStyle, GlobalConfig.leftTextStyle)
        binding.tvMiddle.setStyle(middleTextStyle, GlobalConfig.middleTextStyle)
        binding.tvRight.setStyle(rightTextStyle, GlobalConfig.rightTextStyle)

        binding.tvLeft.setOnClickListener {
            onClickListener?.onClick(dialog, BUTTON_POS_LEFT, dialogTitle, dialogContent)
            clickListener?.invoke(dialog, BUTTON_POS_LEFT, dialogTitle, dialogContent)
        }
        binding.tvMiddle.setOnClickListener {
            onClickListener?.onClick(dialog, BUTTON_POS_MIDDLE, dialogTitle, dialogContent)
            clickListener?.invoke(dialog, BUTTON_POS_MIDDLE, dialogTitle, dialogContent)
        }
        binding.tvRight.setOnClickListener {
            onClickListener?.onClick(dialog, BUTTON_POS_RIGHT, dialogTitle, dialogContent)
            clickListener?.invoke(dialog, BUTTON_POS_RIGHT, dialogTitle, dialogContent)
        }

        val cornerRadius = dialog.dialogCornerRadius.toFloat()
        val normalColor = if (dialog.dialogDarkStyle) BeautyDialog.GlobalConfig.darkBGColor else BeautyDialog.GlobalConfig.lightBGColor
        val selectedColor = UColor.computeColor(normalColor, if (dialog.dialogDarkStyle) Color.WHITE else Color.BLACK, NormalButton.GlobalConfig.fraction)

        binding.tvLeft.background = StateListDrawable().apply {
            val normalDrawable = UImage.getGradientDrawable(normalColor, 0f, 0f, cornerRadius, 0f)
            val selectedDrawable = UImage.getGradientDrawable(selectedColor, 0f, 0f, cornerRadius, 0f)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }
        binding.tvMiddle.background = StateListDrawable().apply {
            val radius = if (bottomStyle == BUTTON_STYLE_SINGLE) cornerRadius else 0f
            val normalDrawable = UImage.getGradientDrawable(normalColor, 0f, 0f, radius, radius)
            val selectedDrawable = UImage.getGradientDrawable(selectedColor, 0f, 0f, radius, radius)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }
        binding.tvRight.background = StateListDrawable().apply {
            val normalDrawable = UImage.getGradientDrawable(normalColor, 0f, 0f, 0f, cornerRadius)
            val selectedDrawable = UImage.getGradientDrawable(selectedColor, 0f, 0f, 0f, cornerRadius)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }

        // 优先使用构建者模式中传入的颜色，如果没有再使用全局配置的颜色，还是没有就使用默认颜色
        val finalDividerColor =
                if (dividerColor == null) {
                    if (GlobalConfig.dividerColor == null) selectedColor
                    else GlobalConfig.dividerColor!!
                } else dividerColor!!
        binding.v1.setBackgroundColor(finalDividerColor)
        binding.v2.setBackgroundColor(finalDividerColor)
        binding.h.setBackgroundColor(finalDividerColor)

        when(bottomStyle) {
            BUTTON_STYLE_SINGLE -> {
                binding.tvLeft.gone()
                binding.tvRight.gone()
                binding.v1.gone()
                binding.v2.gone()
            }
            BUTTON_STYLE_DOUBLE -> {
                binding.tvMiddle.gone()
                binding.v2.gone()
            }
            else -> { /* do nothing */ }
        }
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

    interface OnClickListener {
        fun onClick(dialog: BeautyDialog,
                    @BottomButtonPosition buttonPos: Int,
                    dialogTitle: IDialogTitle?,
                    dialogContent: IDialogContent?)
    }

    class Builder {

        @BottomButtonStyle
        private var bottomStyle: Int? = BUTTON_STYLE_DOUBLE

        private var leftText: CharSequence? = null
        private var leftTextStyle = GlobalConfig.leftTextStyle
        private var middleText: CharSequence? = null
        private var middleTextStyle = GlobalConfig.middleTextStyle
        private var rightText: CharSequence? = null
        private var rightTextStyle = GlobalConfig.rightTextStyle

        private var dividerColor: Int? = null

        private var clickListener: ((dialog: BeautyDialog,
                                     position: Int,
                                     title: IDialogTitle?,
                                     content: IDialogContent?) -> Unit)? = null

        private var onClickListener: OnClickListener? = null

        fun setBottomStyle(@BottomButtonStyle bottomStyle: Int): Builder {
            this.bottomStyle = bottomStyle
            return this
        }

        fun setLeftText(leftText: CharSequence): Builder {
            this.leftText = leftText
            return this
        }

        fun setLeftTextStyle(leftTextStyle: TextStyleBean): Builder {
            this.leftTextStyle = leftTextStyle
            return this
        }

        fun setMiddleText(middleText: CharSequence): Builder {
            this.middleText = middleText
            return this
        }

        fun setMiddleTextStyle(middleTextStyle: TextStyleBean): Builder {
            this.middleTextStyle = middleTextStyle
            return this
        }


        fun setRightText(rightText: CharSequence): Builder {
            this.rightText = rightText
            return this
        }

        fun setRightTextStyle(rightTextStyle: TextStyleBean): Builder {
            this.rightTextStyle = rightTextStyle
            return this
        }

        fun setDividerColor(@ColorInt dividerColor: Int): Builder {
            this.dividerColor = dividerColor
            return this
        }

        fun setOnClickListener(
                clickListener: (dialog: BeautyDialog, position: Int, title: IDialogTitle?, content: IDialogContent?) -> Unit
        ): Builder {
            this.clickListener = clickListener
            return this
        }

        fun setOnClickListener(onClickListener: OnClickListener): Builder {
            this.onClickListener = onClickListener
            return this
        }

        fun build(): SimpleFooter {
            val bottom = SimpleFooter()
            bottom.leftText = leftText
            bottom.leftTextStyle = leftTextStyle
            bottom.middleText = middleText
            bottom.middleTextStyle = middleTextStyle
            bottom.rightText = rightText
            bottom.rightTextStyle = rightTextStyle
            bottom.bottomStyle = bottomStyle
            bottom.dividerColor = dividerColor
            bottom.clickListener = clickListener
            bottom.onClickListener = onClickListener
            return bottom
        }
    }

    object GlobalConfig {
        var leftTextStyle = TextStyleBean()
        var middleTextStyle = TextStyleBean()
        var rightTextStyle = TextStyleBean()
        /** 按钮底部的分割线的颜色 */
        @ColorInt var dividerColor: Int? = null
    }
}