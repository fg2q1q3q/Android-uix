package me.shouheng.uix.page

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.uix_dialog_content_edit_simple.*
import me.shouheng.uix.R
import me.shouheng.uix.page.adapter.AboutItemAdapter
import me.shouheng.uix.page.model.IAboutItem
import me.shouheng.uix.utils.UIXUtils

/**
 * 关于页面
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:09
 */
class AboutFragment : Fragment() {

    @ColorInt private var pageBgColor: Int? = null
    @ColorInt private var barBgColor: Int? = null
    @ColorInt private var contentColor: Int? = null
    @ColorInt private var aboutItemBgColor: Int? = null

    private var iconDrawableResId: Int? = null
    private var iconRightResId: Int? = null

    private var slogan: String? = null
    private var toolbarTitle: String? = null
    private var version: String? = null
    private var aboutItems: List<IAboutItem> = emptyList()

    private var rightClickListener: OnRightClickListener? = null
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

        if (contentColor != null) {
            ab.setHomeAsUpIndicator(UIXUtils.tintDrawable(R.drawable.uix_arrow_back_black_24dp, contentColor!!))
            toolbar.setTitleTextColor(contentColor!!)
            tvSlogan.setTextColor(contentColor!!)
            tvVersion.setTextColor(contentColor!!)
            ctl.setCollapsedTitleTextColor(contentColor!!)
        } else {
            ab.setHomeAsUpIndicator(R.drawable.uix_arrow_back_black_24dp)
        }

        if (pageBgColor != null) cl.setBackgroundColor(pageBgColor!!)
        if (barBgColor != null) {
            ctl.setBackgroundColor(barBgColor!!)
            ctl.contentScrim = ColorDrawable(barBgColor!!)
        }

        if (iconDrawableResId != null) ivIcon.setImageResource(iconDrawableResId!!)

        tvSlogan.text = slogan

        toolbarTitle = if (TextUtils.isEmpty(toolbarTitle)) slogan else toolbarTitle
        ctl.title = toolbarTitle
        ab.title = toolbarTitle
        toolbar.title = toolbarTitle

        tvVersion.text = version

        if (iconRightResId != null) ivRight.setImageResource(iconRightResId!!)
        else ivRight.visibility = View.GONE
        ivRight.setOnClickListener { rightClickListener?.onClick(ivRight) }

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
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    interface OnRightClickListener {

        fun onClick(v: View)
    }

    interface OnItemClickListener {

        fun onItemClick(iAboutItem: IAboutItem)
    }

    class Builder {
        @ColorInt private var pageBgColor: Int? = null
        @ColorInt private var barBgColor: Int? = null
        @ColorInt private var contentColor: Int? = null
        @ColorInt private var aboutItemBgColor: Int? = null

        private var iconDrawableResId: Int? = null
        private var iconRightResId: Int? = null

        private var slogan: String? = null
        private var toolbarTitle: String? = null
        private var version: String? = null
        private var aboutItems: List<IAboutItem> = emptyList()

        private var rightClickListener: OnRightClickListener? = null
        private var onItemClickListener: OnItemClickListener? = null

        fun setPageBgColor(@ColorInt pageBgColor: Int): Builder {
            this.pageBgColor = pageBgColor
            return this
        }

        fun setBarBgColor(@ColorInt barBgColor: Int): Builder {
            this.barBgColor = barBgColor
            return this
        }

        fun setContentColor(@ColorInt contentColor: Int): Builder {
            this.contentColor = contentColor
            return this
        }

        fun setAboutItemBgColor(@ColorInt aboutItemBgColor: Int): Builder {
            this.aboutItemBgColor = aboutItemBgColor
            return this
        }

        fun setIconResId(@DrawableRes iconDrawableResId: Int): Builder {
            this.iconDrawableResId = iconDrawableResId
            return this
        }

        fun setIconRightResId(@DrawableRes iconRightResId: Int): Builder {
            this.iconRightResId = iconRightResId
            return this
        }

        fun setSlogan(slogan: String): Builder {
            this.slogan = slogan
            return this
        }

        fun setToolbarTitle(toolbarTitle: String): Builder {
            this.toolbarTitle = toolbarTitle
            return this
        }

        fun setVersion(version: String): Builder {
            this.version = version
            return this
        }

        fun setAboutItems(aboutItems: List<IAboutItem>): Builder {
            this.aboutItems = aboutItems
            return this
        }

        fun setOnRightClickListener(rightClickListener: OnRightClickListener): Builder {
            this.rightClickListener = rightClickListener
            return this
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener): Builder {
            this.onItemClickListener = onItemClickListener
            return this
        }

        fun build(): AboutFragment {
            val fragment = AboutFragment()
            fragment.pageBgColor = pageBgColor
            fragment.barBgColor = barBgColor
            fragment.contentColor = contentColor
            fragment.aboutItemBgColor = aboutItemBgColor
            fragment.iconDrawableResId = iconDrawableResId
            fragment.iconRightResId = iconRightResId
            fragment.slogan = slogan
            fragment.toolbarTitle = toolbarTitle
            fragment.version = version
            fragment.aboutItems = aboutItems
            fragment.rightClickListener = rightClickListener
            fragment.onItemClickListener = onItemClickListener
            return fragment
        }
    }
}