package me.shouheng.uix.common.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import me.shouheng.uix.common.bean.AddressBean

/**
 * UIX Biz 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UBiz {

    private var addresses: List<AddressBean>? = null

    /**
     * 获取地址信息
     */
    fun getAddressList(): List<AddressBean> {
        if (addresses != null) return addresses!!
        val ins = URes.getAssets().open("province.json")
        val bytes = UIO.is2Bytes(ins)
        val content = String(bytes)
        val list = ArrayList<AddressBean>()
        val gson = Gson()
        try {
            val array = JsonParser().parse(content).asJsonArray
            for (jsonElement in array) {
                list.add(gson.fromJson(jsonElement, AddressBean::class.java))
            }
            addresses = list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}
