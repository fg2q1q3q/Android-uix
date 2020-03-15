//package me.shouheng.uix.common.config
//
//import com.google.auto.service.AutoService
//import me.shouheng.uix.common.anno.UIXConfiguration
//import me.shouheng.uix.common.listener.NoDoubleClickListener
//import me.shouheng.uix.common.listener.UIXConfigurationResolver
//
///**
// * UIX common configuration resolver.
// *
// * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
// * @version 2020-03-09 20:09
// */
//@AutoService(UIXConfigurationResolver::class)
//class UIXConfigurationCommonResolver : UIXConfigurationResolver {
//    override fun onGetConfiguration(configuration: UIXConfiguration) {
//        NoDoubleClickListener.MIN_CLICK_DELAY_TIME = configuration.minClickDelayMillis
//    }
//}