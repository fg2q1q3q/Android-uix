package me.shouheng.uix.page.adapter

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import me.shouheng.uix.page.fragment.ImageFragment

/**
 * 图片 VP 适配器
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 11:21
 */
class UriPagerAdapter(fragmentManager: FragmentManager, private val uris: List<Uri>)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val registeredFragments = SparseArray<Fragment>()

    override fun getItem(pos: Int): Fragment {
        val uri = this.uris[pos]
        val fragment = ImageFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(ImageFragment.ARG_URI, uri as Parcelable)
        }
        return fragment
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, o)
    }

    override fun getItemPosition(o: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return uris.size
    }
}