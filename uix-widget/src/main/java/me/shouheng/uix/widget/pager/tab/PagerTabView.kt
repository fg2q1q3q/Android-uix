package me.shouheng.uix.widget.pager.tab

import android.content.Context
import android.graphics.Typeface
import android.text.Spanned
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import me.shouheng.uix.widget.R

/**
 * ViewPager 的 Tab 控件
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-01 00:03
 */
class PagerTabView : RelativeLayout {
    private var mTitle: TextView? = null
    private var mIconfont: TextView? = null
    private var mDot: ImageView? = null
    private var mContext: Context? = null

    private var tp: TextPaint? = null

    private var mFont: Typeface? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        this.mContext = context
        val v = LayoutInflater.from(mContext).inflate(R.layout.uix_layout_pagertab_imagetabview, this)
        mTitle = v.findViewById(R.id.title)
        mIconfont = v.findViewById(R.id.iconfont)
        mDot = v.findViewById(R.id.dot)
        tp = mTitle!!.paint
        mFont = Typeface.DEFAULT
        mIconfont!!.typeface = mFont
    }

    fun setTitle(title: String) {
        mTitle!!.text = title
    }

    fun setIconfont(iconfont: Spanned?) {
        if (iconfont != null) {
            mIconfont!!.visibility = View.VISIBLE
            mIconfont!!.text = iconfont
        }
    }

    fun showDot() {
        mDot!!.visibility = View.VISIBLE
    }

    fun hideDot() {
        mDot!!.visibility = View.INVISIBLE
    }

    fun updateStyle(textSize: Float, textColor: Int, textStyle: Int) {
        mTitle!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        mTitle!!.setTextColor(textColor)
        if (textStyle == Typeface.BOLD) {
            tp!!.isFakeBoldText = true
        } else if (textStyle == Typeface.NORMAL) {
            tp!!.isFakeBoldText = false
        }
        mIconfont!!.setTextColor(textColor)
    }
}
