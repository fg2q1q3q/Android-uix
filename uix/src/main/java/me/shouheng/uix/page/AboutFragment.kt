package me.shouheng.uix.page

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import me.shouheng.uix.R
import me.shouheng.uix.page.adapter.AboutItemAdapter
import me.shouheng.uix.page.model.IAboutItem

/**
 * 关于页面
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:09
 */
class AboutFragment : Fragment() {

    @ColorInt private var pageBgColor: Int? = null
    @ColorInt private var barBgColor: Int? = null

    private var logo: Drawable? = null
    private var iconRight: Drawable? = null
    private var iconLeft: Drawable? = null

    private var slogan: String? = null
    private var sloganTextSize: Float = 16f
    private var sloganTextColor: Int? = null
    private var sloganTypeface: Int = Typeface.NORMAL
    private var sloganGravity: Int = Gravity.CENTER

    private var version: String? = null
    private var versionTextSize: Float = 14f
    private var versionTextColor: Int? = null
    private var versionTypeface: Int = Typeface.NORMAL
    private var versionGravity: Int = Gravity.CENTER

    @ColorInt private var aboutItemBgColor: Int? = null
    private var aboutItems: List<IAboutItem> = emptyList()

    private var onLeftClickListener: OnLeftClickListener? = null
    private var onRightClickListener: OnRightClickListener? = null
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.uix_fragment_about, null, false)

        val cl = root.findViewById<CoordinatorLayout>(R.id.cl)
        val ctl = root.findViewById<CollapsingToolbarLayout>(R.id.ctl)
        val ivIcon = root.findViewById<AppCompatImageView>(R.id.iv_icon)
        val tvSlogan = root.findViewById<TextView>(R.id.tv_slogan)
        val tvVersion = root.findViewById<TextView>(R.id.tv_version)
        val toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        val ivRight = root.findViewById<ImageView>(R.id.iv_right)
        val rv = root.findViewById<RecyclerView>(R.id.rv)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity).supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        ab.setDisplayShowHomeEnabled(true)
        ab.setHomeAsUpIndicator(iconLeft)

        tvSlogan.text = slogan
        tvSlogan.textSize = sloganTextSize
        if (sloganTextColor != null) tvSlogan.setTextColor(sloganTextColor!!)
        tvSlogan.setTypeface(null, sloganTypeface)
        tvSlogan.gravity = sloganGravity

        tvVersion.text = version
        tvVersion.textSize = versionTextSize
        if (versionTextColor != null) tvVersion.setTextColor(versionTextColor!!)
        tvVersion.setTypeface(null, versionTypeface)
        tvVersion.gravity = versionGravity

        if (pageBgColor != null) cl.setBackgroundColor(pageBgColor!!)
        if (barBgColor != null) {
            ctl.setBackgroundColor(barBgColor!!)
            ctl.contentScrim = ColorDrawable(barBgColor!!)
        }

        ivIcon.setImageDrawable(logo)

        if (iconRight != null) ivRight.setImageDrawable(iconRight!!)
        else ivRight.visibility = View.GONE
        ivRight.setOnClickListener { onRightClickListener?.onClick(ivRight) }

        val adapter = AboutItemAdapter(aboutItems, itemBackground = aboutItemBgColor?: Color.TRANSPARENT)
        rv.adapter = adapter
        adapter.setOnItemClickListener { _, _, p -> onItemClickListener?.onItemClick(adapter.data[p]) }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onLeftClickListener?.onClick()
        }
        return super.onOptionsItemSelected(item)
    }

    interface OnRightClickListener {

        fun onClick(v: View)
    }

    interface OnLeftClickListener {

        fun onClick()
    }

    interface OnItemClickListener {

        fun onItemClick(iAboutItem: IAboutItem)
    }

    class Builder {
        @ColorInt private var pageBgColor: Int? = null
        @ColorInt private var barBgColor: Int? = null

        private var logo: Drawable? = null
        private var iconRight: Drawable? = null
        private var iconLeft: Drawable? = null

        private var slogan: String? = null
        private var sloganTextSize: Float = 16f
        private var sloganTextColor: Int? = null
        private var sloganTypeface: Int = Typeface.NORMAL
        private var sloganGravity: Int = Gravity.CENTER

        private var version: String? = null
        private var versionTextSize: Float = 14f
        private var versionTextColor: Int? = null
        private var versionTypeface: Int = Typeface.NORMAL
        private var versionGravity: Int = Gravity.CENTER

        @ColorInt private var aboutItemBgColor: Int? = null
        private var aboutItems: List<IAboutItem> = emptyList()

        private var onRightClickListener: OnRightClickListener? = null
        private var onLeftClickListener: OnLeftClickListener? = null
        private var onItemClickListener: OnItemClickListener? = null

        /**
         * 设置页面的背景颜色
         */
        fun setPageBgColor(@ColorInt pageBgColor: Int): Builder {
            this.pageBgColor = pageBgColor
            return this
        }

        /**
         * 设置顶部的 AppbarLayout 的背景颜色
         */
        fun setAppBarBgColor(@ColorInt barBgColor: Int): Builder {
            this.barBgColor = barBgColor
            return this
        }

        /**
         * 设置 logo
         */
        fun setLogo(logo: Drawable): Builder {
            this.logo = logo
            return this
        }

        /**
         * 设置工具栏右侧的按钮的图标和点击事件
         */
        fun setRightDrawable(iconRight: Drawable, onRightClickListener: OnRightClickListener): Builder {
            this.iconRight = iconRight
            this.onRightClickListener = onRightClickListener
            return this
        }

        /**
         * 设置工具栏左侧的按钮的图标和点击事件
         */
        fun setLeftDrawable(iconLeft: Drawable, onLeftClickListener: OnLeftClickListener): Builder {
            this.iconLeft = iconLeft
            this.onLeftClickListener = onLeftClickListener
            return this
        }

        /**
         * 一级标题：文字
         */
        fun setSlogan(slogan: String): Builder {
            this.slogan = slogan
            return this
        }

        /**
         * 一级标题：文字大小
         */
        fun setSloganTextSize(textSize: Float): Builder {
            this.sloganTextSize = textSize
            return this
        }

        /**
         * 一级标题：文字颜色
         */
        fun setSloganTextColor(@ColorInt textColor: Int): Builder {
            this.sloganTextColor = textColor
            return this
        }

        /**
         * 一级标题：字体
         */
        fun setSloganTypeface(typeFace: Int): Builder {
            this.sloganTypeface = typeFace
            return this
        }

        /**
         * 一级标题：位置
         */
        fun setSloganGravity(gravity: Int): Builder {
            this.sloganGravity = gravity
            return this
        }

        /**
         * 二级标题
         */
        fun setVersion(version: String): Builder {
            this.version = version
            return this
        }

        /**
         * 二级标题：文字大小
         */
        fun setVersionTextSize(textSize: Float): Builder {
            this.versionTextSize = textSize
            return this
        }

        /**
         * 二级标题：文字颜色
         */
        fun setVersionTextColor(@ColorInt textColor: Int): Builder {
            this.versionTextColor = textColor
            return this
        }

        /**
         * 二级标题：字体
         */
        fun setVersionTypeface(typeFace: Int): Builder {
            this.versionTypeface = typeFace
            return this
        }

        /**
         * 二级标题：位置
         */
        fun setVersionGravity(gravity: Int): Builder {
            this.versionGravity = gravity
            return this
        }

        /**
         * 设置底部条目的背景颜色
         */
        fun setAboutItemBgColor(@ColorInt aboutItemBgColor: Int): Builder {
            this.aboutItemBgColor = aboutItemBgColor
            return this
        }

        /**
         * 设置列表的内容以及单击事件
         */
        fun setAboutItems(aboutItems: List<IAboutItem>, onItemClickListener: OnItemClickListener): Builder {
            this.aboutItems = aboutItems
            this.onItemClickListener = onItemClickListener
            return this
        }

        fun build(): AboutFragment {
            val fragment = AboutFragment()
            fragment.pageBgColor = pageBgColor
            fragment.barBgColor = barBgColor

            fragment.logo = logo
            fragment.iconRight = iconRight
            fragment.iconLeft = iconLeft

            fragment.slogan = slogan
            fragment.sloganTextSize = sloganTextSize
            fragment.sloganTextColor = sloganTextColor
            fragment.sloganTypeface = sloganTypeface

            fragment.version = version
            fragment.versionTextSize = versionTextSize
            fragment.versionTextColor = versionTextColor
            fragment.versionTypeface = versionTypeface

            fragment.aboutItemBgColor = aboutItemBgColor
            fragment.aboutItems = aboutItems

            fragment.onLeftClickListener = onLeftClickListener
            fragment.onRightClickListener = onRightClickListener
            fragment.onItemClickListener = onItemClickListener

            return fragment
        }
    }
}