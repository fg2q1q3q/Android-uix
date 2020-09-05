package me.shouheng.uix.pages.image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.chrisbanes.photoview.PhotoView
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.pages.R
import me.shouheng.uix.pages.utils.UPage

/**
 * The image fragment to display the image, video preview image etc.
 *
 * @author Created by WngShhng (shouheng2015@gmail.com)
 * @since 2017/4/9.
 */
class ImageFragment : Fragment() {

    private var uri: Uri? = null

    private val dp50 = UView.dp2px(50f)

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        if (arguments?.containsKey(ARG_IMAGE_URI) == true)
            uri = arguments!!.getParcelable(ARG_IMAGE_URI)
        if (state != null)
            uri = state.getParcelable(STATE_KEY_IMAGE_URI)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ULog.d("the display uri : $uri")
        return if (UPage.isVideo(context!!, uri!!)) displayVideo() else displayImage()
    }

    private fun displayVideo(): View {
        val root = RelativeLayout(context)
        val imageView = ImageView(context)
        val params0 = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        imageView.layoutParams = params0
        root.addView(imageView)
        // add video tip
        val videoTip = ImageView(context)
        videoTip.setImageResource(R.drawable.uix_play_circle_outline_white_24dp)
        val params = RelativeLayout.LayoutParams(dp50, dp50)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        videoTip.layoutParams = params
        root.addView(videoTip)
        // display video
        val thumbnail = UPage.getThumbnailUri(context!!, uri!!)
        Glide.with(context!!).load(thumbnail).transition(withCrossFade()).into(imageView)
        // video click event
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(uri!!, "video/mp4")
            startActivity(intent)
        }
        return root
    }

    private fun displayImage(): View {
        val photoView = PhotoView(context)
        if (UPage.isGif(context!!, uri!!)) {
            Glide.with(activity!!).load(uri).transition(withCrossFade()).into(photoView)
        } else {
            val thumbnail = UPage.getThumbnailUri(context!!, uri!!)
            Glide.with(context!!)
                    .load(thumbnail)
                    .thumbnail(0.5f)
                    .transition(withCrossFade())
                    .into(photoView as ImageView)
        }
        photoView.setOnPhotoTapListener { _, _, _ -> activity?.onBackPressed() }
        photoView.maximumScale = 5.0f
        photoView.mediumScale = 3.0f
        return photoView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_KEY_IMAGE_URI, uri)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val STATE_KEY_IMAGE_URI       = "__state_key_image_uri"
        const val ARG_IMAGE_URI                     = "__arg_key_image_uri"
        fun getInstance(uri: Uri): ImageFragment {
            val fragment = ImageFragment()
            fragment.arguments = Bundle().apply { putParcelable(ARG_IMAGE_URI, uri as Parcelable) }
            return fragment
        }
    }
}
