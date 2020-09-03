package me.shouheng.suix.tools

import android.os.Bundle
import android.view.View
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityToolsBinding
import me.shouheng.uix.common.listener.NoDoubleClickListener
import me.shouheng.uix.common.listener.OnContinuousClickListener
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * 各种工具示例
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-04-18 14:25
 */
class ToolsActivity : CommonActivity<EmptyViewModel, ActivityToolsBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_tools

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnNoDoubleClick.setOnClickListener(object : NoDoubleClickListener() {
            private var count = 0
            override fun onNoDoubleClick(v: View) {
                toast("点击了控件 ${count++}")
            }
        })
        binding.btnContinuousClick.setOnClickListener(object : OnContinuousClickListener() {
            override fun onContinuousClick(continuousClickCount: Int) {
                toast("连续点击了 $continuousClickCount 次")
            }
        })
    }
}