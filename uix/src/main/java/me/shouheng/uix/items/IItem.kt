package me.shouheng.uix.items

import android.support.annotation.IntDef
import com.chad.library.adapter.base.entity.MultiItemEntity
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_DIVIDER
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_IMAGE
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.items.IItem.ItemType.Companion.TYPE_SWITCH

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 18:17
 */
interface IItem : MultiItemEntity {

    fun getId(): Int

    fun editable(): Boolean

    @IntDef(value = [TYPE_TEXT, TYPE_IMAGE, TYPE_DIVIDER, TYPE_SWITCH])
    @Target(allowedTargets = [AnnotationTarget.FIELD,
        AnnotationTarget.TYPE_PARAMETER,
        AnnotationTarget.VALUE_PARAMETER])
    annotation class ItemType {
        companion object {
            /**
             * 分割线
             */
            const val TYPE_DIVIDER          = 0
            /**
             * 普通的文字
             */
            const val TYPE_TEXT             = 1
            /**
             * 图片
             */
            const val TYPE_IMAGE            = 2
            /**
             * Switch 按钮
             */
            const val TYPE_SWITCH           = 3
        }
    }
}