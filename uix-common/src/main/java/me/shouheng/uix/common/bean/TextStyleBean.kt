package me.shouheng.uix.common.bean

import android.support.annotation.ColorInt
import android.support.annotation.Size
import java.io.Serializable

data class TextStyleBean(
        @ColorInt
        var textColor: Int?                 = null,
        @Size
        var textSize: Float?                = null,
        var typeFace: Int?                  = null,
        var gravity: Int?                   = null
): Serializable