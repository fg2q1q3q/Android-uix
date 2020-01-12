package me.shouheng.suix.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityDialogBinding
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_STYLE_TRIPLE
import me.shouheng.uix.config.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.config.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_WRAP
import me.shouheng.uix.config.EmptyLoadingStyle
import me.shouheng.uix.config.EmptyViewState
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.dialog.MessageDialog
import me.shouheng.uix.dialog.content.*
import me.shouheng.uix.dialog.footer.SimpleFooter
import me.shouheng.uix.dialog.title.IDialogTitle
import me.shouheng.uix.dialog.title.SimpleTitle
import me.shouheng.uix.rv.EmptyView
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ui.ImageUtils
import me.shouheng.utils.ui.ViewUtils

/**
 * 对话框示例
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 15:21
 */
@ActivityConfiguration(layoutResId = R.layout.activity_dialog)
class DialogActivity : CommonActivity<ActivityDialogBinding, EmptyViewModel>() {

    private var customList: CustomList? = null

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnNoBg.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogStyle(STYLE_WRAP)
                    .setDialogBackground(null)
                    .setDialogContent(SampleContent())
                    .build().show(supportFragmentManager, "normal")
        }
        binding.btnNormal.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogStyle(STYLE_WRAP)
                    .setDialogTitle(SimpleTitle.Builder()
                            .setTitle("Title (red/18)")
                            .setTitleSize(18f)
                            .setTitleColor(Color.RED)
                            .build())
                    .setDialogContent(SampleContent())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_STYLE_SINGLE)
                            .setRightText("OK")
                            .setRightTextColor(Color.RED)
                            .setOnClickListener(object : SimpleFooter.OnClickListener {
                                override fun onClick(dialog: BeautyDialog, buttonPos: Int, dialogTitle: IDialogTitle?, dialogContent: IDialogContent?) {
                                    dialog.dismiss()
                                }
                            }).build())
                    .build().show(supportFragmentManager, "normal")
        }
        binding.btnNormalTop.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.Builder()
                            .setTitle("Title (bold|left)")
                            .setGravity(Gravity.START)
                            .setTitleTypeface(Typeface.BOLD)
                            .build())
                    .setDialogContent(SampleContent())
                    .setDialogBottom(SampleFooter())
                    .setDialogPosition(POS_TOP)
                    .build().show(supportFragmentManager, "normal_top")
        }
        binding.btnNormalBottom.setOnClickListener {
            BeautyDialog.Builder()
                    .setDarkDialog(true)
                    .setDialogTitle(SimpleTitle.builder().setTitleColor(Color.WHITE).setTitle("Title (white)").build())
                    .setDialogContent(SampleContent())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_STYLE_TRIPLE)
                            .setLeftText("Left")
                            .setLeftTextColor(Color.WHITE)
                            .setMiddleText("Middle")
                            .setMiddleTextColor(Color.WHITE)
                            .setRightText("Right")
                            .setRightTextColor(Color.WHITE)
                            .build())
                    .setDialogPosition(POS_BOTTOM)
                    .build().show(supportFragmentManager, "normal_bottom")
        }
        binding.btnEditorNormal.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogCornerRadius(ViewUtils.dp2px(25f))
                    .setDialogTitle(SimpleTitle.get("普通编辑对话框"))
                    .setDialogContent(SimpleEditor.Builder()
                            .setClearDrawable(ResUtils.getDrawable(R.drawable.ic_cancel_black_24dp))
                            .build())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_STYLE_TRIPLE)
                            .setLeftText("Left")
                            .setMiddleText("Middle")
                            .setRightText("Right")
                            .build())
                    .build().show(supportFragmentManager, "editor")
        }
        binding.btnEditorNumeric.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.get("编辑对话框（数字|单行|长度10）"))
                    .setDialogContent(SimpleEditor.Builder()
                            .setSingleLine(true)
                            .setNumeric(true)
                            .setContent("10086")
                            .setHint("在这里输入数字...")
                            .setBottomLineColor(Color.LTGRAY)
                            .setMaxLength(10)
                            .build())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_STYLE_DOUBLE)
                            .setLeftText("Cancel")
                            .setRightText("Confirm")
                            .setDividerColor(Color.LTGRAY)
                            .setLeftTextColor(Color.GRAY)
                            .setRightTextColor(Color.BLACK)
                            .setOnClickListener(object : SimpleFooter.OnClickListener {
                                override fun onClick(dialog: BeautyDialog, buttonPos: Int, dialogTitle: IDialogTitle?, dialogContent: IDialogContent?) {
                                    if (buttonPos == BUTTON_POS_LEFT) dialog.dismiss()
                                    else if (buttonPos == BUTTON_POS_RIGHT) {
                                        toast((dialogContent as SimpleEditor).getContent())
                                    }
                                }
                            }).build())
                    .build().show(supportFragmentManager, "editor")
        }
        binding.btnListNormal.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("简单的列表"))
                    .setDialogContent(SimpleList.Builder()
                            .setGravity(Gravity.CENTER)
                            .setTextSize(18f)
                            .setTextColor(Color.BLACK)
                            .setShowIcon(true)
                            .setList(listOf(
                                    SimpleList.Item(0, "秦时明月汉时关，万里长征人未还。\n" +
                                            "但使龙城飞将在，不教胡马度阴山。",
                                            ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                                    SimpleList.Item(1, "春眠不觉晓，处处闻啼鸟。\n" +
                                            "夜来风雨声，花落知多少。",
                                            ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                                    SimpleList.Item(2, "君自故乡来，应知故乡事。\n" +
                                            "来日绮窗前，寒梅著花未？",
                                            ResUtils.getDrawable(R.drawable.uix_close_black_24dp),
                                            Gravity.START),
                                    SimpleList.Item(3, "松下问童子，言师采药去。\n" +
                                            "只在此山中，云深不知处。",
                                            ResUtils.getDrawable(R.drawable.uix_loading),
                                            Gravity.END)
                            ))
                            .setOnItemClickListener(object : SimpleList.OnItemClickListener {
                                override fun onItemClick(dialog: BeautyDialog, item: SimpleList.Item) {
                                    toast("${item.id} : ${item.content}")
                                    dialog.dismiss()
                                }
                            })
                            .build())
                    .build().show(supportFragmentManager, "list")
        }
        binding.btnLoading.setOnClickListener {
            if (it.tag == null) {
                it.tag = "0"
                val dlg = MessageDialog.showLoading(
                        this,
                        "【加载中...】\n2 秒之后自动关闭",
                        false)
                dlg.show()
                Handler().postDelayed({ MessageDialog.hide(dlg) }, 2000)
            } else {
                it.tag = null
                val dlg = MessageDialog.builder(
                        "【抱歉，出错了！】\n2 秒之后自动关闭",
                        false,
                        isAnimation = false,
                        icon = ImageUtils.tintDrawable(R.drawable.uix_error_outline_black_24dp, Color.WHITE)
                ).withTextColor(Color.BLUE).withTypeFace(Typeface.ITALIC).withBorderRadiusInDp(20f).build(context)
                dlg.show()
                Handler().postDelayed({ MessageDialog.hide(dlg) }, 2000)
            }
        }
        binding.btnLoadingCancelable.setOnClickListener {
            if (it.tag == null) {
                it.tag = "0"
                MessageDialog.showLoading(
                        this,
                        "加载中...",
                        true
                ).show()
            } else {
                it.tag = null
                MessageDialog.builder(
                        "君不見黃河之水天上來，奔流到海不復回。 君不見高堂明鏡悲白髮，朝如青絲暮成雪。 人生得意須盡歡，莫使金樽空對月。 天生我材必有用，千金散盡還復來。 烹羊宰牛且爲樂，會須一飲三百杯。 岑夫子，丹丘生。將進酒，杯莫停。 與君歌一曲，請君爲我側耳聽。 鐘鼓饌玉不足貴，但願長醉不願醒。 古來聖賢皆寂寞，惟有飲者留其名。 陳王昔時宴平樂，斗酒十千恣讙謔。 主人何為言少錢？徑須沽取對君酌。 五花馬，千金裘。呼兒將出換美酒，與爾同銷萬古愁。",
                        true,
                        loadingStyle = EmptyLoadingStyle.STYLE_ANDROID
                ).withTextSize(12f).build(context).show()
            }
        }
        binding.btnAddress.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogMargin(0)
                    .setDialogTitle(SimpleTitle.get("地址对话框"))
                    .setDialogContent(AddressContent.Builder()
                            .setMaxLevel(LEVEL_AREA)
                            .setOnAddressSelectedListener(object: AddressContent.OnAddressSelectedListener {
                                override fun onAddressSelected(dialog: BeautyDialog, province: String, city: String?, area: String?) {
                                    toast("$province - $city - $area")
                                    dialog.dismiss()
                                }
                            })
                            .build())
                    .build().show(supportFragmentManager, "list")
        }
        binding.btnContent.setOnClickListener {
            BeautyDialog.Builder()
                    .setOutCancelable(true)
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("简单内容对话框"))
                    .setDialogContent(SimpleContent.get("简单内容对话框"))
                    .build().show(supportFragmentManager, "list")
        }
        binding.btnCustomList.setOnClickListener {
            val adapter = SimpleList.Adapter(emptyList(), Color.BLACK)
            val ev = EmptyView.Builder(this)
                    .setEmptyLoadingTips("loading")
                    .setEmptyLoadingTipsColor(Color.BLUE)
                    .setLoadingStyle(EmptyLoadingStyle.STYLE_IOS)
                    .setEmptyViewState(EmptyViewState.STATE_LOADING)
                    .build()
            customList = CustomList.Builder(this)
                    .setEmptyView(ev)
                    .setAdapter(adapter)
                    .build()
            adapter.setOnItemClickListener { _, _, pos ->
                val item = adapter.data[pos]
                toast("${item.id} : ${item.content}")
                customList?.getDialog()?.dismiss()
            }
            BeautyDialog.Builder()
                    .setFixedHeight(ViewUtils.getScreenHeight()/2)
                    .setOutCancelable(true)
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("自定义列表对话框"))
                    .setDialogContent(customList!!)
                    .build().show(supportFragmentManager, "custom-list")
            // 先显示对话框再加载数据的情形
            Handler().postDelayed({
                ev.hide()
                adapter.setNewData(listOf(
                        SimpleList.Item(0, "第 1 项", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(1, "第 2 项", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(2, "第 3 项", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(3, "第 4 项", ResUtils.getDrawable(R.drawable.uix_loading)),
                        SimpleList.Item(4, "第 5 项", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(5, "第 6 项", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(6, "第 7 项", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(7, "第 8 项", ResUtils.getDrawable(R.drawable.uix_loading)),
                        SimpleList.Item(8, "第 9 项", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(9, "第 10 项", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(10, "第 11 项", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(11, "第 12 项", ResUtils.getDrawable(R.drawable.uix_loading)),
                        SimpleList.Item(12, "第 13 项", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(13, "第 14 项", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(14, "第 15 项", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(15, "第 16 项", ResUtils.getDrawable(R.drawable.uix_loading))
                ))
            }, 3000)
        }
    }
}