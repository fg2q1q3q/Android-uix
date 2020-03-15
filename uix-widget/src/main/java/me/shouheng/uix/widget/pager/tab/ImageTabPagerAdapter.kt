package me.shouheng.uix.widget.pager.tab

import android.support.v4.view.PagerAdapter
import android.text.Spanned
import android.view.View
import android.view.ViewGroup

/**
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-01 00:08
 */
class ImageTabPagerAdapter : PagerAdapter() {

    override fun getCount(): Int {
        return 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return false
    }

    fun getPageImage(position: Int): Spanned? {
        return null
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
