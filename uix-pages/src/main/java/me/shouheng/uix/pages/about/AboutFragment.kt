package me.shouheng.uix.pages.about

import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UColor
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.R
import me.shouheng.uix.pages.about.AboutFragment.FragmentInteraction
import me.shouheng.uix.widget.text.NormalTextView

/**
 * 关于页面：作为该 Fragment 容器的 Activity 应该实现 [FragmentInteraction] 接口
 * 来完成该 Fragment 内部的各种点击和加载等交互性事件。
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:09
 */
class AboutFragment : Fragment() {

    @ColorInt
    private var backgroundColor: Int? = null
    @ColorInt
    private var foregroundColor: Int? = null

    private var logo: Int? = null
    private var iconRight: Int? = null
    private var iconLeft: Int? = null

    private var title: CharSequence? = null
    private var titleStyle: TextStyleBean? = null
    private var slogan: CharSequence? = null
    private var sloganStyle: TextStyleBean? = null
    private var version: CharSequence? = null
    private var versionStyle: TextStyleBean? = null

    private var aboutItems: List<IAboutItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backgroundColor = arguments?.getInt("backgroundColor")
        foregroundColor = arguments?.getInt("foregroundColor")
        logo = arguments?.getInt("logo")
        iconRight = arguments?.getInt("iconRight")
        iconLeft = arguments?.getInt("iconLeft")
        slogan = arguments?.getCharSequence("slogan")
        version = arguments?.getCharSequence("version")
        title = arguments?.getCharSequence("title")
        titleStyle = arguments?.getSerializable("titleStyle") as TextStyleBean?
        sloganStyle = arguments?.getSerializable("sloganStyle") as TextStyleBean?
        versionStyle = arguments?.getSerializable("versionStyle") as TextStyleBean?
        aboutItems = arguments?.getParcelableArrayList<IAboutItem>("aboutItems") as ArrayList<IAboutItem>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.uix_fragment_about, null, false)

        val header = LayoutInflater.from(context).inflate(R.layout.uix_layout_about_header, null, false)
        val ivIcon = header.findViewById<AppCompatImageView>(R.id.iv_icon)
        val tvSlogan = header.findViewById<NormalTextView>(R.id.tv_slogan)
        val tvVersion = header.findViewById<NormalTextView>(R.id.tv_version)
        val llHeader = header.findViewById<LinearLayout>(R.id.ll_header)

        val ll = root.findViewById<FrameLayout>(R.id.ll)
        val toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        val ivRight = root.findViewById<ImageView>(R.id.iv_right)
        val ivLeft = root.findViewById<ImageView>(R.id.iv_left)
        val rv = root.findViewById<RecyclerView>(R.id.rv)
        val tvTitle = root.findViewById<NormalTextView>(R.id.tv_title)

        if (iconLeft == null) ivLeft.visibility = View.GONE
        else ivLeft.setImageResource(iconLeft!!)
        if (iconRight == null) ivRight.visibility = View.GONE
        else ivRight.setImageResource(iconRight!!)
        ivLeft.setOnClickListener { (activity as? FragmentInteraction)?.onLeftClick(it) }
        ivRight.setOnClickListener { (activity as? FragmentInteraction)?.onRightClick(it) }

        tvTitle.text = ""
        tvSlogan.text = slogan
        tvVersion.text = version
        if (logo != null) ivIcon.setImageResource(logo!!)
        if (sloganStyle != null) tvSlogan.setStyle(sloganStyle!!)
        if (versionStyle != null) tvVersion.setStyle(versionStyle!!)
        if (titleStyle != null) tvTitle.setStyle(titleStyle!!)

        if (backgroundColor != null) ll.setBackgroundColor(backgroundColor!!)
        if (foregroundColor != null) {
            toolbar.setBackgroundColor(foregroundColor!!)
//            llHeader.setBackgroundColor(foregroundColor!!)
        }

        val adapter = AboutItemAdapter(aboutItems, backgroundColor, foregroundColor, activity as? FragmentInteraction)
        rv.adapter = adapter
        adapter.setHeaderView(header)
        adapter.setOnItemClickListener { _, _, p -> (activity as? FragmentInteraction)?.onItemClick(adapter.data[p], p) }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            private var toolbarHeight = UView.dp2px(80f)
            private var titleHeight = UView.dp2px(130f)
            private var offHeight = 0
            private var titleShowed = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                (activity as? FragmentInteraction)?.onScrolled(recyclerView, dx, dy)
                offHeight += dy
                toolbar.setBackgroundColor(UColor.setColorAlpha(foregroundColor!!,
                        (offHeight * 1f / toolbarHeight).coerceAtMost(1f), true))
                if (offHeight > titleHeight) {
                    if (!titleShowed) {
                        titleShowed = true
                        tvTitle.text = title
                    }
                } else {
                    if (titleShowed) {
                        titleShowed = false
                        tvTitle.text = ""
                    }
                }
            }
        })

        return root
    }

    interface FragmentInteraction {

        /**
         * 页面顶部左侧按钮被点击的事件
         */
        fun onLeftClick(v: View)

        /**
         * 页面顶部右侧按钮被点击的事件
         */
        fun onRightClick(v: View)

        /**
         * 关于列表条目被点击的回调
         */
        fun onItemClick(item: IAboutItem, pos: Int)

        /**
         * 为列表条目加载子控件
         */
        fun loadSubViews(id: Int): View?

        /**
         * 图片加载事件
         */
        fun loadImage(id: Int, iv: ImageView)

        /** Callback when the recyclerview is scrolled */
        fun onScrolled(rv: RecyclerView, dx: Int, dy: Int)
    }

    class Builder {

        private val args = Bundle()

        fun setBackgroundColor(@ColorInt backgroundColor: Int): Builder {
            args.putInt("backgroundColor", backgroundColor)
            return this
        }

        fun setForegroundColor(@ColorInt foregroundColor: Int): Builder {
            args.putInt("foregroundColor", foregroundColor)
            return this
        }

        fun setLogo(logo: Int): Builder {
            args.putInt("logo", logo)
            return this
        }

        fun setTitle(title: CharSequence): Builder {
            args.putCharSequence("title", title)
            return this
        }

        fun setTitleStyle(titleStyle: TextStyleBean): Builder {
            args.putSerializable("titleStyle", titleStyle)
            return this
        }

        fun setIconRight(iconRight: Int): Builder {
            args.putInt("iconRight", iconRight)
            return this
        }

        fun setIconLeft(iconLeft: Int): Builder {
            args.putInt("iconLeft", iconLeft)
            return this
        }

        fun setSlogan(slogan: CharSequence): Builder {
            args.putCharSequence("slogan", slogan)
            return this
        }

        fun setSloganStyle(sloganStyle: TextStyleBean): Builder {
            args.putSerializable("sloganStyle", sloganStyle)
            return this
        }

        fun setVersion(version: CharSequence): Builder {
            args.putCharSequence("version", version)
            return this
        }

        fun setVersionStyle(versionStyle: TextStyleBean): Builder {
            args.putSerializable("versionStyle", versionStyle)
            return this
        }

        fun setAboutItems(aboutItems: ArrayList<IAboutItem>): Builder {
            args.putParcelableArrayList("aboutItems", aboutItems)
            return this
        }

        fun build(): AboutFragment {
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }
}