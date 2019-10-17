package me.shouheng.suix.container

import android.os.Bundle
import android.view.KeyEvent
import me.shouheng.mvvm.R
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.mvvm.databinding.MvvmsActivityContainerBinding
import me.shouheng.uix.page.FragmentKeyDown
import me.shouheng.uix.page.WebviewFragment
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
                .setUrl("https://weibo.com/5401152113/profile?rightmod=1&wvr=6&mod=personinfo")
                .build()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return (supportFragmentManager.findFragmentById(R.id.container) as? FragmentKeyDown)?.onFragmentKeyDown(keyCode, event)?:super.onKeyDown(keyCode, event)
    }
}