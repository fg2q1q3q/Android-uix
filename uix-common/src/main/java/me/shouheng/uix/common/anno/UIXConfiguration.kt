package me.shouheng.uix.common.anno

@Target(allowedTargets = [AnnotationTarget.TYPE, AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.RUNTIME)
annotation class UIXConfiguration (
        /** 防止连续点击的时候允许的两次点击事件之间的间隔，单位：ms */
        val minClickDelayMillis: Long = 500,
        /** 加载按钮的配置注解 */
        val loadingButton: LoadingButtonConfiguration = LoadingButtonConfiguration(),
        /** 普通按钮的配置注解 */
        val normalButton: NormalButtonConfiguration = NormalButtonConfiguration()
)
