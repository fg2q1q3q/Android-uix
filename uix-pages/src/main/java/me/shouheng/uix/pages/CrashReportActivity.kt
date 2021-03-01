package me.shouheng.uix.pages

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.pages.CrashReportActivity.Companion.Builder
import me.shouheng.uix.pages.databinding.UixActivityCrashReportBinding
import me.shouheng.utils.ktx.onDebouncedClick
import kotlin.system.exitProcess

/**
 * Crash report page. To custom the theme, you can override theme in your
 * AndroidManifest.xml like below:
 *
 * ```xml
 *  <activity android:name="me.shouheng.uix.pages.CrashReportActivity"
 *       android:theme="@style/CrashReportTheme"
 *       tools:replace="android:theme"/>
 * ```
 *
 * or custom theme by calling [Builder.setThemeStyle] method of launcher builder.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-12-22 11:46
 */
class CrashReportActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_KEY_CRASH_IMAGE         = "__extra_crash_image"
        private const val EXTRA_KEY_TITLE               = "__extra_crash_title"
        private const val EXTRA_KEY_TITLE_STYLE         = "__extra_crash_title_style"
        private const val EXTRA_KEY_CONTENT             = "__extra_crash_content"
        private const val EXTRA_KEY_CONTENT_STYLE       = "__extra_crash_content_style"
        private const val EXTRA_KEY_BUTTON_STYLE        = "__extra_crash_button_style"
        private const val EXTRA_KEY_MESSAGE             = "__extra_crash_message"
        private const val EXTRA_KEY_RESTART_ACTIVITY    = "__extra_restart_activity"
        private const val EXTRA_KEY_THEME_STYLE         = "__extra_theme_style"

        class Builder(private val ctx: Context) {

            private val i = Intent(ctx, CrashReportActivity::class.java)

            fun setImage(@DrawableRes crashImage: Int): Builder {
                i.putExtra(EXTRA_KEY_CRASH_IMAGE, crashImage)
                return this
            }

            fun setTitle(title: CharSequence): Builder {
                i.putExtra(EXTRA_KEY_TITLE, title)
                return this
            }

            fun setTitleStyle(titleStyle: TextStyleBean): Builder {
                i.putExtra(EXTRA_KEY_TITLE_STYLE, titleStyle)
                return this
            }

            fun setContent(content: CharSequence): Builder {
                i.putExtra(EXTRA_KEY_CONTENT, content)
                return this
            }

            fun setContentStyle(contentStyle: TextStyleBean): Builder {
                i.putExtra(EXTRA_KEY_CONTENT_STYLE, contentStyle)
                return this
            }

            fun setButtonStyle(buttonStyle: TextStyleBean): Builder {
                i.putExtra(EXTRA_KEY_BUTTON_STYLE, buttonStyle)
                return this
            }

            fun setMessage(message: CharSequence): Builder {
                i.putExtra(EXTRA_KEY_MESSAGE, message)
                return this
            }

            fun setRestartActivity(cls: Class<out Activity>): Builder {
                i.putExtra(EXTRA_KEY_RESTART_ACTIVITY, cls)
                return this
            }

            /**
             * custom the crash page style in code
             */
            fun setThemeStyle(@StyleRes resId: Int): Builder {
                i.putExtra(EXTRA_KEY_THEME_STYLE, resId)
                return this
            }

            fun launch() {
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                ctx.startActivity(i)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getCharSequenceExtra(EXTRA_KEY_TITLE)
        val titleStyle = intent.getSerializableExtra(EXTRA_KEY_TITLE_STYLE) as? TextStyleBean
        val content = intent.getCharSequenceExtra(EXTRA_KEY_CONTENT)
        val contentStyle = intent.getSerializableExtra(EXTRA_KEY_CONTENT_STYLE) as? TextStyleBean
        val buttonStyle = intent.getSerializableExtra(EXTRA_KEY_BUTTON_STYLE) as? TextStyleBean
        val message = intent.getCharSequenceExtra(EXTRA_KEY_MESSAGE)
        val image = intent.getIntExtra(EXTRA_KEY_CRASH_IMAGE, R.drawable.uix_crash_error_image)
        val cls = intent.getSerializableExtra(EXTRA_KEY_RESTART_ACTIVITY) as? Class<Activity>
        val resId = intent.getIntExtra(EXTRA_KEY_THEME_STYLE, 0)

        if (resId != 0) setTheme(resId)

        val binding = UixActivityCrashReportBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.tvTitle.text = title
        binding.tvContent.text = content
        binding.iv.setImageResource(image)

        titleStyle?.let { binding.tvTitle.setStyle(titleStyle) }
        contentStyle?.let { binding.tvContent.setStyle(it) }
        buttonStyle?.let { binding.btnRestart.setStyle(it); binding.btnLog.setStyle(it) }

        binding.btnLog.onDebouncedClick {
            val root = LayoutInflater.from(this).inflate(R.layout.uix_layout_crash_report_message, null, false)
            root.findViewById<TextView>(R.id.tv_report_message).text = message
            AlertDialog.Builder(this@CrashReportActivity)
                    .setTitle(R.string.uix_crash_error_details)
                    .setView(root)
                    .setPositiveButton(R.string.uix_crash_close, null)
                    .setNeutralButton(R.string.uix_crash_copy_log) { _, _ -> copyErrorToClipboard(message.toString()) }
                    .show()
        }

        binding.btnRestart.onDebouncedClick {
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
        ContextCompat.getSystemService(this, ClipboardManager::class.java)?.primaryClip =
                ClipData.newPlainText(getString(R.string.uix_crash_error_info), msg)
    }

    private fun killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(10)
    }
}