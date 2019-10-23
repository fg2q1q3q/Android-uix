package me.shouheng.uix.dialog.content

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.R
import me.shouheng.uix.bean.AddressBean
import me.shouheng.uix.config.AddressSelectLevel
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.config.AddressSelectLevel.Companion.LEVEL_CITY
import me.shouheng.uix.dialog.BeautyDialog
import me.shouheng.uix.utils.UIXUtils

/**
 * 地址选择
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-05 10:44
 */
class AddressContent: IDialogContent {

    @AddressSelectLevel private var maxLevel: Int = LEVEL_CITY
    private var listener: OnAddressSelectedListener? = null
    private lateinit var dialog: BeautyDialog

    private lateinit var tvProvince : TextView
    private lateinit var tvCity : TextView
    private lateinit var tvArea : TextView

    override fun getView(ctx: Context): View {
        val layout = View.inflate(ctx, R.layout.uix_dialog_content_address, null)

        val rvProvince = layout.findViewById<RecyclerView>(R.id.rv_province)
        val rvCity = layout.findViewById<RecyclerView>(R.id.rv_city)
        val rvArea = layout.findViewById<RecyclerView>(R.id.rv_area)

        tvProvince = layout.findViewById(R.id.tv_province)
        tvCity = layout.findViewById(R.id.tv_city)
        tvArea = layout.findViewById(R.id.tv_area)

        val list = UIXUtils.getAddressList()
        val adapterProvince = ProvinceAdapter(list)
        rvProvince.adapter = adapterProvince
        rvProvince.visibility = View.VISIBLE

        rvCity.visibility = View.GONE
        rvArea.visibility = View.GONE

        adapterProvince.setOnItemClickListener { _, _, pos ->
            val province = list[pos].name
            val cityAdapter = CityAdapter()
            val cityBeans = list[pos].city
            cityAdapter.setNewData(cityBeans)
            tvProvince.text = province
            rvCity.adapter = cityAdapter
            rvProvince.visibility = View.GONE
            rvCity.visibility = View.VISIBLE
            rvArea.visibility = View.GONE
            cityAdapter.setOnItemClickListener { _, _, cityPos ->
                val city = cityBeans!![cityPos].name
                val areaAdapter = AreaAdapter()
                val areaBeans = cityBeans[cityPos].area
                areaAdapter.setNewData(areaBeans)
                tvCity.text = city
                rvArea.adapter = areaAdapter
                rvProvince.visibility = View.GONE
                rvCity.visibility = View.GONE
                if (maxLevel == LEVEL_CITY) {
                    listener?.onAddressSelected(dialog, province!!, city, null)
                } else {
                    rvArea.visibility = View.VISIBLE
                }
                areaAdapter.setOnItemClickListener { _, _, areaPos ->
                    val area = areaBeans!![areaPos]
                    tvArea.text = area
                    if (maxLevel == LEVEL_AREA) {
                        listener?.onAddressSelected(dialog, province!!, city, area)
                    }
                }
            }
        }

        tvProvince.setOnClickListener {
            tvCity.text = ""
            tvArea.text = ""
            rvProvince.visibility = View.VISIBLE
            rvCity.visibility = View.GONE
            rvArea.visibility = View.GONE
        }
        tvCity.setOnClickListener {
            tvArea.text = ""
            rvProvince.visibility = View.GONE
            rvCity.visibility = View.VISIBLE
            rvArea.visibility = View.GONE
        }
        tvArea.setOnClickListener {
            rvProvince.visibility = View.GONE
            rvCity.visibility = View.GONE
            rvArea.visibility = View.VISIBLE
        }

        return layout
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    fun getSelction(): Triple<String?, String?, String?> {
        return Triple<String?, String?, String?>(tvProvince.text.toString(), tvCity.text.toString(), tvArea.text.toString())
    }

    interface OnAddressSelectedListener {

        fun onAddressSelected(dialog: BeautyDialog, province: String, city: String?, area: String?)
    }

    private class ProvinceAdapter(list: List<AddressBean>): BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.uix_dialog_content_address_item, list) {

        override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
            helper?.setText(R.id.tv, item?.name)
        }
    }

    private class CityAdapter: BaseQuickAdapter<AddressBean.CityBean, BaseViewHolder>(R.layout.uix_dialog_content_address_item) {

        override fun convert(helper: BaseViewHolder?, item: AddressBean.CityBean?) {
            helper?.setText(R.id.tv, item?.name)
        }
    }

    private class AreaAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.uix_dialog_content_address_item) {

        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper?.setText(R.id.tv, item)
        }
    }

    class Builder {
        @AddressSelectLevel private var maxLevel: Int = LEVEL_CITY
        private var listener: OnAddressSelectedListener? = null

        fun setMaxLevel(@AddressSelectLevel maxLevel: Int): Builder {
            this.maxLevel = maxLevel
            return this
        }

        fun setOnAddressSelectedListener(listener: OnAddressSelectedListener): Builder {
            this.listener = listener
            return this
        }

        fun build(): AddressContent {
            val content = AddressContent()
            content.maxLevel = maxLevel
            content.listener = listener
            return content
        }
    }
}