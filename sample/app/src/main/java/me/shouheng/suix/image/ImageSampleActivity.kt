package me.shouheng.suix.image

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.base.anno.ActivityConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.comn.PalmUtils
import me.shouheng.suix.databinding.ActivityMatisseSampleBinding
import me.shouheng.uix.pages.image.GalleryActivity
import me.shouheng.uix.pages.image.GifSizeFilter
import me.shouheng.uix.pages.image.Glide4Engine
import me.shouheng.uix.pages.image.TakePhotoActivity
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.permission.callback.OnGetPermissionCallback
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.FileUtils
import me.shouheng.utils.store.PathUtils
import java.io.File

@ActivityConfiguration(layoutResId = R.layout.activity_matisse_sample)
class ImageSampleActivity : CommonActivity<ActivityMatisseSampleBinding, EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) { /* noop */ }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x0001 && resultCode == Activity.RESULT_OK) {
            val urls = Matisse.obtainResult(data)
            ActivityUtils.open(GalleryActivity::class.java)
                    .put(GalleryActivity.EXTRA_GALLERY_IMAGES, ArrayList<Uri>(urls))
                    .launch(this)
        } else if (requestCode == 0x0002 && resultCode == Activity.RESULT_OK) {
            val path = data!!.getStringExtra(TakePhotoActivity.EXTRA_FILE_PATH)
            val url = PalmUtils.getUriFromFile(this, File(path))
            ActivityUtils.open(GalleryActivity::class.java)
                    .put(GalleryActivity.EXTRA_GALLERY_IMAGES, ArrayList<Uri>(listOf(url)))
                    .launch(this)
        }
    }

    fun onMatisseLight(v: View) {
        Matisse.from(this@ImageSampleActivity)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider", "test"))
                .maxSelectable(9)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                // 如果指定了 gridExpectedSize，将优先使用 gridExpectedSize 而不是 spanCount
                .spanCount(4)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                // .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(Glide4Engine())    // for glide-V4
                .setOnSelectedListener { _, pathList ->
                    // DO SOMETHING IMMEDIATELY HERE
                    L.e("onSelected", "onSelected: pathList=$pathList")
                }
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener { isChecked ->
                    // DO SOMETHING IMMEDIATELY HERE
                    L.e("isChecked", "onCheck: isChecked=$isChecked")
                }
                .forResult(0x0001)
    }

    fun onMatisseDark(v: View) {
        Matisse.from(this@ImageSampleActivity)
                .choose(MimeType.ofImage())
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .maxSelectable(9)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(Glide4Engine())
                .forResult(0x0001)
    }

    fun onGallery(v: View) {
        onMatisseDark(v)
    }

    @SuppressLint("MissingPermission")
    fun onPhoto(v: View) {
        // sample for custom camera
        // TakePhotoActivity.showSwitch = false
        val appDir = PathUtils.getExternalAppPicturesPath()
        FileUtils.createOrExistsDir(appDir)
        val fileName = "${System.currentTimeMillis()}.jpg"
        val filePath = File(appDir, fileName).path
        PermissionUtils.checkPermissions(this, OnGetPermissionCallback {
            TakePhotoActivity.launch(this@ImageSampleActivity, filePath, 0x0002)
        }, Permission.STORAGE, Permission.CAMERA)
    }
}