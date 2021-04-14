package me.shouheng.uix.pages.image

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rd.PageIndicatorView
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.R
import me.shouheng.uix.widget.layout.PullBackLayout
import me.shouheng.uix.widget.pager.HackyViewPager
import me.shouheng.uix.widget.pager.trans.DepthPageTransformer

class GalleryActivity : AppCompatActivity(), PullBackLayout.Callback {

    private var mBackground: ColorDrawable? = null
    private lateinit var mViewPager: HackyViewPager
    private var uris: ArrayList<Uri>? = ArrayList()
    private var currentIndex: Int = 0

    companion object {
        const val EXTRA_GALLERY_IMAGES          = "__extra_gallery_images"
        const val EXTRA_GALLERY_CLICKED_IMAGE   = "__extra_gallery_current_image"

        fun launch(context: Context, uris: ArrayList<Uri>, currentIndex: Int) {
            val intent = Intent(context, GalleryActivity::class.java)
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
            currentIndex = intent.getIntExtra(EXTRA_GALLERY_CLICKED_IMAGE, 0)
        } else {
            uris = savedInstanceState.getParcelableArrayList(EXTRA_GALLERY_IMAGES)
            currentIndex = savedInstanceState.getInt(EXTRA_GALLERY_CLICKED_IMAGE, 0)
        }
    }

    private fun configViews() {
        val pullBackLayout = findViewById<PullBackLayout>(R.id.pull_back)
        pullBackLayout.setCallback(this)

        mViewPager = findViewById(R.id.view_pager)
        val adapter = UriPagerAdapter(supportFragmentManager, uris!!)
        mViewPager.adapter = adapter
        mViewPager.currentItem = currentIndex
        mViewPager.setPageTransformer(true, DepthPageTransformer())
        mViewPager.offscreenPageLimit = 3

        val piv = findViewById<PageIndicatorView>(R.id.piv)
        piv.setViewPager(mViewPager)

        mBackground = ColorDrawable(Color.BLACK)
        UView.getRootView(this).setBackgroundDrawable(mBackground)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(applicationContext).clearMemory()
        Glide.get(applicationContext).trimMemory(ComponentCallbacks2.TRIM_MEMORY_COMPLETE)
        System.gc()
    }

    override fun onPullStart() {}

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
        outState.putInt(EXTRA_GALLERY_CLICKED_IMAGE, currentIndex)
    }
}
