package me.shouheng.uix.pages.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresPermission
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
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.pages.R
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class TakePhotoActivity : AppCompatActivity() {

    private lateinit var cv: CameraView

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
        val ivVoice = findViewById<AppCompatImageView>(R.id.iv_voice)
        ivVoice.setImageResource(if (cv.isVoiceEnable)
            R.drawable.uix_volume_up_white_24dp else R.drawable.uix_volume_off_white_24dp)
        val ivSwitch = findViewById<AppCompatImageView>(R.id.iv_switch)
        ivFlash.visibility = if (showFlash) View.VISIBLE else View.GONE
        ivVoice.visibility = if (showVoice) View.VISIBLE else View.GONE
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

    fun onVoice(v: View) {
        cv.isVoiceEnable = !cv.isVoiceEnable
        (v as AppCompatImageView).setImageResource(
                if (cv.isVoiceEnable) {
                    R.drawable.uix_volume_up_white_24dp
                } else {
                    R.drawable.uix_volume_off_white_24dp
                }
        )
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

        var showSwitch = true
        var showFlash = true
        var showVoice = true
        @MediaType var mediaType: Int = MediaType.TYPE_PICTURE

        @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA])
        fun launch(context: Activity, filePath: String, reqCode: Int) {
            val i = Intent(context, TakePhotoActivity::class.java)
            i.putExtra(EXTRA_FILE_PATH, filePath)
            context.startActivityForResult(i, reqCode)
        }
    }
}
