package me.shouheng.suix

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Process
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import me.shouheng.beauty.comn.PalmUtils
import me.shouheng.mvvm.MVVMs
import me.shouheng.mvvm.comn.ContainerActivity
import me.shouheng.uix.UIX
import me.shouheng.uix.UIXConfig
import me.shouheng.uix.page.AboutFragment
import me.shouheng.uix.page.CrashActivity
import me.shouheng.uix.page.GetSubViewListener
import me.shouheng.uix.page.ImageLoader
import me.shouheng.uix.page.fragment.WebviewFragment
import me.shouheng.uix.page.model.AboutSectionItem
import me.shouheng.uix.page.model.AboutTextItem
import me.shouheng.uix.page.model.AboutUserItem
import me.shouheng.uix.page.model.IAboutItem
import me.shouheng.uix.utils.UIXUtils
import me.shouheng.utils.app.IntentUtils
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.L
import me.shouheng.utils.ui.ToastUtils
import kotlin.system.exitProcess

const val COMMAND_LAUNCH_WEBVIEW = 0x00001
const val COMMAND_LAUNCH_ABOUT   = 0x00002

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:44
 */
class SampleApp: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MVVMs.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MVVMs.onCreate(this)
        UIX.init(this)
        customUIX()
        customCrash()
        customContainer()
    }

    private fun customCrash() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            CrashHelper.init(this, "") { crashInfo, e ->
                L.e(crashInfo)
                CrashActivity.Companion.Builder(this)
                        .setCrashInfo(crashInfo)
                        .setCrashImage(R.drawable.uix_crash_error_image)
                        .setRestartActivity(MainActivity::class.java)
                        .setTips("发生了崩溃，抱歉！")
                        .setButtonColor(Color.GRAY)
                        .launch()
                Process.killProcess(Process.myPid())
                exitProcess(1)
            }
        }
    }

    private fun customContainer() {
        ContainerActivity.registerCommandHandler { activity, command, containerLayoutId, _ ->
            when(command) {
                COMMAND_LAUNCH_WEBVIEW -> {
                    val fragment = WebviewFragment.Builder()
                            .setDarkTheme(true)
                            .setIndicatorColor(Color.BLUE)
                            .setUrl("https://weibo.com/5401152113/profile?rightmod=1&wvr=6&mod=personinfo")
                            .build()
                    activity.supportFragmentManager.beginTransaction().replace(containerLayoutId, fragment).commit()
                }
                COMMAND_LAUNCH_ABOUT -> {
                    val fragment = AboutFragment.Builder()
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
                                    activity.finish()
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
                                                    textSize = 14f,
                                                    getSubViewListener = object : GetSubViewListener {
                                                        override fun getSubView(): View {
                                                            return Button(activity).apply {
                                                                text = "Custom Button"
                                                            }
                                                        }
                                                    }
                                            ),
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
                                    ToastUtils.showShort("点击了 $iAboutItem")
                                }
                            })
                            .build()
                    activity.supportFragmentManager.beginTransaction().replace(containerLayoutId, fragment).commit()
                }
            }
        }
    }

    private fun customUIX() {
        UIXConfig.depthPageTransScale = .5f
        UIXConfig.Dialog.margin = UIXUtils.dp2px(30f)
        UIXConfig.Button.normalColor = Color.GRAY
    }
}