package me.shouheng.uix.rv.decor

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View


/**
 * Sample:
 *
 * mAdapter = new FabSortAdapter(this, UserPreferences.getInstance().getFabSortResult());
 * getBinding().rvFabs.setAdapter(mAdapter);
 *
 * DragSortRecycler dragSortRecycler = new DragSortRecycler();
 * dragSortRecycler.setViewHandleId(R.id.iv_drag_handler);
 *
 * dragSortRecycler.setOnItemMovedListener((from, to) -> {
 *     saved = false;
 *     FabSortItem fabSortItem = mAdapter.getFabSortItemAt(from);
 *     mAdapter.removeFabSortItemAt(from);
 *     mAdapter.addFabSortItemTo(to, fabSortItem);
 *     mAdapter.notifyDataSetChanged();
 * });
 *
 * getBinding().rvFabs.addItemDecoration(dragSortRecycler);
 * getBinding().rvFabs.addOnItemTouchListener(dragSortRecycler);
 * getBinding().rvFabs.setLayoutManager(new LinearLayoutManager(this));
 * getBinding().rvFabs.addOnItemTouchListener(dragSortRecycler);
 * getBinding().rvFabs.addOnScrollListener(dragSortRecycler.getScrollListener());
 * getBinding().rvFabs.getLayoutManager().scrollToPosition(0);
 */
class DragSortRecycler : RecyclerView.ItemDecoration(), RecyclerView.OnItemTouchListener {

    private val TAG = "DragSortRecycler"

    private val DEBUG = false
    private var moveInterface: OnItemMovedListener? = null
    private var dragStateChangedListener: OnDragStateChangedListener? = null
    private var bgColor = Paint()
    private var dragHandleWidth = 0
    private var selectedDragItemPos = -1
    private var fingerAnchorY: Int = 0

