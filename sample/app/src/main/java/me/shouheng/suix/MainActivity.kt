package me.shouheng.suix

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Looper
import android.view.View
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.ContainerActivity
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.databinding.ActivityMainBinding
import me.shouheng.suix.dialog.DialogActivity
import me.shouheng.suix.dialog.TipsActivity
import me.shouheng.suix.image.ImageSampleActivity
import me.shouheng.suix.pop.PopActivity
import me.shouheng.suix.setting.SettingFragment
import me.shouheng.suix.tools.ToolsActivity
import me.shouheng.suix.widget.LayoutActivity
import me.shouheng.suix.widget.LayoutActivity2
import me.shouheng.suix.widget.WidgetActivity

/**
 * Main page.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-12 15:40
 */
@ActivityConfiguration(layoutResId = R.layout.activity_main)
class MainActivity : CommonActivity<ActivityMainBinding, EmptyViewModel>() {

    private var dlg: AlertDialog? = null

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnTools.setOnClickListener { startActivity(ToolsActivity::class.java) }
        binding.btnDialogs.setOnClickListener { startActivity(DialogActivity::class.java) }
        binding.btnTips.setOnClickListener { startActivity(TipsActivity::class.java) }
        binding.btnPops.setOnClickListener { startActivity(PopActivity::class.java) }
        binding.btnMatisse.setOnClickListener { startActivity(ImageSampleActivity::class.java) }
        binding.btnWeb.setOnClickListener {
            ContainerActivity.open(COMMAND_LAUNCH_WEBVIEW).launch(this)
        }
        binding.btnWidgets.setOnClickListener { startActivity(WidgetActivity::class.java) }
        binding.btnLayout.setOnClickListener { startActivity(LayoutActivity::class.java) }
        binding.btnLayout2.setOnClickListener { startActivity(LayoutActivity2::class.java) }
        binding.btnSetting.setOnClickListener {
            ContainerActivity.open(SettingFragment::class.java).launch(this)
        }
        binding.btnAbout.setOnClickListener { startActivity(me.shouheng.suix.comn.ContainerActivity::class.java) }
        binding.btnCrash.setOnClickListener { produceException2() }
    }

    // 重现问题方式 1：利用反射修改字段属性
    private fun produceException1() {
        dlg = AlertDialog.Builder(context).create()
        dlg?.setView(View.inflate(context, R.layout.layout_dialog_title_sample, null))
        dlg?.show()
        val f = Dialog::class.java.getDeclaredField("mShowing")
        f.isAccessible = true
        f.setBoolean(dlg, false)
        dlg?.show()
    }

    // 重现问题方式 2：多线程中进行对话框显示操作
    private fun produceException2() {
        dlg = AlertDialog.Builder(context).create()
        dlg?.setView(View.inflate(context, R.layout.layout_dialog_title_sample, null))
        dlg?.show()
        dlg?.dismiss()
        for (i in 1..2) {
            Thread(Runnable {
                Looper.prepare()
                dlg?.show()
                Looper.loop()
            }).start()
        }
    }
}
