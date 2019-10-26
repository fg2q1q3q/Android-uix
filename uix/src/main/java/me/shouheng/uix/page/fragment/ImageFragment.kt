package me.shouheng.uix.page.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.chrisbanes.photoview.PhotoView
import com.zhihu.matisse.internal.utils.PathUtils.getPath
import me.shouheng.uix.R
import me.shouheng.uix.page.GalleryActivity
import me.shouheng.uix.utils.UIXUtils

/**
 * The image fragment to display the image, video preview image etc.
 *
 * Created by WngShhng (shouheng2015@gmail.com) on 2017/4/9.
 */
class ImageFragment : Fragment() {

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.containsKey(ARG_URI)) {
            uri = arguments!!.getParcelable(ARG_URI) as Uri?
        }
        if (savedInstanceState != null) {
            uri = savedInstanceState.getParcelable(STATE_SAVE_KEY_ATTACHMENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (uri != null && UIXUtils.MIME_TYPE_VIDEO == UIXUtils.getMimeType(context!!, uri!!)) {
            val layout = RelativeLayout(context)
            val imageView = ImageView(context)
            imageView.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
            layout.addView(imageView)
            val videoTip = ImageView(context)
            videoTip.setImageResource(R.drawable.uix_play_circle_outline_white_24dp)
            val dp50 = UIXUtils.dp2px(50f)
            val params = RelativeLayout.LayoutParams(dp50, dp50)
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            videoTip.layoutParams = params
            layout.addView(videoTip)
            displayMedia(imageView)
            return layout
        }

        val photoView = PhotoView(context)
        if (uri != null && getPath(context!!, uri).endsWith("gif", true)) {
            Glide.with(activity!!).load(uri).into(photoView)
        } else {
            displayMedia(photoView)
        }
        photoView.setOnPhotoTapListener { _, _, _ ->
            val activity = activity
            if (activity is GalleryActivity) {
                activity.onBackPressed()
            }
        }
        photoView.maximumScale = 5.0f
        photoView.mediumScale = 3.0f
        return photoView
    }

    private fun displayMedia(photoView: PhotoView) {
        Glide.with(context!!)
                .load(UIXUtils.getThumbnailUri(context!!, uri!!))
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .into(photoView as ImageView)
        photoView.setOnClickListener {
            if (uri != null && UIXUtils.MIME_TYPE_VIDEO == UIXUtils.getMimeType(context!!, uri!!)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(uri!!, UIXUtils.getMimeType(context!!, uri!!))
                startActivity(intent)
            }
        }
    }

    /**
     * Note that you shouldn't set diskCacheStrategy of Glide, and you should use ImageView instead of PhotoView
     *
     * @param imageView view to show
     */
    private fun displayMedia(imageView: ImageView) {
        Glide.with(context!!)
                .load(UIXUtils.getThumbnailUri(context!!, uri!!))
                .transition(withCrossFade())
                .into(imageView)
        imageView.setOnClickListener {
            if (uri != null && UIXUtils.MIME_TYPE_VIDEO == UIXUtils.getMimeType(context!!, uri!!)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(uri!!, UIXUtils.getMimeType(context!!, uri!!))
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_SAVE_KEY_ATTACHMENT, uri)
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val ARG_URI = "__args_key_attachment"
        private const val STATE_SAVE_KEY_ATTACHMENT = "__state_save_key_attachment"
    }
}
