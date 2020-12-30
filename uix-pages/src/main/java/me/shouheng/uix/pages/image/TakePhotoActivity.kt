package me.shouheng.uix.pages.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.annotation.RequiresPermission
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import me.shouheng.icamera.CameraView
import me.shouheng.icamera.enums.CameraFace
import me.shouheng.icamera.enums.FlashMode
import me.shouheng.icamera.enums.MediaType
import me.shouheng.icamera.listener.CameraOpenListener
import me.shouheng.icamera.listener.CameraPhotoListener
import me.shouheng.icamera.listener.CameraVideoListener
import me.shouheng.uix.common.anno.BottomButtonPosition
import me.shouheng.uix.common.anno.DialogStyle
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.common.utils.f
import me.shouheng.uix.pages.R
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.content.SimpleContent
import me.shouheng.uix.widget.dialog.content.SimpleList
import me.shouheng.uix.widget.dialog.footer.SimpleFooter
import me.shouheng.uix.widget.dialog.title.IDialogTitle
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.*
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.SPUtils
import me.shouheng.utils.ui.ImageUtils
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class TakePhotoActivity : AppCompatActivity() {

    private lateinit var cv: CameraView
    private lateinit var hLines: View
    private lateinit var vLines: View
    private lateinit var flRay: View
    private lateinit var ivClose: AppCompatImageView
    private lateinit var ivAlbum: AppCompatImageView
    private lateinit var tvNum: AppCompatTextView

    private var singleMode: Boolean = true
    private var path: String? = null
    /** Results when trying to get multiple images */
    private val results = mutableListOf<String>()

    private var recording = AtomicBoolean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uix_activity_take_photo)

        val showFlash = intent.getBooleanExtra(EXTRA_SHOW_FLASH, true)
        val showSwitch = intent.getBooleanExtra(EXTRA_SHOW_SWITCH, true)
        val mediaType = intent.getIntExtra(EXTRA_MEDIA_TYPE, MediaType.TYPE_PICTURE)
        val showGrid = intent.getBooleanExtra(EXTRA_SHOW_GRID, false)
        val showRay = intent.getBooleanExtra(EXTRA_SHOW_RAY, false)
        val showAlbum = intent.getBooleanExtra(EXTRA_SHOW_ALBUM, true)
        singleMode = intent.getBooleanExtra(EXTRA_IS_SINGLE_MODE, true)
        path = intent.getStringExtra(EXTRA_FILE_PATH_OR_PATHS)

        cv = f(R.id.cv)
        cv.mediaType = mediaType
        cv.isVoiceEnable = SPUtils.get().getBoolean(SETTING_CAMERA_VOICE_ENABLED, true)
        cv.switchCamera(SPUtils.get().getInt(SETTING_CAMERA_FACE, CameraFace.FACE_REAR))

        val ivFlash = f<AppCompatImageView>(R.id.iv_flash)
        ivFlash.setImageResource(
                when(cv.flashMode) {
                    FlashMode.FLASH_AUTO -> R.drawable.uix_flash_auto_white_24dp
                    FlashMode.FLASH_OFF -> R.drawable.uix_flash_on_white_24dp
                    FlashMode.FLASH_ON -> R.drawable.uix_flash_off_white_24dp
                    else -> R.drawable.uix_flash_auto_white_24dp
                }
        )
        ivFlash.gone(!showFlash)
        f<AppCompatImageView>(R.id.iv_switch).gone(!showSwitch)

        hLines = f(R.id.fl_h_line)
        vLines = f(R.id.fl_v_line)
        hLines.gone(!showGrid || !SPUtils.get().getBoolean(SETTING_CAMERA_SHOW_GRID, true))
        vLines.gone(!showGrid || !SPUtils.get().getBoolean(SETTING_CAMERA_SHOW_GRID, true))

        flRay = f(R.id.fl_ray)
        flRay.gone(!showRay || !SPUtils.get().getBoolean(SETTING_CAMERA_SHOW_RAY, true))
        f<AppCompatImageView>(R.id.iv_ray).scaleUpDown(2000)

        ivAlbum = f(R.id.iv_album)
        ivAlbum.gone(!showAlbum)
        ivClose = f(R.id.iv_close)
        tvNum = f(R.id.tv_num)
    }

    fun onShot(v: View) {
        if (path == null) return
        if (cv.mediaType == MediaType.TYPE_PICTURE) {
            val file = if (singleMode) File(path) else File(path, "${now()}.jpg")
            cv.takePicture(file, object : CameraPhotoListener {
                override fun onCaptureFailed(throwable: Throwable?) {
                    ULog.e("" + throwable)
                }

                override fun onPictureTaken(data: ByteArray?, picture: File) {
                    if (singleMode) {
                        val i = Intent().putStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS, arrayListOf(path))
                        setResult(Activity.RESULT_OK, i)
                        onBackPressed()
                    } else {
                        results.add(file.path)
                        ivClose.setImageResource(R.drawable.uix_done_24)
                        ivAlbum.isEnabled = false
                        tvNum.text = "${results.size}"
                        tvNum.background = ImageUtils.getDrawable(Color.WHITE, 10f.dp().toFloat(), 2f.dp(), Color.BLACK)
                        try {
                            val options = BitmapFactory.Options()
                            options.inJustDecodeBounds = true
                            BitmapFactory.decodeFile(picture.path, options)
                            options.inSampleSize = options.outWidth/200f.dp()
                            ULog.d("inSampleSize : ${options.inSampleSize}")
                            options.inJustDecodeBounds = false
                            var bitmap = BitmapFactory.decodeFile(picture.path, options)
                            val rotate = ImageUtils.getRotateDegree(picture.path)
                            bitmap = ImageUtils.rotate(bitmap, rotate, bitmap.width*.5f, bitmap.height*.5f)
                            ivAlbum.setImageBitmap(bitmap)
                        } catch (e: Exception) {
                            ULog.e("$e")
                        }
                    }
                    // resume preview
                    cv.resumePreview()
                }
            })
        } else {
            cv.startVideoRecord(File(path!!), object : CameraVideoListener {
                override fun onVideoRecordStart() {
                    recording.set(true)
                }

                override fun onVideoRecordStop(file: File?) {
                    recording.set(false)
                    val i = Intent().putStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS, arrayListOf(path))
                    setResult(Activity.RESULT_OK, i)
                    onBackPressed()
                }

                override fun onVideoRecordError(throwable: Throwable?) {
                    recording.set(false)
                    ULog.e("" + throwable)
                }
            })
        }
    }

    fun onSwitch(v: View) {
        cv.switchCamera(if (cv.cameraFace == CameraFace.FACE_FRONT)
            CameraFace.FACE_REAR else CameraFace.FACE_FRONT)
    }

    fun onAlbum(v: View) {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .addFilter(GifSizeFilter(320, 320, 0))
                // set the minimum required image width and height
                .maxSelectable(if (singleMode) 1 else 9)
                .originalEnable(true)
                .maxOriginalSize(5)
                .imageEngine(Glide4Engine())
                .forResult(REQ_PICK_FROM_ALBUM)
        ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_FORWARD)
    }

    fun onClose(v: View) {
        if (results.isNotEmpty()) {
            val paths = arrayListOf<String>()
            paths.addAll(results)
            val i = Intent().putStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS, paths)
            setResult(Activity.RESULT_OK, i)
        }
        super.onBackPressed()
        ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
    }

    fun onSetting(v: View) {
        val list = listOf(
                SimpleList.Item(0, stringOf(R.string.uix_camera_voice_enable),
                        drawableOf(if (cv.isVoiceEnable) {
                            R.drawable.uix_volume_up_white_24dp
                        } else {
                            R.drawable.uix_volume_off_white_24dp
                        })),
                SimpleList.Item(1, stringOf(R.string.uix_camera_show_ray), drawableOf(R.drawable.uix_gradient_24)),
                SimpleList.Item(2, stringOf(R.string.uix_camera_show_grid), drawableOf(R.drawable.uix_grid_on_24))
        )
        BeautyDialog.Builder()
                .setDarkDialog(true)
                .setDialogContent(SimpleList.builder()
                        .setTextStyle(TextStyleBean(textColor = Color.WHITE, textSize = 18f))
                        .setList(list)
                        .setOnItemClickListener(object : SimpleList.OnItemClickListener {
                            override fun onItemClick(dialog: BeautyDialog, item: SimpleList.Item) {
                                if (item.id == 0) {
                                    cv.isVoiceEnable = !cv.isVoiceEnable
                                    SPUtils.get().put(SETTING_CAMERA_VOICE_ENABLED, cv.isVoiceEnable)
                                } else if (item.id == 1) {
                                    if (flRay.visibility == View.VISIBLE) {
                                        flRay.visibility = View.GONE
                                        SPUtils.get().put(SETTING_CAMERA_SHOW_RAY, false)
                                    } else {
                                        flRay.visibility = View.VISIBLE
                                        SPUtils.get().put(SETTING_CAMERA_SHOW_RAY, true)
                                    }
                                } else if (item.id == 2) {
                                    if (vLines.visibility == View.VISIBLE) {
                                        vLines.visibility = View.GONE
                                        hLines.visibility = View.GONE
                                        SPUtils.get().put(SETTING_CAMERA_SHOW_GRID, false)
                                    } else {
                                        vLines.visibility = View.VISIBLE
                                        hLines.visibility = View.VISIBLE
                                        SPUtils.get().put(SETTING_CAMERA_SHOW_GRID, true)
                                    }
                                }
                                dialog.dismiss()
                            }
                        }).build())
                .build().show(supportFragmentManager, "setting")
    }

    fun onFlash(v: View) {
        val mode = when(cv.flashMode) {
            FlashMode.FLASH_AUTO -> FlashMode.FLASH_ON
            FlashMode.FLASH_OFF -> FlashMode.FLASH_AUTO
            FlashMode.FLASH_ON -> FlashMode.FLASH_OFF
            else -> FlashMode.FLASH_AUTO
        }
        cv.flashMode = mode
        (v as AppCompatImageView).setImageResource(
                when(mode) {
                    FlashMode.FLASH_AUTO -> R.drawable.uix_flash_auto_white_24dp
                    FlashMode.FLASH_OFF -> R.drawable.uix_flash_on_white_24dp
                    FlashMode.FLASH_ON -> R.drawable.uix_flash_off_white_24dp
                    else -> R.drawable.uix_flash_auto_white_24dp
                }
        )
    }

    override fun onResume() {
        super.onResume()
        if (!cv.isCameraOpened) {
            cv.openCamera(object : CameraOpenListener {
                override fun onCameraOpened(cameraFace: Int) {
                    ULog.d("onCameraOpened")
                }

                override fun onCameraOpenError(throwable: Throwable?) {
                    ULog.e("" + throwable)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        cv.closeCamera {
            ULog.d("closeCamera : $it")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PICK_FROM_ALBUM && resultCode == Activity.RESULT_OK) {
            try {
                val i = Intent().putExtras(data!!)
                if (singleMode) {
                    val path = Matisse.obtainPathResult(data)[0]
                    i.putStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS, arrayListOf(path)).putExtra(EXTRA_FROM_ALBUM, true)
                    setResult(Activity.RESULT_OK, i)
                } else {
                    val paths = arrayListOf<String>()
                    paths.addAll(Matisse.obtainPathResult(data))
                    i.putStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS, paths).putExtra(EXTRA_FROM_ALBUM, true)
                    setResult(Activity.RESULT_OK, i)
                }
                onBackPressed()
            } catch (e: Exception) {
                ULog.e(e); toast("$e")
            }
        }
    }

    override fun onBackPressed() {
        val doBack = {
            super.onBackPressed()
            ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
        }
        if (results.isEmpty()) {
            doBack()
        } else {
            BeautyDialog.Builder()
                    .setDialogStyle(DialogStyle.STYLE_WRAP)
                    .setDarkDialog(true)
                    .setDialogContent(SimpleContent.Builder()
                            .setContentColor(Color.WHITE)
                            .setContent(stringOf(R.string.uix_camera_back_tip))
                            .build())
                    .setDialogBottom(SimpleFooter.Builder()
                            .setLeftText(stringOf(R.string.uix_camera_back_cancel))
                            .setLeftTextStyle(TextStyleBean(Color.WHITE, 16f))
                            .setRightText(stringOf(R.string.uix_camera_back_confirm))
                            .setRightTextStyle(TextStyleBean(Color.WHITE, 16f))
                            .setOnClickListener(object : SimpleFooter.OnClickListener {
                                override fun onClick(dialog: BeautyDialog, buttonPos: Int, dialogTitle: IDialogTitle?, dialogContent: IDialogContent?) {
                                    if (buttonPos == BottomButtonPosition.BUTTON_POS_RIGHT) {
                                        doBack()
                                    }
                                    dialog.dismiss()
                                }
                            })
                            .build())
                    .build().show(supportFragmentManager, "back-options")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cv.releaseCamera()
    }

    companion object {
        private const val REQ_PICK_FROM_ALBUM   = 4567

        private const val EXTRA_MEDIA_TYPE      = "__extra_uix_media_type"
        private const val EXTRA_SHOW_SWITCH     = "__extra_uix_show_switch"
        private const val EXTRA_SHOW_FLASH      = "__extra_uix_show_flash"
        private const val EXTRA_SHOW_GRID       = "__extra_uix_show_grid"
        private const val EXTRA_SHOW_RAY        = "__extra_uix_show_scan_ray"
        private const val EXTRA_IS_SINGLE_MODE  = "__extra_uix_is_single_mode"
        private const val EXTRA_SHOW_ALBUM      = "__extra_uix_show_album"

        private const val EXTRA_FILE_PATH_OR_PATHS      = "__extra_uix_file_path"
        private const val EXTRA_FROM_ALBUM              = "__extra_uix_from_album"

        const val SETTING_CAMERA_VOICE_ENABLED  = "__uix_camera_voice_enabled"
        const val SETTING_CAMERA_FACE           = "__uix_camera_face"
        const val SETTING_CAMERA_SHOW_GRID      = "__uix_camera_show_grid"
        const val SETTING_CAMERA_SHOW_RAY       = "__uix_camera_show_ray"

        fun obtainPathResult(data: Intent): List<String> {
            return if (data.getBooleanExtra(EXTRA_FROM_ALBUM, false)) {
                Matisse.obtainPathResult(data)
            } else {
                data.getStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS).toList()
            }
        }

        fun obtainResult(data: Intent, converter: (path: String) -> Uri): List<Uri> {
            return if (data.getBooleanExtra(EXTRA_FROM_ALBUM, false)) {
                Matisse.obtainResult(data)
            } else {
                val paths = data.getStringArrayListExtra(EXTRA_FILE_PATH_OR_PATHS)
                val uris = arrayListOf<Uri>()
                uris.addAll(paths.map(converter))
                return uris
            }
        }

        fun isFromAlbum(data: Intent): Boolean {
            return data.getBooleanExtra(EXTRA_FROM_ALBUM, false)
        }

        @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA])
        fun launch(context: Activity, filePath: String, reqCode: Int) {
            val i = Intent(context, TakePhotoActivity::class.java)
            i.putExtra(EXTRA_FILE_PATH_OR_PATHS, filePath)
            context.startActivityForResult(i, reqCode)
        }

        @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA])
        fun open(): Builder {
            return Builder(ActivityUtils.open(TakePhotoActivity::class.java))
        }

        class Builder constructor(private val innerBuilder: ActivityUtils.Builder<TakePhotoActivity>) {

            /** Set store file path if single mode. Get capture file by this key
             * from intent of activity result. */
            fun setFilePath(filePath: String): Builder {
                innerBuilder.put(EXTRA_FILE_PATH_OR_PATHS, filePath)
                innerBuilder.put(EXTRA_IS_SINGLE_MODE, true)
                return this
            }

            /** Set captured images stored file directory. Get captured file paths
             * by this key from intent when got activity result. */
            fun setFileDir(fileDir: String): Builder {
                innerBuilder.put(EXTRA_FILE_PATH_OR_PATHS, fileDir)
                innerBuilder.put(EXTRA_IS_SINGLE_MODE, false)
                return this
            }

            fun showMediaType(@MediaType mediaType: Int): Builder {
                innerBuilder.put(EXTRA_MEDIA_TYPE, mediaType)
                return this
            }

            fun setShowSwitch(showSwitch: Boolean): Builder {
                innerBuilder.put(EXTRA_SHOW_SWITCH, showSwitch)
                return this
            }

            fun setShowFlash(showFlash: Boolean): Builder {
                innerBuilder.put(EXTRA_SHOW_FLASH, showFlash)
                return this
            }

            fun setShowGrid(showGrid: Boolean): Builder {
                innerBuilder.put(EXTRA_SHOW_GRID, showGrid)
                return this
            }

            fun setShowRay(showRay: Boolean): Builder {
                innerBuilder.put(EXTRA_SHOW_RAY, showRay)
                return this
            }

            fun setShowAlbum(showAlbum: Boolean): Builder {
                innerBuilder.put(EXTRA_SHOW_ALBUM, showAlbum)
                return this
            }

            fun build(): ActivityUtils.Builder<TakePhotoActivity> = innerBuilder
        }
    }
}
