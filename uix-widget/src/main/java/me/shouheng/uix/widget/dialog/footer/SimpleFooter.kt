package me.shouheng.uix.widget.dialog.footer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.view.View
import me.shouheng.uix.common.anno.BottomButtonPosition
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_MIDDLE
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.common.anno.BottomButtonStyle
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UIXColorUtils
import me.shouheng.uix.common.utils.UIXImageUtils
import me.shouheng.uix.common.utils.UIXViewUtils
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.title.IDialogTitle
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 对话框底部按钮
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-15 11:44
 */
class SimpleFooter private constructor(): IDialogFooter {

    @BottomButtonStyle
    private var bottomStyle: Int? = BUTTON_STYLE_DOUBLE

    private var leftText: CharSequence? = null
    private var leftTextStyle = GlobalConfig.leftTextStyle
    private var middleText: CharSequence? = null
    private var middleTextStyle = GlobalConfig.middleTextStyle
    private var rightText: CharSequence? = null
    private var rightTextStyle = GlobalConfig.rightTextStyle

    private var dividerColor: Int? = null
    private var onClickListener: OnClickListener? = null

    private lateinit var tvLeft: NormalTextView
    private lateinit var tvMiddle: NormalTextView
    private lateinit var tvRight: NormalTextView
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

        tvLeft.setStyle(leftTextStyle, GlobalConfig.leftTextStyle)
        tvMiddle.setStyle(middleTextStyle, GlobalConfig.middleTextStyle)
        tvRight.setStyle(rightTextStyle, GlobalConfig.rightTextStyle)

        tvLeft.setOnClickListener { onClickListener?.onClick(dialog, BUTTON_POS_LEFT, dialogTitle, dialogContent) }
        tvMiddle.setOnClickListener { onClickListener?.onClick(dialog, BUTTON_POS_MIDDLE, dialogTitle, dialogContent) }
        tvRight.setOnClickListener { onClickListener?.onClick(dialog, BUTTON_POS_RIGHT, dialogTitle, dialogContent) }

        val cornerRadius = UIXViewUtils.dp2px(dialog.dialogCornerRadius)
        val normalColor = if (dialog.dialogDarkStyle) BeautyDialog.GlobalConfig.darkBGColor else BeautyDialog.GlobalConfig.lightBGColor
        val selectedColor = UIXColorUtils.computeColor(normalColor, if (dialog.dialogDarkStyle) Color.WHITE else Color.BLACK, NormalButton.GlobalConfig.fraction)
        tvLeft.background = StateListDrawable().apply {
            val normalDrawable = UIXImageUtils.getGradientDrawable(normalColor, 0f, 0f, cornerRadius.toFloat(), 0f)
            val selectedDrawable = UIXImageUtils.getGradientDrawable(selectedColor, 0f, 0f, cornerRadius.toFloat(), 0f)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }
        tvMiddle.background = StateListDrawable().apply {
            val radius = if (bottomStyle == BUTTON_STYLE_SINGLE) cornerRadius.toFloat() else 0f
            val normalDrawable = UIXImageUtils.getGradientDrawable(normalColor, 0f, 0f, radius, radius)
            val selectedDrawable = UIXImageUtils.getGradientDrawable(selectedColor, 0f, 0f, radius, radius)
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }
        tvRight.background = StateListDrawable().apply {
            val normalDrawable = UIXImageUtils.getGradientDrawable(normalColor, 0f, 0f, 0f, cornerRadius.toFloat())
            val selectedDrawable = UIXImageUtils.getGradientDrawable(selectedColor, 0f, 0f, 0f, cornerRadius.toFloat())
            addState(intArrayOf(android.R.attr.state_pressed), selectedDrawable)
            addState(intArrayOf(-android.R.attr.state_pressed), normalDrawable)
        }

        // 优先使用构建者模式中传入的颜色，如果没有再使用全局配置的颜色，还是没有就使用默认颜色
        val finalDividerColor =
                if (dividerColor == null) {
                    if (GlobalConfig.dividerColor == null) selectedColor
                    else GlobalConfig.dividerColor!!
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
        private var leftTextStyle = GlobalConfig.leftTextStyle
        private var middleText: CharSequence? = null
        private var middleTextStyle = GlobalConfig.middleTextStyle
        private var rightText: CharSequence? = null
        private var rightTextStyle = GlobalConfig.rightTextStyle

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
            bottom.onClickListener = onClickListener
            return bottom
        }
    }

    object GlobalConfig {
        var leftTextStyle = TextStyleBean()
        var middleTextStyle = TextStyleBean()
        var rightTextStyle = TextStyleBean()
        /** 按钮底部的分割线的颜色 */
        @ColorInt
        var dividerColor: Int? = null
    }
}