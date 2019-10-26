package me.shouheng.suix.setting

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.FragmentSettingBinding
import me.shouheng.uix.button.SwitchButton
import me.shouheng.uix.items.*
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ui.ImageUtils

/**
 * 设置界面
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-26 21:37
 */
@FragmentConfiguration(layoutResId = R.layout.fragment_setting)
class SettingFragment : CommonFragment<FragmentSettingBinding, EmptyViewModel>() {

    private val adapter = ItemAdapter(emptyList(),
            ResUtils.getColor(R.color.settingBg),
            ImageUtils.tintDrawable(R.drawable.ic_chevron_right_black_24dp, Color.GRAY),
            itemBackground = Color.WHITE)

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.rv.adapter = adapter
        binding.rv.setEmptyView(binding.ev)

        binding.ev.showLoading()
        Handler().postDelayed({
            binding.ev.showEmpty()
            Handler().postDelayed({
                binding.ev.hide()
                adapter.setNewData(
                        listOf(
                                DividerItem(0, bgColor = ResUtils.getColor(R.color.settingBg)),
                                ImageItem(1,
                                        editable = false,
                                        title = "图片的标题啊",
                                        imageLoader = object : Tools {
                                            override fun load(imageView: ImageView) {
                                                imageView.setImageResource(R.mipmap.ic_launcher)
                                            }
                                        }),
                                DividerItem(2, bgColor = ResUtils.getColor(R.color.settingBg)),
                                TextItem(3, title = "文字的标题啊", foot = "末尾啊"),
                                SwitchItem(4, title = "开关的标题啊", enable = true,
                                        onCheckStateChangeListener = object : OnCheckStateChangeListener {
                                            override fun onStateChange(switchButton: SwitchButton, checked: Boolean) {
                                                toast(if (checked) "打开了开关！" else "无情地关闭了开关！")
                                            }
                                        })
                        ))
            }, 1000)
        }, 1000)

        adapter.setOnItemClickListener { _, _, pos ->
            toast("点击了条目$pos")
            val item = adapter.data[pos]
            if (item.itemType == IItem.ItemType.TYPE_SWITCH) {
                item as SwitchItem
                item.enable = !item.enable
                adapter.notifyItemChanged(pos)
            }
        }
    }
}