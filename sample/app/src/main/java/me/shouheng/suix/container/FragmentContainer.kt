package me.shouheng.suix.container

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
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
                        .setIndicatorColor(Color.BLUE)
                        .setUrl("https://weibo.com/5401152113/profile?rightmod=1&wvr=6&mod=personinfo")
                        .build()
            }
            FRAGMENT_TYPE_ABOUT -> {
                fragment = AboutFragment.Builder()
                        .setContentColor(Color.BLUE)
                        .setBarBgColor(Color.WHITE)
                        .setToolbarTitle("安卓 UI 组件化 -- UIX")
                        .setSlogan("安卓 UI 组件化")
                        .setVersion("1.0")
                        .setIconResId(R.drawable.mark_note)
                        .setIconRightResId(R.drawable.ic_favorite_border_black_24dp)
                        .setOnRightClickListener(object : AboutFragment.OnRightClickListener {
                            override fun onClick(v: View) {
                                startActivity(IntentUtils.getLaunchMarketIntent(BuildConfig.APPLICATION_ID))
                            }
                        })
                        .setAboutItemBgColor(Color.WHITE)
                        .setAboutItems(
                                listOf(
                                        AboutSectionItem(0, title = "标题啊"),
                                        AboutTextItem(1, "I couldnt figure why my toolbar colour was just blank." +
                                                " Couldnt seem to figure out how to change it to green. " +
                                                "I feel silly as all I had to do was put android:background=\" \" within the <android.support.v7.widget.Toolbar />" +
                                                " in the layout file and that did it! . Thanks – Andrew Irwin"),
                                        AboutSectionItem(0, title = "开发者啊"),
                                        AboutUserItem(2, "开发者啊", desc = "穷困潦倒的开发者啊\n穷困潦倒的开发者啊\n穷困潦倒的开发者啊\nPoor lonely and homeless developer", imageLoader = object : ImageLoader {
                                            override fun load(imageView: ImageView) {
                                                imageView.setImageResource(R.drawable.fat_ass)
                                            }
                                        })
                                ))
                        .setOnItemClickListener(object : AboutFragment.OnItemClickListener {
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