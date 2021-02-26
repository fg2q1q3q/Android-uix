package me.shouheng.uix.common.anno

import android.support.annotation.IntDef
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_LEFT
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_MIDDLE
import me.shouheng.uix.common.anno.BottomButtonPosition.Companion.BUTTON_POS_RIGHT

@IntDef(value = [BUTTON_POS_LEFT, BUTTON_POS_MIDDLE, BUTTON_POS_RIGHT])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class BottomButtonPosition {
    companion object {
        const val BUTTON_POS_LEFT                   = 1
        const val BUTTON_POS_MIDDLE                 = 2
        const val BUTTON_POS_RIGHT                  = 3

        fun isLeft(position: Int): Boolean = position == BUTTON_POS_LEFT
        fun isMiddle(position: Int): Boolean = position == BUTTON_POS_MIDDLE
        fun isRight(position: Int): Boolean = position == BUTTON_POS_RIGHT
    }
}
