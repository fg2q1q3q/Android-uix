package me.shouheng.uix.pages.rate

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.pages.R
import me.shouheng.uix.widget.dialog.content.IDialogContent

/**
 * Rating dialog content
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-07-25 10:53
 */
class RatingContent : IDialogContent {

    private var rb: RatingBar? = null
    private var iv: ImageView? = null
    private var rating: Int = 0

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_rating, null)
        iv = layout.findViewById(R.id.iv_facial)
        rb = layout.findViewById(R.id.rb)
        rb!!.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                ULog.d(rating)
                this.rating = rating.toInt()
                when(this.rating) {
                    0->{ iv!!.setImageResource(R.drawable.uix_ic_facial_crying) }
                    1->{ iv!!.setImageResource(R.drawable.uix_ic_facial_crying) }
                    2->{ iv!!.setImageResource(R.drawable.uix_ic_facial_sad) }
                    3->{ iv!!.setImageResource(R.drawable.uix_ic_facial_unhappy) }
                    4->{ iv!!.setImageResource(R.drawable.uix_ic_facial_happy) }
                    5->{ iv!!.setImageResource(R.drawable.uix_ic_facial_very_happy) }
                }
            }
        return layout
    }

    fun getRating(): Int = rating
}