package me.shouheng.suix.container

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import me.shouheng.mvvm.R
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.mvvm.databinding.MvvmsActivityContainerBinding
import me.shouheng.uix.page.fragment.FragmentKeyDown
import me.shouheng.uix.page.fragment.WebviewFragment
import me.shouheng.utils.stability.LogUtils

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-17 23:28
 */
class FragmentContainer : CommonActivity<MvvmsActivityContainerBinding, EmptyViewModel>(), WebviewFragment.OnReceivedTitleListener {

    override fun getLayoutResId() = R.layout.mvvms_activity_container

    override fun onReceivedTitle(title: String) {
        LogUtils.d(title)
        supportActionBar?.title = title
    }

    override fun doCreateView(savedInstanceState: Bundle?) {
        val fragment = WebviewFragment.Builder()
                .setIndicatorColor(Color.BLUE)
                .setUrl("https://weibo.com/5401152113/profile?rightmod=1&wvr=6&mod=personinfo")
                .build()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        return if ((fragment as? FragmentKeyDown)?.onFragmentKeyDown(keyCode, event) == true) {
            return true
        } else super.onKeyDown(keyCode, event)
    }
}