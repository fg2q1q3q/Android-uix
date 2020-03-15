package me.shouheng.uix.common.anno

import android.support.annotation.IntDef
import me.shouheng.uix.common.anno.AddressSelectLevel.Companion.LEVEL_AREA
import me.shouheng.uix.common.anno.AddressSelectLevel.Companion.LEVEL_CITY

/**
 * 地址选择的最大等级
 */
@IntDef(value = [LEVEL_CITY, LEVEL_AREA])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class AddressSelectLevel {
    companion object {
        const val LEVEL_CITY                        = 0
        const val LEVEL_AREA                        = 1
    }
}