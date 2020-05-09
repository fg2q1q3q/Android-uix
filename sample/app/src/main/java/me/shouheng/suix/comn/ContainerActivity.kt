package me.shouheng.suix.comn

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.mvvm.databinding.MvvmsActivityContainerBinding
import me.shouheng.suix.R
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.pages.about.*
import me.shouheng.utils.app.ResUtils

@ActivityConfiguration(layoutResId = R.layout.mvvms_activity_container)
class ContainerActivity : CommonActivity<MvvmsActivityContainerBinding, EmptyViewModel>(), AboutFragment.FragmentInteraction {

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

    override fun doCreateView(savedInstanceState: Bundle?) {
        val fragment = AboutFragment.Builder()
                .setBackgroundColor(Color.LTGRAY)
                .setForegroundColor(Color.WHITE)
                .setSlogan("安卓 UI 组件化")
                .setSloganStyle(TextStyleBean().apply {
                    typeFace = Typeface.BOLD
                    textColor = Color.BLACK
                    textSize = 20f
                    gravity = Gravity.CENTER
                })
                .setVersion("穷困潦倒的开发者啊\n" +
                        "穷困潦倒的开发者啊\n" +
                        "穷困潦倒的开发者啊\n" +
                        "Poor lonely and homeless developer")
                .setVersionStyle(TextStyleBean().apply {
                    typeFace = Typeface.ITALIC
                    textColor = Color.GRAY
                    textSize = 12f
                    gravity = Gravity.CENTER
                })
                .setTitle("关于")
                .setTitleStyle(TextStyleBean().apply {
                    textColor = Color.BLACK
                    typeFace = Typeface.BOLD
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