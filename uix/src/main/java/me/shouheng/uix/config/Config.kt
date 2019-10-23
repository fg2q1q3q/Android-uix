package me.shouheng.uix.config

/**
 * 全局配置
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 12:31
 */
object Config {

    /**
     * 防止连续点击的时候允许的两次点击事件之间的间隔
     */
    var MIN_CLICK_DELAY_TIME                            = 500

    /**
     * RecyclerView 判定为上下滚动的距离
     */
    var RECYCLER_VIEW_SCROLL_DIST                       = 20

    /**
     * ViewPager 变换放缩的大小
     */
    var DEPTH_PAGE_TRANSFORMER_MIN_SCALE                = 0.75f
}