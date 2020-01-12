package me.shouheng.suix.setting

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.suix.R
import me.shouheng.suix.databinding.FragmentSettingBinding
import me.shouheng.uix.button.SwitchButton
import me.shouheng.uix.page.CustomTextViewCallback
import me.shouheng.uix.page.ImageLoader
import me.shouheng.uix.page.OnCheckStateChangeListener
import me.shouheng.uix.page.adapter.SettingItemAdapter
import me.shouheng.uix.page.model.*
import me.shouheng.uix.rv.IEmptyView
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ui.ImageUtils
import me.shouheng.utils.ui.ViewUtils

/**
 * 设置界面
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-26 21:37
 */
@FragmentConfiguration(layoutResId = R.layout.fragment_setting)
class SettingFragment : CommonFragment<FragmentSettingBinding, EmptyViewModel>() {

    private val adapter = SettingItemAdapter(emptyList(),
            ResUtils.getColor(R.color.settingBg),
            ImageUtils.tintDrawable(R.drawable.uix_chevron_right_black_24dp, Color.GRAY),
            itemBackground = Color.WHITE)

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.rv.adapter = adapter
        binding.rv.setEmptyView(binding.ev as IEmptyView)

        binding.ev.showLoading()
        Handler().postDelayed({
            binding.ev.emptyTitle = "【修改了下标题】"
            binding.ev.showEmpty()
            Handler().postDelayed({
                binding.ev.hide()
                adapter.setNewData(
                        listOf(
                                SettingDividerItem(0, bgColor = ResUtils.getColor(R.color.settingBg)),
                                SettingImageItem(1,
                                        editable = false,
                                        title = "图片的标题啊",
                                        imageLoader = object : ImageLoader {
                                            override fun load(imageView: ImageView) {
                                                imageView.setImageResource(R.mipmap.ic_launcher)
                                            }
                                        }),
                                SettingDividerItem(2, bgColor = ResUtils.getColor(R.color.settingBg)),
                                SettingTextItem(3, title = "文字的标题啊", foot = "末尾啊", lineColor = Color.TRANSPARENT),
                                SettingDescItem(5,
                                        desc = "描述描述描述描述描述描述",
                                        lineColor = ResUtils.getColor(R.color.settingBg),
                                        callback = object : CustomTextViewCallback {
                                            override fun custom(tv: TextView) {
                                                tv.setTextColor(Color.RED)
                                                tv.gravity = Gravity.END
                                                tv.setPadding(0, 0, ViewUtils.dp2px(20f), 0)
                                            }
                                        }),
                                SettingSwitchItem(4, title = "开关的标题啊", enable = true,
                                        onCheckStateChangeListener = object : OnCheckStateChangeListener {
                                            override fun onStateChange(switchButton: SwitchButton, checked: Boolean) {
                                                toast(if (checked) "打开了开关！" else "无情地关闭了开关！")
                                            }
                                        }),
                                SettingLongTextItem(5, title = "长长的文案啊", subTitle = "观棋柯烂，伐木丁丁，云边谷口徐行。卖薪沽酒，狂笑自陶情。苍径秋高，对月枕松根，一觉天明。认旧林，登崖过岭，持斧断枯藤。收来成一担，行歌市上，易米三升。更无些子争竞，时价平平。不会机谋巧算，没荣辱，恬淡延生。相逢处，非仙即道，静坐讲《黄庭》。")
                        ))
            }, 1000)
        }, 1000)

        adapter.setOnItemClickListener { _, _, pos ->
            toast("点击了条目$pos")
            val item = adapter.data[pos]
            if (item.itemType == ISettingItem.ItemType.TYPE_SWITCH) {
                item as SettingSwitchItem
                if (item.loading) return@setOnItemClickListener
                item.enable = !item.enable
                item.loading = true
                adapter.notifyItemChanged(pos)
                Handler().postDelayed({
                    item.loading = false
                    adapter.notifyItemChanged(pos)
                }, 2000)
            }
            if (item.itemType == ISettingItem.ItemType.TYPE_TEXT) {
                item as SettingTextItem
                if (item.loading) return@setOnItemClickListener
                item.loading = true
                adapter.notifyItemChanged(pos)
                Handler().postDelayed({
                    item.loading = false
                    adapter.notifyItemChanged(pos)
                }, 2000)
            }
            if (item.itemType == ISettingItem.ItemType.TYPE_IMAGE) {
                item as SettingImageItem
                if (item.loading) return@setOnItemClickListener
                item.loading = true
                adapter.notifyItemChanged(pos)
                Handler().postDelayed({
                    item.loading = false
                    adapter.notifyItemChanged(pos)
                }, 2000)
            }
            if (item.itemType == ISettingItem.ItemType.TYPE_LONG_TEXT) {
                item as SettingLongTextItem
                if (item.loading) return@setOnItemClickListener
                item.loading = true
                adapter.notifyItemChanged(pos)
                Handler().postDelayed({
                    item.loading = false
                    adapter.notifyItemChanged(pos)
                }, 2000)
            }
        }
    }
}