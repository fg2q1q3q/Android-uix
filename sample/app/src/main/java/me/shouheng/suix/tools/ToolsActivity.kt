package me.shouheng.suix.tools

import android.os.Bundle
import me.shouheng.suix.R
import me.shouheng.suix.databinding.ActivityToolsBinding
import me.shouheng.utils.ktx.onContinuousClick
import me.shouheng.utils.ktx.onDebouncedClick
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
        var count = 0
        binding.btnNoDoubleClick.onDebouncedClick { toast("点击了控件 ${count++}") }
        binding.btnContinuousClick.onContinuousClick { toast("连续点击了 $it 次") }
    }
}