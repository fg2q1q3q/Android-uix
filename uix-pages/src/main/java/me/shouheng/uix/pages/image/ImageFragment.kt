package me.shouheng.uix.pages.image

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
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.common.utils.URes
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.R
import me.shouheng.uix.pages.utils.UIXPageUtils

/**
 * The image fragment to display the image, video preview image etc.
 *
 * @author Created by WngShhng (shouheng2015@gmail.com)
 * @since 2017/4/9.
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
        val mimeType = URes.getMimeType(context!!, uri!!)
        if (uri != null && mimeType != null && mimeType.contains("video", true)) {
            val layout = RelativeLayout(context)
            val imageView = ImageView(context)
            imageView.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT)
            layout.addView(imageView)
            val videoTip = ImageView(context)
            videoTip.setImageResource(R.drawable.uix_play_circle_outline_white_24dp)
            val dp50 = UView.dp2px(50f)
            videoTip.layoutParams = RelativeLayout.LayoutParams(dp50, dp50).apply {
                addRule(RelativeLayout.CENTER_IN_PARENT)
            }
            layout.addView(videoTip)
            displayMedia(imageView)
            return layout
        }

        val photoView = PhotoView(context)
        val path = getRealPath()
        if (uri != null && path?.endsWith("gif", true) == true) {
            Glide.with(activity!!).load(uri).into(photoView)
        } else {
            displayMedia(photoView)
        }
        photoView.setOnPhotoTapListener { _, _, _ -> (activity as? GalleryActivity)?.onBackPressed() }
        photoView.maximumScale = 5.0f
        photoView.mediumScale = 3.0f
        return photoView
    }

    private fun displayMedia(photoView: PhotoView) {
        Glide.with(context!!)
                .load(UIXPageUtils.getThumbnailUri(context!!, uri!!))
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .into(photoView as ImageView)
        photoView.setOnClickListener {
            if (uri != null && MIME_TYPE_VIDEO == URes.getMimeType(context!!, uri!!)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(uri!!, URes.getMimeType(context!!, uri!!))
                startActivity(intent)
            }
        }
    }

    /**
     * Note that you shouldn't set diskCacheStrategy of Glide,
     * and you should use ImageView instead of PhotoView
     *
     * @param imageView view to show
     */
    private fun displayMedia(imageView: ImageView) {
        Glide.with(context!!)
                .load(UIXPageUtils.getThumbnailUri(context!!, uri!!))
                .transition(withCrossFade())
                .into(imageView)
        imageView.setOnClickListener {
            if (uri != null && MIME_TYPE_VIDEO == URes.getMimeType(context!!, uri!!)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(uri!!, URes.getMimeType(context!!, uri!!))
                startActivity(intent)
            }
        }
    }

    private fun getRealPath(): String? {
        var path: String? = null
        try {
            path = getPath(context!!, uri)
        } catch (ex: Exception) {
            ULog.e(ex)
        }
        return path
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_SAVE_KEY_ATTACHMENT, uri)
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val ARG_URI = "__args_key_attachment"
        private const val MIME_TYPE_VIDEO = "video/mp4"
        private const val STATE_SAVE_KEY_ATTACHMENT = "__state_save_key_attachment"
    }
}
