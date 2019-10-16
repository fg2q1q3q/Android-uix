package me.shouheng.suix.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityDialogBinding
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.config.BottomButtonPosition.Companion.BUTTON_POS_RIGHT
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_RIGHT_ONLY
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_THREE
import me.shouheng.uix.config.BottomButtonStyle.Companion.BUTTON_TWO
import me.shouheng.uix.config.DialogPosition.Companion.POS_BOTTOM
import me.shouheng.uix.config.DialogPosition.Companion.POS_TOP
import me.shouheng.uix.config.DialogStyle.Companion.STYLE_WRAP
import me.shouheng.uix.dialog.DialogUtils
import me.shouheng.uix.dialog.content.AddressContent
import me.shouheng.uix.dialog.content.IDialogContent
import me.shouheng.uix.dialog.content.SimpleEditor
import me.shouheng.uix.dialog.content.SimpleList
import me.shouheng.uix.dialog.footer.SimpleFooter
import me.shouheng.uix.dialog.title.IDialogTitle
import me.shouheng.uix.dialog.title.SimpleTitle

/**
 * 对话框示例
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 15:21
 */
@ActivityConfiguration(layoutResId = R.layout.activity_dialog)
class DialogActivity : CommonActivity<ActivityDialogBinding, EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
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
                            .setBottomStyle(BUTTON_RIGHT_ONLY)
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
                    .setDialogTitle(SimpleTitle.Builder().setTitleColor(Color.WHITE).setTitle("Title (white)").build())
                    .setDialogContent(SampleContent())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_THREE)
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
                    .setDialogTitle(SimpleTitle.Builder()
                            .setTitle("普通编辑对话框")
                            .build())
                    .setDialogContent(SimpleEditor.Builder().build())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_THREE)
                            .setLeftText("Left")
                            .setMiddleText("Middle")
                            .setRightText("Right")
                            .build())
                    .build().show(supportFragmentManager, "editor")
        }
        binding.btnEditorNumeric.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.Builder()
                            .setTitle("编辑对话框（数字|单行|长度10）")
                            .build())
                    .setDialogContent(SimpleEditor.Builder()
                            .setSingleLine(true)
                            .setNumeric(true)
                            .setContent("10086")
                            .setHint("在这里输入数字...")
                            .setBottomLineColor(Color.LTGRAY)
                            .setMaxLength(10)
                            .build())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setBottomStyle(BUTTON_TWO)
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
                    .setDialogTitle(SimpleTitle.Builder()
                            .setTitle("简单的列表")
                            .build())
                    .setDialogContent(SimpleList.Builder()
                            .setGravity(Gravity.CENTER)
                            .setTextSize(18f)
                            .setTextColor(Color.BLACK)
                            .setShowIcon(true)
                            .setList(listOf(
                                    SimpleList.Item(0, "第 1 项", resources.getDrawable(R.drawable.uix_eye_close_48)),
                                    SimpleList.Item(1, "第 2 项", resources.getDrawable(R.drawable.uix_eye_open_48)),
                                    SimpleList.Item(2, "第 3 项", resources.getDrawable(R.drawable.uix_close_black_24dp)),
                                    SimpleList.Item(3, "第 4 项", resources.getDrawable(R.drawable.uix_loading))
                            ))
                            .setOnItemClickListener(object : SimpleList.OnItemClickListener {
                                override fun onItemClick(item: SimpleList.Item) {
                                    toast("${item.id} : ${item.name}")
                                }
                            })
                            .build())
                    .build().show(supportFragmentManager, "list")
        }
        binding.btnLoading.setOnClickListener {
            DialogUtils.showLoading(this, "加载中...", true).show()
        }
        binding.btnAddress.setOnClickListener {
            BeautyDialog.Builder()
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.Builder()
                            .setTitle("地址对话框")
                            .build())
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
    }
}