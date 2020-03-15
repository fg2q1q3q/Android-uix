package me.shouheng.uix.common.listener

import me.shouheng.uix.common.anno.UIXConfiguration

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-03-09 19:54
 */
interface UIXConfigurationResolver {

    fun onGetConfiguration(configuration: UIXConfiguration)
}