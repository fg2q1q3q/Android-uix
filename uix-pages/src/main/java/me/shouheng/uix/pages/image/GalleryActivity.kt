package me.shouheng.uix.pages.image

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Surface
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.R
import me.shouheng.uix.widget.layout.PullBackLayout
import me.shouheng.uix.widget.pager.HackyViewPager
import me.shouheng.uix.widget.pager.trans.DepthPageTransformer

class GalleryActivity : AppCompatActivity(), PullBackLayout.Callback {

    private var mBackground: ColorDrawable? = null
    private lateinit var mViewPager: HackyViewPager
    private var toolbar: Toolbar? = null

    private var uris: ArrayList<Uri>? = ArrayList()
    private var currentIndex: Int = 0
    private var title: String? = ""
    private var fullScreenMode: Boolean = false

    companion object {
        const val EXTRA_GALLERY_TITLE           = "__extra_gallery_title"
        const val EXTRA_GALLERY_IMAGES          = "__extra_gallery_images"
        const val EXTRA_GALLERY_CLICKED_IMAGE   = "__extra_gallery_current_image"

        fun launch(context: Context, title: String, uris: ArrayList<Uri>, currentIndex: Int) {
            val intent = Intent(context, GalleryActivity::class.java)
            intent.putExtra(EXTRA_GALLERY_TITLE, title)
            intent.putExtra(EXTRA_GALLERY_IMAGES, uris)
            intent.putExtra(EXTRA_GALLERY_CLICKED_IMAGE, currentIndex)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uix_activity_gallery)
        handleIntent(savedInstanceState)
        configViews()
    }

    private fun handleIntent(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            uris = intent.getParcelableArrayListExtra(EXTRA_GALLERY_IMAGES)
            title = intent.getStringExtra(EXTRA_GALLERY_TITLE)
            currentIndex = intent.getIntExtra(EXTRA_GALLERY_CLICKED_IMAGE, 0)
        } else {
            uris = savedInstanceState.getParcelableArrayList(EXTRA_GALLERY_IMAGES)
            title = savedInstanceState.getString(EXTRA_GALLERY_TITLE)
            currentIndex = savedInstanceState.getInt(EXTRA_GALLERY_CLICKED_IMAGE, 0)
        }
        ULog.d(uris!!)
        ULog.d(currentIndex)
    }

    private fun configViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title
            actionBar.subtitle = "${currentIndex + 1}/${uris!!.size}"
        }

        val pullBackLayout = findViewById<PullBackLayout>(R.id.pull_back)
        pullBackLayout.setCallback(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                showSystemUI()
            } else {
                hideSystemUI()
            }
        }

        mViewPager = findViewById(R.id.view_pager)
        val adapter = UriPagerAdapter(supportFragmentManager, uris!!)
        mViewPager.adapter = adapter
        mViewPager.currentItem = currentIndex
        mViewPager.setPageTransformer(true, DepthPageTransformer())
        mViewPager.offscreenPageLimit = 3
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                toolbar!!.subtitle = "${position + 1}/${uris!!.size}"
                invalidateOptionsMenu()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        val aa = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        if (aa.rotation == Surface.ROTATION_90) {
            val configuration = Configuration()
            configuration.orientation = Configuration.ORIENTATION_LANDSCAPE
            onConfigurationChanged(configuration)
        }

        mBackground = ColorDrawable(Color.BLACK)
        UView.getRootView(this).setBackgroundDrawable(mBackground)
        hideSystemUI()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(applicationContext).clearMemory()
        Glide.get(applicationContext).trimMemory(ComponentCallbacks2.TRIM_MEMORY_COMPLETE)
        System.gc()
    }

    override fun onPullStart() {
        fullScreenMode = true
        hideOrShowStatusBar()
    }

    override fun onPull(v: Float) {
        val a = 1f.coerceAtMost(v * 3f)
        val alpha = (0xff * (1f - a)).toInt()
        mBackground!!.alpha = alpha
    }

    override fun onPullCancel() {}

    override fun onPullComplete() {
        supportFinishAfterTransition()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_GALLERY_IMAGES, uris)
        outState.putString(EXTRA_GALLERY_TITLE, title)
        outState.putInt(EXTRA_GALLERY_CLICKED_IMAGE, currentIndex)
    }

    private fun hideSystemUI() {
        runOnUiThread {
            window.decorView.systemUiVisibility = UView.getSystemVisibility()
            fullScreenMode = true
        }
    }

    private fun showSystemUI() {
        runOnUiThread {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            fullScreenMode = false
        }
    }

    private fun hideOrShowStatusBar() {
        if (fullScreenMode) {
            UView.enter(this)
        } else {
            UView.exit(this)
        }
        fullScreenMode = !fullScreenMode
    }
}
