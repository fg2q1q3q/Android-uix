package me.shouheng.uix.pages.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.annotation.RequiresPermission
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.view.View
import me.shouheng.icamera.CameraView
import me.shouheng.icamera.enums.CameraFace
import me.shouheng.icamera.enums.FlashMode
import me.shouheng.icamera.enums.MediaType
import me.shouheng.icamera.listener.CameraOpenListener
import me.shouheng.icamera.listener.CameraPhotoListener
import me.shouheng.icamera.listener.CameraVideoListener
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.pages.R
import me.shouheng.uix.pages.utils.UPage
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.content.SimpleList
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class TakePhotoActivity : AppCompatActivity() {

    private lateinit var cv: CameraView
    private lateinit var sp: SharedPreferences
    private lateinit var hLines: View
    private lateinit var vLines: View
    private lateinit var flRay: View

    private var filePath: String? = null

    private var recording = AtomicBoolean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uix_activity_take_photo)
        cv = findViewById(R.id.cv)
        filePath = intent.getStringExtra(EXTRA_FILE_PATH)
        val ivFlash = findViewById<AppCompatImageView>(R.id.iv_flash)
        ivFlash.setImageResource(
                when(cv.flashMode) {
                    FlashMode.FLASH_AUTO -> R.drawable.uix_flash_auto_white_24dp
                    FlashMode.FLASH_OFF -> R.drawable.uix_flash_on_white_24dp
                    FlashMode.FLASH_ON -> R.drawable.uix_flash_off_white_24dp
                    else -> R.drawable.uix_flash_auto_white_24dp
                }
        )
        flRay = findViewById<View>(R.id.fl_ray)
        val ivRay = findViewById<AppCompatImageView>(R.id.iv_ray)
        UPage.scaleUpDown(ivRay, 2000)
        hLines = findViewById<View>(R.id.fl_h_line)
        vLines = findViewById<View>(R.id.fl_v_line)

        // get configurations from shared preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        cv.isVoiceEnable = sp.getBoolean(SETTING_CAMERA_VOICE_ENABLED, true)
        cv.switchCamera(sp.getInt(SETTING_CAMERA_FACE, CameraFace.FACE_REAR))
        hLines.visibility = if (sp.getBoolean(SETTING_CAMERA_SHOW_GRID, true)) View.VISIBLE else View.GONE
        vLines.visibility = if (sp.getBoolean(SETTING_CAMERA_SHOW_GRID, true)) View.VISIBLE else View.GONE
        flRay.visibility = if (sp.getBoolean(SETTING_CAMERA_SHOW_RAY, true)) View.VISIBLE else View.GONE

        val ivSwitch = findViewById<AppCompatImageView>(R.id.iv_switch)
        ivFlash.visibility = if (showFlash) View.VISIBLE else View.GONE
        ivSwitch.visibility = if (showSwitch) View.VISIBLE else View.GONE
        cv.mediaType = mediaType
    }

    fun onShot(v: View) {
        if (filePath == null) return
        if (cv.mediaType == MediaType.TYPE_PICTURE) {
            cv.takePicture(File(filePath!!), object : CameraPhotoListener {
                override fun onCaptureFailed(throwable: Throwable?) {
                    ULog.e("" + throwable)
                }

                override fun onPictureTaken(data: ByteArray?, picture: File) {
                    setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_FILE_PATH, filePath))
                    finish()
                }
            })
        } else {
            cv.startVideoRecord(File(filePath!!), object : CameraVideoListener {
                override fun onVideoRecordStart() {
                    recording.set(true)
                }

                override fun onVideoRecordStop(file: File?) {
                    recording.set(false)
                    setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_FILE_PATH, filePath))
                    finish()
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

    fun onClose(v: View) {
        finish()
    }

    fun onSetting(v: View) {
        val list = listOf(
                SimpleList.Item(0,
                        getString(R.string.uix_camera_voice_enable),
                        ContextCompat.getDrawable(this, if (cv.isVoiceEnable) {
                            R.drawable.uix_volume_up_white_24dp
                        } else {
                            R.drawable.uix_volume_off_white_24dp
                        })),
                SimpleList.Item(1,
                        getString(R.string.uix_camera_show_ray),
                        ContextCompat.getDrawable(this, R.drawable.uix_gradient_24)),
                SimpleList.Item(2,
                        getString(R.string.uix_camera_show_grid),
                        ContextCompat.getDrawable(this, R.drawable.uix_grid_on_24))
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
                                    sp.edit().putBoolean(SETTING_CAMERA_VOICE_ENABLED, cv.isVoiceEnable).apply()
                                } else if (item.id == 1) {
                                    if (flRay.visibility == View.VISIBLE) {
                                        flRay.visibility = View.GONE
                                        sp.edit().putBoolean(SETTING_CAMERA_SHOW_RAY, false).apply()
                                    } else {
                                        flRay.visibility = View.VISIBLE
                                        sp.edit().putBoolean(SETTING_CAMERA_SHOW_RAY, true).apply()
                                    }
                                } else if (item.id == 2) {
                                    if (vLines.visibility == View.VISIBLE) {
                                        vLines.visibility = View.GONE
                                        hLines.visibility = View.GONE
                                        sp.edit().putBoolean(SETTING_CAMERA_SHOW_GRID, false).apply()
                                    } else {
                                        vLines.visibility = View.VISIBLE
                                        hLines.visibility = View.VISIBLE
                                        sp.edit().putBoolean(SETTING_CAMERA_SHOW_GRID, true).apply()
                                    }
                                }
                                dialog.dismiss()
                            }
                        })
                        .build())
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

    override fun onDestroy() {
        super.onDestroy()
        cv.releaseCamera()
    }

    companion object {
        const val EXTRA_FILE_PATH = "__extra_file_path"

        const val SETTING_CAMERA_VOICE_ENABLED  = "__uix_camera_voice_enabled"
        const val SETTING_CAMERA_FACE           = "__uix_camera_face"
        const val SETTING_CAMERA_SHOW_GRID      = "__uix_camera_show_grid"
        const val SETTING_CAMERA_SHOW_RAY       = "__uix_camera_show_ray"

        var showSwitch = true
        var showFlash = true
        @MediaType var mediaType: Int = MediaType.TYPE_PICTURE

        @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA])
        fun launch(context: Activity, filePath: String, reqCode: Int) {
            val i = Intent(context, TakePhotoActivity::class.java)
            i.putExtra(EXTRA_FILE_PATH, filePath)
            context.startActivityForResult(i, reqCode)
        }
    }
}
