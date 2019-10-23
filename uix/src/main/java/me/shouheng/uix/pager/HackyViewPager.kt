package me.shouheng.uix.pager

/**
 * Found at http://stackoverflow.com/questions/7814017/is-it-possible-to-disable-scrolling-on-a-viewpager.
 * Convenient way to temporarily disable ViewPager navigation while interacting with ImageView.
 *
 *
 * Julia Zudikova
 */

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 *
 *
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 *
 *
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 * @author Chris Banes
 */
class HackyViewPager : ViewPager {

    var isLocked: Boolean = false

    constructor(context: Context) : super(context) {
        isLocked = false
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        isLocked = false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                return false
            }

        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return !isLocked && super.onTouchEvent(event)
    }

    fun toggleLock() {
        isLocked = !isLocked
    }
}
