package me.shouheng.suix.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityDialogBinding
import me.shouheng.uix.common.anno.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.isLeft
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.isMiddle
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.isRight
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import me.shouheng.uix.common.anno.BottomButtonStyle.Companion.BUTTON_STYLE_TRIPLE
import me.shouheng.uix.common.anno.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.common.anno.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_HALF
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_TWO_THIRD
import me.shouheng.uix.common.anno.DialogStyle.Companion.STYLE_WRAP
import me.shouheng.uix.common.anno.EmptyViewState
import me.shouheng.uix.common.anno.LoadingStyle
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.rate.RatingManager
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.content.*
import me.shouheng.uix.widget.dialog.footer.SimpleFooter
import me.shouheng.uix.widget.dialog.title.SimpleTitle
import me.shouheng.uix.widget.image.CircleImageView
import me.shouheng.uix.widget.rv.EmptyView
import me.shouheng.uix.widget.rv.getAdapter
import me.shouheng.uix.widget.rv.onItemDebouncedClick
import me.shouheng.uix.widget.text.NormalTextView
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ui.ViewUtils
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * ???????????????
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 15:21
 */
class DialogActivity : CommonActivity<EmptyViewModel, ActivityDialogBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_dialog

    private var customList: CustomList? = null

    override fun doCreateView(savedInstanceState: Bundle?) {
        val builder = BeautyDialog.Builder()
            .setDarkDialog(true)
            .setDialogTitle(SimpleTitle.builder()
                .setTitle("???????????? [WHITE]")
                .setTitleStyle(TextStyleBean().apply {
                    textColor = Color.WHITE
                })
                .build())
            .setDialogContent(MultipleContent())
            .setDialogBottom(SimpleFooter.Builder()
                .setBottomStyle(BUTTON_STYLE_TRIPLE)
                .setLeftText("???")
                .setLeftTextStyle(TextStyleBean().apply {
                    textColor = Color.WHITE
                    textSize = 14f
                })
                .setMiddleText("???")
                .setMiddleTextStyle(TextStyleBean().apply {
                    textColor = Color.WHITE
                    textSize = 15f
                })
                .setRightText("???")
                .setRightTextStyle(TextStyleBean().apply {
                    textColor = Color.WHITE
                    textSize = 16f
                })
                .build())
            .setDialogPosition(POS_BOTTOM)

        binding.btnNoBg.setOnClickListener {
            BeautyDialog.Builder()
                .onDismiss { toast("Dismissed") }
                .onShow { toast("Showed") }
                .setDialogStyle(STYLE_WRAP)
                .setDialogBackground(null)
                .setDialogContent(UpgradeContent())
                .build().show(supportFragmentManager, "normal")
        }
        binding.btnNoBgOneThird.onDebouncedClick {
            builder.setDialogStyle(STYLE_TWO_THIRD)
                .build().show(supportFragmentManager, "normal")
        }
        binding.btnNoBgHalf.onDebouncedClick {
            builder.setDialogStyle(STYLE_HALF)
                .build().show(supportFragmentManager, "normal")
        }
        binding.btnNormal.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogStyle(STYLE_WRAP)
                .setDialogTitle(SimpleTitle.Builder()
                    .setTitle("???????????? [RED|18f]")
                    .setTitleStyle(TextStyleBean().apply {
                        textSize = 18f
                        textColor = Color.RED
                    })
                    .build())
                .setDialogContent(MultipleContent())
                .setDialogBottom(SimpleFooter.Builder()
                    .setBottomStyle(BUTTON_STYLE_SINGLE)
                    .setMiddleText("OK")
                    .setMiddleTextStyle(TextStyleBean().apply {
                        textColor = Color.RED
                        typeFace = Typeface.BOLD
                    })
                    .setOnClickListener { dialog, _, _, _ ->
                        dialog.dismiss()
                    }.build())
                .build().show(supportFragmentManager, "normal")
        }
        binding.btnNormalTop.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogTitle(SimpleTitle.Builder()
                    .setTitle("???????????? [BOLD|LEFT]")
                    .setTitleStyle(TextStyleBean().apply {
                        gravity = Gravity.START
                        typeFace = Typeface.BOLD
                    })
                    .build())
                .setDialogContent(MultipleContent())
                .setDialogBottom(SampleFooter())
                .setDialogPosition(POS_TOP)
                .build().show(supportFragmentManager, "normal_top")
        }
        binding.btnNormalBottom.setOnClickListener {
            BeautyDialog.Builder()
                .setDarkDialog(true)
                .setDialogTitle(SimpleTitle.builder()
                    .setTitle("???????????? [WHITE]")
                    .setTitleStyle(TextStyleBean().apply {
                        textColor = Color.WHITE
                    })
                    .build())
                .setDialogContent(MultipleContent())
                .setDialogBottom(SimpleFooter.Builder()
                    .setBottomStyle(BUTTON_STYLE_TRIPLE)
                    .setLeftText("???")
                    .setLeftTextStyle(TextStyleBean().apply {
                        textColor = Color.WHITE
                        textSize = 14f
                    })
                    .setMiddleText("???")
                    .setMiddleTextStyle(TextStyleBean().apply {
                        textColor = Color.WHITE
                        textSize = 15f
                    })
                    .setRightText("???")
                    .setRightTextStyle(TextStyleBean().apply {
                        textColor = Color.WHITE
                        textSize = 16f
                    })
                    .build())
                .setDialogPosition(POS_BOTTOM)
                .build().show(supportFragmentManager, "normal_bottom")
        }
        binding.btnEditorNormal.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogStyle(STYLE_HALF)
                .setDialogCornerRadius(UView.dp2px(4f))
                .setDialogTitle(SimpleTitle.get("????????????????????? [?????????]"))
                .setDialogContent(SimpleEditor.Builder()
                    .setClearDrawable(ResUtils.getDrawable(R.drawable.ic_cancel_black_24dp))
                    .build())
                .setDialogBottom(SimpleFooter.Builder()
                    .setBottomStyle(BUTTON_STYLE_TRIPLE)
                    .setLeftText("Left")
                    .setMiddleText("Middle")
                    .setRightText("Right")
                    .setOnClickListener { _, p, _, _ ->
                        when {
                            isLeft(p) -> {
                                toast("Left")
                            }
                            isRight(p) -> {
                                toast("Right")
                            }
                            isMiddle(p) -> {
                                toast("Middle")
                            }
                        }
                    }
                    .build())
                .build().show(supportFragmentManager, "editor")
        }
        binding.btnEditorNumeric.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogStyle(STYLE_TWO_THIRD)
                .setDialogTitle(SimpleTitle.get("????????????????????????|??????|??????10???"))
                .setDialogContent(SimpleEditor.Builder()
                    .setSingleLine(true)
                    .setNumeric(true)
                    .setContent("10086")
                    .setHint("?????????????????????...")
                    .setBottomLineColor(Color.LTGRAY)
                    .setMaxLength(10)
                    .build())
                .setDialogBottom(SimpleFooter.Builder()
                    .setBottomStyle(BUTTON_STYLE_DOUBLE)
                    .setLeftText("??????")
                    .setRightText("??????")
                    .setDividerColor(Color.LTGRAY)
                    .setRightTextStyle(TextStyleBean().apply {
                        textColor = Color.GRAY
                    })
                    .setRightTextStyle(TextStyleBean().apply {
                        textColor = Color.RED
                    })
                    .setOnClickListener { dialog, position, _, content ->
                        if (isLeft(position)) dialog.dismiss()
                        else if (isRight(position)) {
                            toast((content as SimpleEditor).getContent())
                        }
                    }.build())
                .build().show(supportFragmentManager, "editor")
        }
        binding.btnListNormal.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogPosition(POS_BOTTOM)
                .setDialogTitle(SimpleTitle.get("???????????????"))
                .setDialogContent(SimpleList.Builder()
                    .setTextStyle(TextStyleBean().apply {
                        gravity = Gravity.CENTER
                        textSize = 14f
                        textColor = Color.BLACK
                        typeFace = Typeface.BOLD
                    })
                    .setShowIcon(true)
                    .setList(listOf(
                        SimpleList.Item(0, "????????????????????????????????????????????????\n" +
                                "????????????????????????????????????????????????",
                            ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(1, "????????????????????????????????????\n" +
                                "????????????????????????????????????",
                            ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(2, "????????????????????????????????????\n" +
                                "????????????????????????????????????",
                            ResUtils.getDrawable(R.drawable.uix_close_black_24dp),
                            Gravity.START),
                        SimpleList.Item(3, "????????????????????????????????????\n" +
                                "????????????????????????????????????",
                            ResUtils.getDrawable(R.drawable.uix_loading),
                            Gravity.END)
                    ))
                    .setOnItemClickListener { dialog, item ->
                        toast("${item.id} : ${item.content}")
                        dialog.dismiss()
                    }.build())
                .build().show(supportFragmentManager, "list")
        }
        binding.btnAddress.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogPosition(POS_BOTTOM)
                .setDialogMargin(UView.dp2px(8f))
                .setDialogTitle(SimpleTitle.get("???????????????"))
                .setDialogContent(AddressContent.Builder()
                    .setMaxLevel(LEVEL_AREA)
                    .setOnAddressSelectedListener { dialog: BeautyDialog, province: String, city: String?, area: String? ->
                        toast("$province - $city - $area")
                        dialog.dismiss()
                    }.build())
                .build().show(supportFragmentManager, "list")
        }
        binding.btnContent.setOnClickListener {
            BeautyDialog.Builder()
                .setDialogStyle(STYLE_TWO_THIRD)
                .setOutCancelable(true)
                .setDialogPosition(POS_BOTTOM)
                .setDialogTitle(SimpleTitle.get("?????????????????????"))
                .setDialogContent(SimpleContent.get("????????????????????????????????????????????????????????? ????????????????????????????????????????????????????????? ???????????????????????????????????????????????? ???????????????????????????????????????????????? ???????????????????????????????????????????????? ???????????????????????????????????????????????? ?????????????????????????????????????????? ???????????????????????????????????????????????? ???????????????????????????????????????????????? ???????????????????????????????????????????????? ???????????????????????????????????????????????? ????????????????????????????????????????????????????????????????????????"))
                .build().show(supportFragmentManager, "list")
        }
        binding.btnCustomList.setOnClickListener {
            val adapter = getAdapter(R.layout.uix_dialog_content_list_simple_item, { helper, item: SimpleList.Item ->
                val tv = helper.getView<NormalTextView>(me.shouheng.uix.widget.R.id.tv)
                tv.text = item.content
                item.gravity?.let { tv.gravity = it }
                item.icon?.let { helper.setImageDrawable(me.shouheng.uix.widget.R.id.iv, it) }
            }, emptyList())
            val ev = EmptyView.Builder(this)
                .setEmptyLoadingTips("loading")
                .setEmptyLoadingTipsColor(Color.BLUE)
                .setLoadingStyle(LoadingStyle.STYLE_IOS)
                .setEmptyViewState(EmptyViewState.STATE_LOADING)
                .build()
            customList = CustomList.Builder(this)
                .setEmptyView(ev)
                .setAdapter(adapter)
                .build()
            adapter.onItemDebouncedClick { _, _, pos ->
                val item = adapter.data[pos]
                toast("${item.id} : ${item.content}")
                customList?.getDialog()?.dismiss()
            }
            BeautyDialog.Builder()
                .setFixedHeight(ViewUtils.getScreenHeight()/2)
                .setOutCancelable(true)
                .setDialogPosition(POS_BOTTOM)
                .setDialogTitle(SimpleTitle.get("????????????????????????"))
                .setDialogContent(customList!!)
                .build().show(supportFragmentManager, "custom-list")
            // ??????????????????????????????????????????
            Handler().postDelayed({
                ev.hide()
                adapter.setNewData(listOf(
                    SimpleList.Item(0, "??? 1 ???", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                    SimpleList.Item(1, "??? 2 ???", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                    SimpleList.Item(2, "??? 3 ???", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                    SimpleList.Item(3, "??? 4 ???", ResUtils.getDrawable(R.drawable.uix_loading)),
                    SimpleList.Item(4, "??? 5 ???", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                    SimpleList.Item(5, "??? 6 ???", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                    SimpleList.Item(6, "??? 7 ???", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                    SimpleList.Item(7, "??? 8 ???", ResUtils.getDrawable(R.drawable.uix_loading)),
                    SimpleList.Item(8, "??? 9 ???", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                    SimpleList.Item(9, "??? 10 ???", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                    SimpleList.Item(10, "??? 11 ???", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                    SimpleList.Item(11, "??? 12 ???", ResUtils.getDrawable(R.drawable.uix_loading)),
                    SimpleList.Item(12, "??? 13 ???", ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                    SimpleList.Item(13, "??? 14 ???", ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                    SimpleList.Item(14, "??? 15 ???", ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                    SimpleList.Item(15, "??? 16 ???", ResUtils.getDrawable(R.drawable.uix_loading))
                ))
            }, 3000)
        }
        binding.btnNotCancelable.setOnClickListener {
            BeautyDialog.Builder()
                .setBackCancelable(false)
                .setOutCancelable(false)
                .setDialogStyle(STYLE_WRAP)
                .setDialogBackground(null)
                .setDialogContent(UpgradeContent())
                .build().show(supportFragmentManager, "not cancelable")
        }
        binding.btnRateIntro.onDebouncedClick {
            RatingManager.marketTitle = "??????????????????"
            RatingManager.rateApp(STYLE_WRAP, { toast("??????????????????") }, { toast("???????????????????????????") }, supportFragmentManager)
        }
        binding.btnSimpleGrid.onDebouncedClick {
            BeautyDialog.Builder()
                .setDialogMargin(0)
                .setDialogPosition(POS_BOTTOM)
                .setDialogTitle(SimpleTitle.get("???????????????"))
                .setDialogContent(
                    SimpleGrid.Builder(R.layout.item_tool_color) { helper: BaseViewHolder, item: Int ->
                        helper.getView<CircleImageView>(R.id.iv).setFillingCircleColor(item)
                    }.setList(listOf(
                        ResUtils.getColor(R.color.tool_item_color_1),
                        ResUtils.getColor(R.color.tool_item_color_2),
                        ResUtils.getColor(R.color.tool_item_color_3),
                        ResUtils.getColor(R.color.tool_item_color_4),
                        ResUtils.getColor(R.color.tool_item_color_5),
                        ResUtils.getColor(R.color.tool_item_color_6),
                        ResUtils.getColor(R.color.tool_item_color_7),
                        ResUtils.getColor(R.color.tool_item_color_8),
                        ResUtils.getColor(R.color.tool_item_color_9),
                        ResUtils.getColor(R.color.tool_item_color_10),
                        ResUtils.getColor(R.color.tool_item_color_11),
                        ResUtils.getColor(R.color.tool_item_color_12)
                    )).setOnItemClickListener { _: BeautyDialog, item: Int ->
                        toast("$item")
                    }.setSpanCount(5).build()
                ).build().show(supportFragmentManager, "grid")
        }
    }
}