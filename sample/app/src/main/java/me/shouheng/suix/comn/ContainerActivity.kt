package me.shouheng.suix.comn

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityContainerBinding
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UColor
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.about.*
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ui.BarUtils
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

class ContainerActivity : CommonActivity<EmptyViewModel, ActivityContainerBinding>(), AboutFragment.FragmentInteraction {

    override fun getLayoutResId(): Int = R.layout.activity_container

    override fun onLeftClick(v: View) {
        finish()
    }

    override fun onRightClick(v: View) {
        toast("Right clicked!")
    }

    override fun onItemClick(item: IAboutItem, pos: Int) {
        toast("$item clicked!")
    }

    override fun loadSubViews(id: Int): View? {
        return if (id == 2) Button(activity).apply {
            text = "Custom Button"
        }
        else null
    }

    override fun loadImage(id: Int, iv: ImageView) {
        if (id == 2) iv.setImageResource(R.drawable.fat_ass)
        else if (id == 4) iv.setImageResource(R.drawable.mark_note)
    }

    override fun onNewVersionClick(v: View) {
        toast("new version 1.3.1!!!")
    }

    private var offHeight = 0
    private var toolbarHeight = UView.dp2px(80f)

    override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
        offHeight += dy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val alpha = (offHeight * 1f / toolbarHeight).coerceAtMost(1f)
            window.statusBarColor = UColor.computeColor(Color.LTGRAY, Color.WHITE, alpha)
            BarUtils.setStatusBarLightMode(this, alpha > .5f)
        }
    }

    override fun doCreateView(savedInstanceState: Bundle?) {
        val fragment = AboutFragment.Builder()
                .setBackgroundColor(Color.LTGRAY)
                .setForegroundColor(Color.WHITE)
                .setSlogan("?????? UI ?????????")
                .setSloganStyle(TextStyleBean().apply {
                    typeFace = Typeface.BOLD
                    textColor = Color.BLACK
                    textSize = 20f
                    gravity = Gravity.CENTER
                })
                .setVersion("???????????????????????????\n" +
                        "???????????????????????????\n" +
                        "???????????????????????????\n" +
                        "Poor lonely and homeless developer")
                .setNewerVersion("????????? 1.3.1")
                .setNewerVersionStyle(TextStyleBean(Color.RED, 12f, Typeface.ITALIC))
                .setVersionStyle(TextStyleBean().apply {
                    typeFace = Typeface.ITALIC
                    textColor = Color.GRAY
                    textSize = 12f
                    gravity = Gravity.CENTER
                })
                .setTitle("?????? UI ?????????")
                .setTitleStyle(TextStyleBean().apply {
                    textColor = Color.BLACK
                    typeFace = Typeface.BOLD
                    textSize = 20f
                })
                .setLogo(R.mipmap.ic_launcher)
                .setIconRight(R.drawable.ic_favorite_border_black_24dp)
                .setIconLeft(R.drawable.uix_arrow_back_black_24dp)
                .setAboutItems(
                        arrayListOf(
//                                AboutSectionItem(
//                                        id = 0,
//                                        title = ResUtils.getString(R.string.about_app_section)),
                                AboutTextItem(
                                        id = 1,
                                        text = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_app_detail)),
                                        textStyle = TextStyleBean().apply {
                                            textSize = 14f
                                        }
                                ),
                                AboutSectionItem(
                                        id = 0,
                                        title = ResUtils.getString(R.string.about_developer_section)),
                                AboutUserItem(
                                        id = 2,
                                        name = ResUtils.getString(R.string.about_developer_name),
                                        nameStyle = TextStyleBean().apply {
                                            textColor = Color.BLACK
                                            textSize = 14f
                                        },
                                        desc = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_developer_detail)),
                                        descStyle = TextStyleBean().apply {
                                            textColor = Color.GRAY
                                            textSize = 14f
                                        }),
                                AboutSectionItem(
                                        id = 0,
                                        title = ResUtils.getString(R.string.about_sources)),
                                AboutTextItem(
                                        id = 3,
                                        text = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_sources_detail)),
                                        textStyle = TextStyleBean().apply {
                                            textSize = 14f
                                        }),
                                AboutSectionItem(
                                        id = 0,
                                        title = ResUtils.getString(R.string.about_other_apps)),
                                AboutUserItem(
                                        id = 4,
                                        name = ResUtils.getString(R.string.about_other_marknote),
                                        nameStyle = TextStyleBean().apply {
                                            textColor = Color.BLACK
                                            textSize = 14f
                                        },
                                        desc = PalmUtils.getTextFromHtml(ResUtils.getString(R.string.about_other_marknote_desc)),
                                        descStyle = TextStyleBean().apply {
                                            textColor = Color.GRAY
                                            textSize = 14f
                                        })
                        ))
                .build()
        this.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}