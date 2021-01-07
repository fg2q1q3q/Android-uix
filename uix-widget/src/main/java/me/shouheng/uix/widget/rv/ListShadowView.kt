package me.shouheng.uix.widget.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.AbsListView
import android.widget.ListView
import me.shouheng.uix.widget.R
import me.shouheng.utils.ktx.dp2px
import me.shouheng.utils.ktx.drawableOf
import kotlin.math.abs

class ListShadowView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    init {
        background = drawableOf(R.drawable.uix_shadow_below)
        alpha = 0f
    }

    private val dp5 = 5f.dp2px()
    private var offset = 0

    fun reset() { offset=0 }

    fun bind(rv: RecyclerView) {
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                offset += dy
                // fix 2020-11-20 the offset might be smaller than 0 if the layout changed after
                // a layout calculation, so we add this option to solve this problem
                if (offset < 0) offset = 0
                if (offset >= 0) {
                    alpha = (abs(offset) / dp5).coerceAtMost(1)
                        .toFloat().coerceAtLeast(0f)
                }
            }
        })
    }

    fun bind(lv: ListView) {
        lv.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (totalItemCount == 0) return
                alpha = if (firstVisibleItem > 0) 1f
                else {
                    val firstChild = view!!.getChildAt(0)
                    (abs(firstChild.top) / dp5).coerceAtMost(1)
                        .toFloat().coerceAtLeast(0f)
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) { /*noop*/ }
        })
    }

    fun update(offset: Int) {
        alpha = (abs(offset) / dp5).coerceAtMost(1)
            .toFloat().coerceAtLeast(0f)
    }
}
