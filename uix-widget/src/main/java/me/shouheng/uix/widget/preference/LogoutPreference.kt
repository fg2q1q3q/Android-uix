package me.shouheng.uix.widget.preference

import android.content.Context
import android.graphics.Color
import android.preference.Preference
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.widget.R
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 退出登录
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 10:40
 */
class LogoutPreference : Preference {

    private var onclickListener: View.OnClickListener? = null

    private var tvLogout: NormalTextView? = null
    private var v1: View? = null
    private var v2: View? = null
    private var dividerColor: Int? = null
    private var text: CharSequence? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        layoutResource = R.layout.uix_logout_preference
    }

    override fun onBindView(view: View?) {
        super.onBindView(view)
        tvLogout = view?.findViewById(R.id.tv_logout)
        setText(text?:"")
        v1 = view?.findViewById(R.id.v1)
        v2 = view?.findViewById(R.id.v2)
        setDividerColor(dividerColor?:Color.TRANSPARENT)
        tvLogout?.setOnClickListener { v -> onclickListener?.onClick(v) }
    }

    fun setOnClickListener(onclickListener: View.OnClickListener) {
        this.onclickListener = onclickListener
    }

    fun setText(text: CharSequence) {
        this.text = text
        tvLogout?.text = text
    }

    fun setTextStyle(style: TextStyleBean) {
        tvLogout?.setStyle(style)
    }

    fun setDividerColor(@ColorInt color: Int) {
        this.dividerColor = color
        v1?.setBackgroundColor(color)
        v2?.setBackgroundColor(color)
    }
}