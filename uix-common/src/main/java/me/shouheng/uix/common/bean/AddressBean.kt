package me.shouheng.uix.common.bean

/**
 * Address bean
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-05 11:09
 */
class AddressBean {

    /**
     * name : 北京市
     * city : [{"name":"北京市","area":["东城区","西城区","海淀区","朝阳区","丰台区","石景山区","门头沟区","通州区","顺义区","房山区","大兴区","昌平区","怀柔区","平谷区","密云区","延庆区"]}]
     */

    var name: String? = null
    var city: List<CityBean>? = null

    class CityBean {
        /**
         * name : 北京市
         * area : ["东城区","西城区","海淀区","朝阳区","丰台区","石景山区","门头沟区","通州区","顺义区","房山区","大兴区","昌平区","怀柔区","平谷区","密云区","延庆区"]
         */
        var name: String? = null
        var area: List<String>? = null
    }
}
