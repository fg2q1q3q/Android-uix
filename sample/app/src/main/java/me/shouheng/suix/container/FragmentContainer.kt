package me.shouheng.suix.container

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import me.shouheng.beauty.comn.PalmUtils
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.mvvm.databinding.MvvmsActivityContainerBinding
import me.shouheng.suix.BuildConfig
import me.shouheng.suix.R
import me.shouheng.uix.page.AboutFragment
import me.shouheng.uix.page.ImageLoader
import me.shouheng.uix.page.fragment.FragmentKeyDown
import me.shouheng.uix.page.fragment.WebviewFragment
import me.shouheng.uix.page.model.AboutSectionItem
import me.shouheng.uix.page.model.AboutTextItem
import me.shouheng.uix.page.model.AboutUserItem
import me.shouheng.uix.page.model.IAboutItem
import me.shouheng.utils.app.IntentUtils
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.LogUtils

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-17 23:28
 */
class FragmentContainer : CommonActivity<MvvmsActivityContainerBinding, EmptyViewModel>(), WebviewFragment.OnReceivedTitleListener {

    companion object {
        const val FRAGMENT_TYPE             = "__fragment_type"

        const val FRAGMENT_TYPE_WEB         = 0
        const val FRAGMENT_TYPE_ABOUT       = 1
    }

    private var fragmentType: Int? = null

    override fun getLayoutResId() = R.layout.mvvms_activity_container

    override fun onReceivedTitle(title: String) {
        LogUtils.d(title)
        supportActionBar?.title = title
    }

    override fun doCreateView(savedInstanceState: Bundle?) {
        fragmentType = intent.getIntExtra(FRAGMENT_TYPE, FRAGMENT_TYPE_WEB)
        var fragment: Fragment? = null
        when(fragmentType) {
            FRAGMENT_TYPE_WEB -> {
                fragment = WebviewFragment.Builder()
                        .setDarkTheme(true)
                        .setIndicatorColor(Color.BLUE)
                        .setUrl("https://weibo.com/5401152113/profile?rightmod=1&wvr=6&mod=personinfo")
                        .build()
            }
            FRAGMENT_TYPE_ABOUT -> {
                fragment = AboutFragment.Builder()
                        .setPageBgColor(Color.LTGRAY)
                        .setAppBarBgColor(Color.WHITE)
                        .setSlogan("安卓 UI 组件化")
                        .setSloganTypeface(Typeface.BOLD)
                        .setSloganTextColor(Color.BLACK)
                        .setSloganTextSize(16f)
                        .setSloganGravity(Gravity.CENTER)
                        .setVersion("穷困潦倒的开发者啊\n" +
                                "穷困潦倒的开发者啊\n" +
                                "穷困潦倒的开发者啊\n" +
                                "Poor lonely and homeless developer")
                        .setVersionTextColor(Color.DKGRAY)
                        .setVersionTypeface(Typeface.ITALIC)
                        .setVersionGravity(Gravity.CENTER)
                        .setVersionTextSize(12f)
                        .setLogo(ResUtils.getDrawable(R.drawable.ic_empty_zhihu))
                        .setRightDrawable(ResUtils.getDrawable(R.drawable.ic_favorite_border_black_24dp), object : AboutFragment.OnRightClickListener {
                            override fun onClick(v: View) {
                                startActivity(IntentUtils.getLaunchMarketIntent(BuildConfig.APPLICATION_ID))
                            }
                        })
                        .setLeftDrawable(ResUtils.getDrawable(R.drawable.uix_arrow_back_black_24dp), object : AboutFragment.OnLeftClickListener {
                            override fun onClick() {
                                finish()
                            }
                        })
                        .setAboutItemBgColor(Color.WHITE)
                        .setAboutItems(
                                listOf(
                                        AboutSectionItem(
                                                id = 0,
                                                title = ResUtils.getString(R.string.about_app_section)),
                                        AboutTextItem(
                                                id = 1,
                                                text = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_app_detail)),
                                                textSize = 14f),
                                        AboutSectionItem(
                                                id = 0,
                                                title = ResUtils.getString(R.string.about_developer_section)),
                                        AboutUserItem(
                                                id = 2,
                                                name = ResUtils.getString(R.string.about_developer_name),
                                                nameTextColor = Color.BLACK,
                                                nameTextSize = 14f,
                                                desc = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_developer_detail)),
                                                imageLoader = object : ImageLoader {
                                                    override fun load(imageView: ImageView) {
                                                        imageView.setImageResource(R.drawable.fat_ass)
                                                    }
                                                },
                                                descTextColor = Color.GRAY,
                                                descTextSize = 14f),
                                        AboutSectionItem(
                                                id = 0,
                                                title = ResUtils.getString(R.string.about_sources)),
                                        AboutTextItem(
                                                id = 3,
                                                text = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_sources_detail)),
                                                textSize = 14f),
                                        AboutSectionItem(
                                                id = 0,
                                                title = ResUtils.getString(R.string.about_other_apps)),
                                        AboutUserItem(
                                                id = 4,
                                                name = ResUtils.getString(R.string.about_other_marknote),
                                                nameTextColor = Color.BLACK,
                                                nameTextSize = 14f,
                                                desc = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_other_marknote_desc)),
                                                imageLoader = object : ImageLoader {
                                                    override fun load(imageView: ImageView) {
                                                        imageView.setImageResource(R.drawable.mark_note)
                                                    }
                                                },
                                                descTextColor = Color.GRAY,
                                                descTextSize = 14f)
                                ), object : AboutFragment.OnItemClickListener {
                            override fun onItemClick(iAboutItem: IAboutItem) {
                                toast("点击了 $iAboutItem")
                            }
                        })
                        .build()
            }
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment!!)
                .commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        return if ((fragment as? FragmentKeyDown)?.onFragmentKeyDown(keyCode, event) == true) {
            return true
        } else super.onKeyDown(keyCode, event)
    }
}