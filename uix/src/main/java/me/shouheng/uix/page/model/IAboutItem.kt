package me.shouheng.uix.page.model

import android.support.annotation.IntDef
import com.chad.library.adapter.base.entity.MultiItemEntity
import me.shouheng.uix.page.model.IAboutItem.ItemType.Companion.TYPE_SECTION
import me.shouheng.uix.page.model.IAboutItem.ItemType.Companion.TYPE_TEXT
import me.shouheng.uix.page.model.IAboutItem.ItemType.Companion.TYPE_USER

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-27 13:20
 */
interface IAboutItem : MultiItemEntity {

    fun getId(): Int

    @IntDef(value = [TYPE_SECTION, TYPE_TEXT, TYPE_USER])
    @Target(allowedTargets = [AnnotationTarget.FIELD,
        AnnotationTarget.TYPE_PARAMETER,
        AnnotationTarget.VALUE_PARAMETER])
    annotation class ItemType {
        companion object {
            /**
             * 分割线
             */
            const val TYPE_SECTION          = 0
            /**
             * 普通的文字
             */
            const val TYPE_TEXT             = 1
            /**
             * 图片
             */
            const val TYPE_USER             = 2
        }
    }
}