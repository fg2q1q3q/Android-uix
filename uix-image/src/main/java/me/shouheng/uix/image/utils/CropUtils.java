package me.shouheng.uix.image.utils;

import android.graphics.Point;

/**
 * 裁剪相关的工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @since 2020/09/01 23:12
 */
public final class CropUtils {

    /**
     * 获取两个点之间的距离
     *
     * @param p1 点1
     * @param p2 点2
     * @return   距离
     */
    public static double getDistance(Point p1, Point p2) {
        return getDistance(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * 获取两个点之间的距离
     *
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     * @return   距离
     */
    public static double getDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /*--------------------------------- inner methods ----------------------------------*/

    private CropUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
