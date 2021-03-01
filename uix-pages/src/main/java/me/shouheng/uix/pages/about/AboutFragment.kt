package me.shouheng.uix.pages.about

import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UColor
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.about.AboutFragment.FragmentInteraction
import me.shouheng.uix.pages.databinding.UixFragmentAboutBinding
import me.shouheng.uix.pages.databinding.UixLayoutAboutHeaderBinding
import me.shouheng.uix.widget.rv.onItemDebouncedClick
import me.shouheng.utils.ktx.gone
import me.shouheng.utils.ktx.onDebouncedClick

/**
 * 关于页面：作为该 Fragment 容器的 Activity 应该实现 [FragmentInteraction] 接口
 * 来完成该 Fragment 内部的各种点击和加载等交互性事件。
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:09
 */
class AboutFragment : Fragment() {

    @ColorInt private var backgroundColor: Int? = null
    @ColorInt private var foregroundColor: Int? = null

    private var logo: Int? = null
    private var iconRight: Int? = null
    private var iconLeft: Int? = null

    private var title: CharSequence? = null
    private var titleStyle: TextStyleBean? = null
    private var slogan: CharSequence? = null
    private var sloganStyle: TextStyleBean? = null
    private var version: CharSequence? = null
    private var versionStyle: TextStyleBean? = null
    private var newerVersion: CharSequence? = null
    private var newerVersionStyle: TextStyleBean? = null

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
        newerVersion = arguments?.getCharSequence("newerVersion")
        titleStyle = arguments?.getSerializable("titleStyle") as TextStyleBean?
        sloganStyle = arguments?.getSerializable("sloganStyle") as TextStyleBean?
        versionStyle = arguments?.getSerializable("versionStyle") as TextStyleBean?
        newerVersionStyle = arguments?.getSerializable("newerVersionStyle") as TextStyleBean?
        aboutItems = arguments?.getParcelableArrayList<IAboutItem>("aboutItems") as ArrayList<IAboutItem>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = UixFragmentAboutBinding.inflate(inflater, null, false)
        val header = UixLayoutAboutHeaderBinding.inflate(inflater, null, false)

        if (iconLeft == null) binding.ivLeft.gone() else binding.ivLeft.setImageResource(iconLeft!!)
        if (iconRight == null) binding.ivRight.gone() else binding.ivRight.setImageResource(iconRight!!)
        binding.ivLeft.onDebouncedClick { (activity as? FragmentInteraction)?.onLeftClick(it) }
        binding.ivRight.onDebouncedClick { (activity as? FragmentInteraction)?.onRightClick(it) }

        binding.tvTitle.text = ""
        header.tvSlogan.text = slogan
        header.tvVersion.text = version
        header.tvNewerVersion.text = newerVersion

        logo?.let { header.ivIcon.setImageResource(it) }
        titleStyle?.let { binding.tvTitle.setStyle(it) }
        sloganStyle?.let { header.tvSlogan.setStyle(it) }
        versionStyle?.let { header.tvVersion.setStyle(it) }
        newerVersionStyle?.let { header.tvNewerVersion.setStyle(it) }
        header.tvNewerVersion.onDebouncedClick { (activity as? FragmentInteraction)?.onNewVersionClick(it) }

        backgroundColor?.let { binding.fl.setBackgroundColor(it) }
        foregroundColor?.let { binding.toolbar.setBackgroundColor(it) }

        val adapter = AboutItemAdapter(aboutItems, backgroundColor, foregroundColor, activity as? FragmentInteraction)
        binding.rv.adapter = adapter
        adapter.setHeaderView(header.root)
        adapter.onItemDebouncedClick { _, _, p -> (activity as? FragmentInteraction)?.onItemClick(adapter.data[p], p) }

        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            private var toolbarHeight = UView.dp2px(80f)
            private var titleHeight = UView.dp2px(130f)
            private var offHeight = 0
            private var titleShowed = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                (activity as? FragmentInteraction)?.onScrolled(recyclerView, dx, dy)
                offHeight += dy
                binding.toolbar.setBackgroundColor(UColor.setColorAlpha(foregroundColor!!,
                        (offHeight * 1f / toolbarHeight).coerceAtMost(1f), true))
                if (offHeight > titleHeight) {
                    if (!titleShowed) {
                        titleShowed = true
                        binding.tvTitle.text = title
                    }
                } else {
                    if (titleShowed) {
                        titleShowed = false
                        binding.tvTitle.text = ""
                    }
                }
            }
        })

        return binding.root
    }

    interface FragmentInteraction {

        /** 页面顶部左侧按钮被点击的事件 */
        fun onLeftClick(v: View)

        /** 页面顶部右侧按钮被点击的事件 */
        fun onRightClick(v: View)

        /** 关于列表条目被点击的回调 */
        fun onItemClick(item: IAboutItem, pos: Int) { }

        /** On newer version view click callback */
        fun onNewVersionClick(v: View) { }

        /** 为列表条目加载子控件 */
        fun loadSubViews(id: Int): View? { return null }

        /** 图片加载事件 */
        fun loadImage(id: Int, iv: ImageView) { }

        /** Callback when the recyclerview is scrolled */
        fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) { }
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

        fun setNewerVersion(newerVersion: CharSequence): Builder {
            args.putCharSequence("newerVersion", newerVersion)
            return this
        }

        fun setNewerVersionStyle(newerVersionStyle: TextStyleBean): Builder {
            args.putSerializable("newerVersionStyle", newerVersionStyle)
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