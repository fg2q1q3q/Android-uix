package me.shouheng.uix.widget.text

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import me.shouheng.uix.common.bean.TextStyleBean

class NormalTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setStyle(style: TextStyleBean) {
        if (style.textSize != null) this.textSize = style.textSize!!
        if (style.gravity != null) this.gravity = style.gravity!!
        if (style.textColor != null) setTextColor(style.textColor!!)
        if (style.typeFace != null) this.setTypeface(null, style.typeFace!!)
    }

    fun setStyle(self: TextStyleBean, global: TextStyleBean) {
        if (self.textSize != null) {
            this.textSize = self.textSize!!
        } else if (global.textSize != null) {
            this.textSize = global.textSize!!
        }
        if (self.gravity != null) {
            this.gravity = self.gravity!!
        } else if (global.gravity != null) {
            this.gravity = global.gravity!!
        }
        if (self.textColor != null) {
            setTextColor(self.textColor!!)
        } else if (global.textColor != null) {
            setTextColor(global.textColor!!)
        }
        if (self.typeFace != null) {
            this.setTypeface(null, self.typeFace!!)
        } else if (global.typeFace != null) {
            this.setTypeface(null, global.typeFace!!)
        }
    }
}
