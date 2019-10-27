package me.shouheng.uix.page

import android.content.ActivityNotFoundException
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
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.bumptech.glide.Glide
import me.shouheng.uix.R
import me.shouheng.uix.page.adapter.UriPagerAdapter
import me.shouheng.uix.pager.HackyViewPager
import me.shouheng.uix.pager.trans.DepthPageTransformer
import me.shouheng.uix.utils.UIXUtils
import ooo.oxo.library.widget.PullBackLayout
import java.util.*


class GalleryActivity : AppCompatActivity(), PullBackLayout.Callback {

    private var mBackground: ColorDrawable? = null
    private var mViewPager: HackyViewPager? = null
    private var toolbar: Toolbar? = null

    private var uris: ArrayList<Uri>? = null
    private var currentIndex: Int = 0
    private var title: String? = ""
    private var fullScreenMode: Boolean = false

    companion object {
        const val EXTRA_GALLERY_IMAGES          = "__extra_gallery_images"
        const val EXTRA_GALLERY_TITLE           = "__extra_gallery_title"
        const val EXTRA_GALLERY_CLICKED_IMAGE   = "__extra_gallery_current_image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uix_activity_gallery)
        handleIntent(savedInstanceState)
        configToolbar()
        configViews()
    }

    private fun handleIntent(savedInstanceState: Bundle?) {
        uris = ArrayList()
        currentIndex = 0
        if (savedInstanceState == null) {
            uris = intent.getParcelableArrayListExtra(EXTRA_GALLERY_IMAGES)
            title = intent.getStringExtra(EXTRA_GALLERY_TITLE)
            currentIndex = intent.getIntExtra(EXTRA_GALLERY_CLICKED_IMAGE, 0)
        } else {
            uris = savedInstanceState.getParcelableArrayList(EXTRA_GALLERY_IMAGES)
            title = savedInstanceState.getString(EXTRA_GALLERY_TITLE)
            currentIndex = savedInstanceState.getInt(EXTRA_GALLERY_CLICKED_IMAGE, 0)
        }
        UIXUtils.d(uris!!)
        UIXUtils.d(currentIndex)
    }

    private fun configToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setBackgroundDrawable(resources.getDrawable(R.drawable.uix_bg_toolbar_shade))
            actionBar.title = title
            actionBar.subtitle = (currentIndex + 1).toString() + "/" + uris!!.size
        }
    }

    private fun configViews() {
        val pullBackLayout = findViewById<PullBackLayout>(R.id.pull_back)
        pullBackLayout.setCallback(this)
        setupSystemUI()
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                showSystemUI()
            } else {
                hideSystemUI()
            }
        }

        mViewPager = findViewById(R.id.view_pager)
        val adapter = UriPagerAdapter(supportFragmentManager, uris!!)
        mViewPager!!.adapter = adapter
        mViewPager!!.currentItem = currentIndex
        mViewPager!!.setPageTransformer(true, DepthPageTransformer())
        mViewPager!!.offscreenPageLimit = 3
        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                toolbar!!.subtitle = (position + 1).toString() + "/" + uris!!.size
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
        UIXUtils.getRootView(this).setBackgroundDrawable(mBackground)
    }

    fun toggleSystemUI() {
        if (fullScreenMode) {
            showSystemUI()
        } else {
            hideSystemUI()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(applicationContext).clearMemory()
        Glide.get(applicationContext).trimMemory(ComponentCallbacks2.TRIM_MEMORY_COMPLETE)
        System.gc()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.uix_gallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_share -> {
                val uri = uris!![mViewPager!!.currentItem]
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = UIXUtils.getMimeType(this, uri)
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(intent)
            }
            R.id.action_open -> {
                try {
                    val uri = uris!![mViewPager!!.currentItem]
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.setDataAndType(uri, UIXUtils.getMimeType(this, uri))
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this,"failed", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.action_download -> { /* temp do nothing */ }
        }
        return super.onOptionsItemSelected(item)
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
            toolbar!!.animate().translationY((-toolbar!!.height).toFloat()).setInterpolator(
                    AccelerateInterpolator()).setDuration(200).start()
            window.decorView.systemUiVisibility = UIXUtils.getSystemVisibility()
            fullScreenMode = true
        }
    }

    private fun setupSystemUI() {
        toolbar!!.animate().translationY(UIXUtils.getStatusBarHeight().toFloat()).setInterpolator(
                DecelerateInterpolator()).setDuration(0).start()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun showSystemUI() {
        runOnUiThread {
            toolbar!!.animate().translationY(UIXUtils.getStatusBarHeight().toFloat()).setInterpolator(
                    DecelerateInterpolator()).setDuration(240).start()
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            fullScreenMode = false
        }
    }

    private fun hideOrShowStatusBar() {
        if (fullScreenMode) {
            UIXUtils.enter(this)
        } else {
            UIXUtils.exit(this)
        }
        fullScreenMode = !fullScreenMode
    }
}
