package me.shouheng.uix.page

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import me.shouheng.uix.R
import kotlin.system.exitProcess

/**
 * Crash page
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-12-22 11:46
 */
class CrashActivity : AppCompatActivity() {

    private lateinit var ivCrashImage: ImageView
    private lateinit var tvCrashTips: TextView
    private lateinit var btnCrashRestart: Button
    private lateinit var btnCrashLog: Button

    companion object {
        /**
         * Crash page image resources id
         */
        const val EXTRA_KEY_CRASH_IMAGE       = "__extra_crash_image"
        /**
         * Crash page title
         */
        const val EXTRA_KEY_CRASH_TIPS        = "__extra_crash_tips"
        /**
         * Crash information
         */
        const val EXTRA_KEY_CRASH_INFO        = "__extra_crash_info"
        /**
         * The activity to restart when click restart button
         */
        const val EXTRA_KEY_RESTART_ACTIVITY  = "__extra_restart_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uix_activity_crash)

        ivCrashImage = findViewById(R.id.iv_crash_image)
        tvCrashTips = findViewById(R.id.tv_crash_tips)
        btnCrashRestart = findViewById(R.id.btn_crash_restart)
        btnCrashLog = findViewById(R.id.btn_crash_log)

        val tips = intent.getStringExtra(EXTRA_KEY_CRASH_TIPS)
        tvCrashTips.text = tips
        val image = intent.getIntExtra(EXTRA_KEY_CRASH_IMAGE, R.drawable.uix_crash_error_image)
        ivCrashImage.setImageResource(image)
        val msg = intent.getStringExtra(EXTRA_KEY_CRASH_INFO)?:""
        val cls = intent.getSerializableExtra(EXTRA_KEY_RESTART_ACTIVITY) as? Class<Activity>
        btnCrashLog.setOnClickListener {
            val dialog = AlertDialog.Builder(this@CrashActivity)
                    .setTitle(R.string.uix_crash_error_details)
                    .setMessage(msg)
                    .setPositiveButton(R.string.uix_crash_close, null)
                    .setNeutralButton(R.string.uix_crash_copy_log) { _, _ -> copyErrorToClipboard(msg) }
                    .show()
            val textView = dialog.findViewById<TextView>(android.R.id.message)
            textView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        }
        btnCrashRestart.setOnClickListener {
            if (cls != null) {
                val i = Intent(this, cls)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                if (i.component != null) {
                    i.action = Intent.ACTION_MAIN
                    i.addCategory(Intent.CATEGORY_LAUNCHER)
                }
                finish()
                startActivity(i)
                killCurrentProcess()
            }
        }
    }

    private fun copyErrorToClipboard(msg: String) {
        ContextCompat.getSystemService(this, ClipboardManager::class.java)
                ?.setPrimaryClip(ClipData.newPlainText(getString(R.string.uix_crash_error_info), msg))
    }

    private fun killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(10)
    }
}