    var scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            debugLog("Scrolled: $dx $dy")
            fingerAnchorY -= dy
        }
    }
        internal set
    private var fingerY: Int = 0
    private var fingerOffsetInViewY: Int = 0
    private var autoScrollWindow = 0.1f
    private var autoScrollSpeed = 0.5f
    private var floatingItem: BitmapDrawable? = null
    private var floatingItemStatingBounds: Rect? = null
    private var floatingItemBounds: Rect? = null
    private var floatingItemAlpha = 0.5f
    private var floatingItemBgColor = 0
    private var viewHandleId = -1
    private var isDragging: Boolean = false

    private fun debugLog(log: String) {
        if (DEBUG)
            Log.d(TAG, log)
    }

    /*
     * Set the item move interface
     */
    fun setOnItemMovedListener(swif: OnItemMovedListener) {
        moveInterface = swif
    }

    fun setViewHandleId(id: Int) {
        viewHandleId = id
    }

    fun setLeftDragArea(w: Int) {
        dragHandleWidth = w
    }

    fun setFloatingAlpha(a: Float) {
        floatingItemAlpha = a
    }

    fun setFloatingBgColor(c: Int) {
        floatingItemBgColor = c
    }

    /*
     Set the window at top and bottom of list, must be between 0 and 0.5
     For example 0.1 uses the top and bottom 10% of the lists for scrolling
     */
    fun setAutoScrollWindow(w: Float) {
        autoScrollWindow = w
    }

    /*
    Set the autoscroll speed, default is 0.5
     */
    fun setAutoScrollSpeed(speed: Float) {
        autoScrollSpeed = speed
    }

    override fun getItemOffsets(outRect: Rect, view: View, rv: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, rv, state)

        debugLog("getItemOffsets")

        debugLog("View top = " + view.top)
        if (selectedDragItemPos != -1) {
            val itemPos = rv.getChildLayoutPosition(view)
            debugLog("itemPos =$itemPos")

            if (!canDragOver(itemPos)) {
                return
            }

            //Movement of finger
            val totalMovement = (fingerY - fingerAnchorY).toFloat()

            if (itemPos == selectedDragItemPos) {
                view.visibility = View.INVISIBLE
            } else {
                //Make view visible incase invisible
                view.visibility = View.VISIBLE

                //Find middle of the floatingItem
                val floatMiddleY = (floatingItemBounds!!.top + floatingItemBounds!!.height() / 2).toFloat()

                //Moving down the list
                //These will auto-animate if the device continually sends touch motion events
                // if (totalMovment>0)
                run {
                    if (itemPos > selectedDragItemPos && view.top < floatMiddleY) {
                        var amountUp = (floatMiddleY - view.top) / view.height.toFloat()
                        //  amountUp *= 0.5f;
                        if (amountUp > 1)
                            amountUp = 1f

                        outRect.top = -(floatingItemBounds!!.height() * amountUp).toInt()
                        outRect.bottom = (floatingItemBounds!!.height() * amountUp).toInt()
                    }

                }//Moving up the list
                // else if (totalMovment < 0)
                run {
                    if (itemPos < selectedDragItemPos && view.bottom > floatMiddleY) {
                        var amountDown = (view.bottom.toFloat() - floatMiddleY) / view.height.toFloat()
                        //  amountDown *= 0.5f;
                        if (amountDown > 1)
                            amountDown = 1f

                        outRect.top = (floatingItemBounds!!.height() * amountDown).toInt()
                        outRect.bottom = -(floatingItemBounds!!.height() * amountDown).toInt()
                    }
                }
            }
        } else {
            outRect.top = 0
            outRect.bottom = 0
            //Make view visible incase invisible
            view.visibility = View.VISIBLE
        }
    }

    /**
     * Find the new position by scanning through the items on
     * screen and finding the positional relationship.
     * This *seems* to work, another method would be to use
     * getItemOffsets, but I think that could miss items?..
     */
    private fun getNewPostion(rv: RecyclerView): Int {
        val itemsOnScreen = rv.layoutManager!!.childCount

        val floatMiddleY = (floatingItemBounds!!.top + floatingItemBounds!!.height() / 2).toFloat()

        var above = 0
        var below = Integer.MAX_VALUE
        for (n in 0 until itemsOnScreen)
        //Scan though items on screen, however they may not
        {                                   // be in order!

            val view = rv.layoutManager!!.getChildAt(n)

            if (view!!.visibility != View.VISIBLE)
                continue

            val itemPos = rv.getChildLayoutPosition(view)

            if (itemPos == selectedDragItemPos)
            //Don't check against itself!
                continue

            val viewMiddleY = (view.top + view.height / 2).toFloat()
            if (floatMiddleY > viewMiddleY)
            //Is above this item
            {
                if (itemPos > above)
                    above = itemPos
            } else if (floatMiddleY <= viewMiddleY)
            //Is below this item
            {
                if (itemPos < below)
                    below = itemPos
            }
        }
        debugLog("above = $above below = $below")

        return if (below != Integer.MAX_VALUE) {
            if (below < selectedDragItemPos)
            //Need to count itself
                below++
            below - 1
        } else {
            if (above < selectedDragItemPos)
                above++

            above
        }
    }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        debugLog("onInterceptTouchEvent")

        //if (e.getAction() == MotionEvent.ACTION_DOWN)
        run {
            val itemView = rv.findChildViewUnder(e.x, e.y) ?: return false

            var dragging = false

            if (dragHandleWidth > 0 && e.x < dragHandleWidth) {
                dragging = true
            } else if (viewHandleId != -1) {
                //Find the handle in the list item
                val handleView = itemView.findViewById<View>(viewHandleId)

                if (handleView == null) {
                    Log.e(TAG, "The view ID $viewHandleId was not found in the RecycleView item")
                    return false
                }

                //View should be visible to drag
                if (handleView.visibility != View.VISIBLE) {
                    return false
                }

                //We need to find the relative position of the handle to the parent view
                //Then we can work out if the touch is within the handle
                val parentItemPos = IntArray(2)
                itemView.getLocationInWindow(parentItemPos)

                val handlePos = IntArray(2)
                handleView.getLocationInWindow(handlePos)

                val xRel = handlePos[0] - parentItemPos[0]
                val yRel = handlePos[1] - parentItemPos[1]

                val touchBounds = Rect(itemView.left + xRel, itemView.top + yRel,
                        itemView.left + xRel + handleView.width,
                        itemView.top + yRel + handleView.height
                )

                if (touchBounds.contains(e.x.toInt(), e.y.toInt()))
                    dragging = true

                debugLog("parentItemPos = " + parentItemPos[0] + " " + parentItemPos[1])
                debugLog("handlePos = " + handlePos[0] + " " + handlePos[1])
            }


            if (dragging) {
                debugLog("Started Drag")

                setIsDragging(true)

                floatingItem = createFloatingBitmap(itemView)

                fingerAnchorY = e.y.toInt()
                fingerOffsetInViewY = fingerAnchorY - itemView.top
                fingerY = fingerAnchorY

                selectedDragItemPos = rv.getChildLayoutPosition(itemView)
                debugLog("selectedDragItemPos = $selectedDragItemPos")

                return true
            }
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {

    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        debugLog("onTouchEvent")

        if (e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_CANCEL) {
            if (e.action == MotionEvent.ACTION_UP && selectedDragItemPos != -1) {
                val newPos = getNewPostion(rv)
                if (moveInterface != null)
                    moveInterface!!.onItemMoved(selectedDragItemPos, newPos)
            }

            setIsDragging(false)
            selectedDragItemPos = -1
            floatingItem = null
            rv.invalidateItemDecorations()
            return
        }


        fingerY = e.y.toInt()

        if (floatingItem != null) {
            floatingItemBounds!!.top = fingerY - fingerOffsetInViewY

            if (floatingItemBounds!!.top < -floatingItemStatingBounds!!.height() / 2)
            //Allow half the view out the top
                floatingItemBounds!!.top = -floatingItemStatingBounds!!.height() / 2

            floatingItemBounds!!.bottom = floatingItemBounds!!.top + floatingItemStatingBounds!!.height()

            floatingItem!!.bounds = floatingItemBounds!!
        }

        //Do auto scrolling at end of list
        var scrollAmount = 0f
        if (fingerY > rv.height * (1 - autoScrollWindow)) {
            scrollAmount = fingerY - rv.height * (1 - autoScrollWindow)
        } else if (fingerY < rv.height * autoScrollWindow) {
            scrollAmount = fingerY - rv.height * autoScrollWindow
        }
        debugLog("Scroll: $scrollAmount")

        scrollAmount *= autoScrollSpeed
        rv.scrollBy(0, scrollAmount.toInt())

        rv.invalidateItemDecorations()// Redraw
    }

    private fun setIsDragging(dragging: Boolean) {
        if (dragging != isDragging) {
            isDragging = dragging
            if (dragStateChangedListener != null) {
                if (isDragging) {
                    dragStateChangedListener!!.onDragStart()
                } else {
                    dragStateChangedListener!!.onDragStop()
                }
            }
        }
    }

    fun setOnDragStateChangedListener(dragStateChangedListener: OnDragStateChangedListener) {
        this.dragStateChangedListener = dragStateChangedListener
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (floatingItem != null) {
            floatingItem!!.alpha = (255 * floatingItemAlpha).toInt()
            bgColor.color = floatingItemBgColor
            c.drawRect(floatingItemBounds!!, bgColor)
            floatingItem!!.draw(c)
        }
    }

    /**
     * @param position
     * @return True if we can drag the item over this position, False if not.
     */
    protected fun canDragOver(position: Int): Boolean {
        return true
    }

    private fun createFloatingBitmap(v: View): BitmapDrawable {
        floatingItemStatingBounds = Rect(v.left, v.top, v.right, v.bottom)
        floatingItemBounds = Rect(floatingItemStatingBounds)

        val bitmap = Bitmap.createBitmap(floatingItemStatingBounds!!.width(),
                floatingItemStatingBounds!!.height(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)

        val retDrawable = BitmapDrawable(v.resources, bitmap)
        retDrawable.bounds = floatingItemBounds!!

        return retDrawable
    }

    interface OnItemMovedListener {
        fun onItemMoved(from: Int, to: Int)
    }

    interface OnDragStateChangedListener {
        fun onDragStart()

        fun onDragStop()
    }
}