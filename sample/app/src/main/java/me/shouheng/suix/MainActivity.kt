package me.shouheng.suix

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.ContainerActivity
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.databinding.ActivityMainBinding
import me.shouheng.suix.dialog.DialogActivity
import me.shouheng.suix.setting.SettingFragment
import me.shouheng.suix.widget.WidgetActivity
import me.shouheng.uix.image.GifSizeFilter
import me.shouheng.uix.image.Glide4Engine
import me.shouheng.uix.page.GalleryActivity
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection

/**
 * Main
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-12 15:40
 */
@ActivityConfiguration(layoutResId = R.layout.activity_main)
class MainActivity : CommonActivity<ActivityMainBinding, EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnDialogs.setOnClickListener {
            ActivityUtils.start(this, DialogActivity::class.java)
        }
        binding.btnMatisse.setOnClickListener {
            Matisse.from(this)
                    .choose(MimeType.ofAll())
                    .theme(R.style.Matisse_Dracula) // or R.style.Matisse_Zhihu
                    .countable(false)
                    .addFilter(GifSizeFilter(320, 320, 0))
                    .maxSelectable(10)
                    .originalEnable(true)
                    .maxOriginalSize(5)
                    .imageEngine(Glide4Engine())
                    .capture(false)
                    .forResult(0x0001)
            ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_FORWARD)
        }
        binding.btnWeb.setOnClickListener {
            ContainerActivity.open(COMMAND_LAUNCH_WEBVIEW).launch(this)
        }
        binding.btnWidgets.setOnClickListener {
            ActivityUtils.start(this, WidgetActivity::class.java)
        }
        binding.btnSetting.setOnClickListener {
            ContainerActivity.open(SettingFragment::class.java).launch(this)
        }
        binding.btnAbout.setOnClickListener {
            ContainerActivity.open(COMMAND_LAUNCH_ABOUT).launch(this)
        }
        binding.btnCrash.setOnClickListener { 1/0 }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x0001 && resultCode == Activity.RESULT_OK) {
            val urls = Matisse.obtainResult(data)
            toast(urls.toTypedArray().contentToString())
            ActivityUtils.open(GalleryActivity::class.java)
                    .put(GalleryActivity.EXTRA_GALLERY_IMAGES, ArrayList<Uri>(urls))
                    .launch(this)
        }
    }
}