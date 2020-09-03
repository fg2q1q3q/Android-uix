package me.shouheng.uix.widget.pager.tab

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.widget.R

/**
 * ViewPager 的 tab 标签
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-09-30 23:58
 */
class PagerTab : LinearLayout, ViewPager.OnPageChangeListener {

    private var mContext: Context? = null
    private var selectTextSize: Float = 0.toFloat()
    private var selectTextColor: Int = 0
    private var selectTextStyle: Int = 0

    private var unSelectTextSize: Float = 0.toFloat()
    private var unSelectTextColor: Int = 0
    private var unSelectTextStyle: Int = 0

    private var indicatorToBottom: Int = 0  // 指示器到底部
    private var tabToIndicator: Int = 0     // 文字到指示器的间距
    private var tabToTab: Int = 0           // 文字之间的间距

    private var mIndicatorMode: Int = 0             // 指示器的模式 3种
    private var mIndicatorLineScrollMode: Int = 0   // 指示器的滚动模式 2种
    private var mIndicatorLineWidth: Int = 0
    private var mIndicatorLineHeight: Int = 0
    private var mIndicatorLineRadius: Int = 0
    private var mIndicatorLineColor: Int = 0

    private var mTabParentView: LinearLayout? = null
    var mAutoScrollHorizontalScrollView: HorizontalScrollView? = null
    private var mScrollableLine: ScrollableLine? = null

    private var mCurrentPosition: Int = 0

    private var mOnItemTabClickListener: OnItemTabClickListener? = null

    private var mViewPager: ViewPager? = null

    private var tabSelectMode = false//点击tab 没有长度变化效果

    private var secondChange = false

    private var rl: RelativeLayout? = null

    constructor(context: Context) : super(context) {
        initPagerTab(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPagerTab(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPagerTab(context, attrs)
    }

    private fun initPagerTab(context: Context, attrs: AttributeSet?) {
        this.mContext = context
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerTab)
        selectTextSize = typedArray.getDimension(R.styleable.PagerTab_ptSelectTextSize, UView.sp2px(16f).toFloat())
        selectTextColor = typedArray.getColor(R.styleable.PagerTab_ptSelectTextColor, Color.BLACK)
        selectTextStyle = typedArray.getInt(R.styleable.PagerTab_ptSelectTextStyle, Typeface.BOLD)

        unSelectTextSize = typedArray.getDimension(R.styleable.PagerTab_ptUnSelectTextSize, UView.sp2px(16f).toFloat())
        unSelectTextColor = typedArray.getColor(R.styleable.PagerTab_ptUnSelectTextColor, Color.GRAY)
        unSelectTextStyle = typedArray.getInt(R.styleable.PagerTab_ptUnSelectTextStyle, Typeface.NORMAL)

        indicatorToBottom = typedArray.getDimension(R.styleable.PagerTab_ptIndicatorToBottom, UView.dp2px(6f).toFloat()).toInt()
        tabToIndicator = typedArray.getDimension(R.styleable.PagerTab_ptTabToIndicator, UView.dp2px(2f).toFloat()).toInt()
        tabToTab = typedArray.getDimension(R.styleable.PagerTab_ptTabToTab, UView.dp2px(12f).toFloat()).toInt()

        mIndicatorMode = typedArray.getInt(R.styleable.PagerTab_ptIndicatorMode, INDICATOR_MODE_LEFT)
        mIndicatorLineScrollMode = typedArray.getInt(R.styleable.PagerTab_ptIndicatorLineScrollMode, INDICATOR_SCROLL_MODE_DYNAMIC)

        mIndicatorLineWidth = typedArray.getDimension(R.styleable.PagerTab_ptIndicatorLineWidth, UView.dp2px(10f).toFloat()).toInt()
        mIndicatorLineHeight = typedArray.getDimension(R.styleable.PagerTab_ptIndicatorLineHeight, UView.dp2px(3f).toFloat()).toInt()
        mIndicatorLineRadius = typedArray.getDimension(R.styleable.PagerTab_ptIndicatorLineRadius, UView.dp2px(2f).toFloat()).toInt()
        mIndicatorLineColor = typedArray.getColor(R.styleable.PagerTab_ptIndicatorLineColor, Color.RED)

        typedArray.recycle()
        findView()
    }

    private fun findView() {
        val v = LayoutInflater.from(mContext).inflate(R.layout.uix_layout_pagertab, this)
        mAutoScrollHorizontalScrollView = v.findViewById(R.id.autoScrollHorizontalScrollView)
        rl = v.findViewById(R.id.rl)
        mTabParentView = v.findViewById(R.id.tabParent)
        val lp2 = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mIndicatorLineHeight + indicatorToBottom)
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        rl!!.addView(addScrollableLine(), lp2)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mIndicatorLineScrollMode == INDICATOR_SCROLL_MODE_TRANSFORM || tabSelectMode) {
            transformScrollIndicator(position, positionOffset)
        } else {
            dynamicScrollIndicator(position, positionOffset)
        }

        linkageScrollTitleParentToCenter(position, positionOffset)

        if (positionOffset == 0f) {
            tabSelectMode = false
        }
    }

    override fun onPageSelected(position: Int) {
        this.mCurrentPosition = position
        updateTitleStyle(position)
        mViewPager!!.currentItem = position
        if (mOnItemTabClickListener != null) {
            mOnItemTabClickListener!!.onItemTabClick(position)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}

    fun setIndicatorMode(mIndicatorMode: Int) {
        this.mIndicatorMode = mIndicatorMode
    }

    /**
     * INDICATOR_MODE_SCROLLABLE 模式下，滑动条目居中显示
     * 联动模式居中显示当前的条目
     */
    fun linkageScrollTitleParentToCenter(position: Int, positionOffset: Float) {
        if (mTabParentView != null && mTabParentView!!.childCount > position) {
            val positionView = mTabParentView!!.getChildAt(position)
            val afterView = mTabParentView!!.getChildAt(position + 1)
            val positionRight = positionView.right
            val positionWidth = positionView.width
            var afterViewWidth = 0
            if (afterView != null) {
                afterViewWidth = afterView.width
            }
            val halfScreenWidth = UView.getScreenWidth() / 2
            val offsetStart = positionRight - positionWidth / 2 - halfScreenWidth
            val scrollX = ((afterViewWidth / 2 + positionWidth / 2) * positionOffset).toInt() + offsetStart
            if (mAutoScrollHorizontalScrollView != null) {
                mAutoScrollHorizontalScrollView!!.scrollTo(scrollX, 0)
            }
        }
    }

    /**
     * 更新选中和不选中的文字效果
     *
     * @param position
     */
    private fun updateTitleStyle(position: Int) {
        if (mTabParentView == null || mTabParentView!!.childCount <= position) {
            return
        }
        for (i in 0 until mTabParentView!!.childCount) {
            val childView = mTabParentView!!.getChildAt(i)
            if (childView is PagerTabView) {
                if (position == i) {
                    childView.updateStyle(selectTextSize, selectTextColor, selectTextStyle)
                } else {
                    childView.updateStyle(unSelectTextSize, unSelectTextColor, unSelectTextStyle)
                }
            }
        }
    }

    /**
     * indicatorLine 长度不变化
     *
     * @param position
     * @param positionOffset
     */
    private fun transformScrollIndicator(position: Int, positionOffset: Float) {
        if (mTabParentView != null && mTabParentView!!.childCount > position) {
            val positionView = mTabParentView!!.getChildAt(position)
            val positionLeft = positionView.left
            val positionViewWidth = positionView.width
            val afterView = mTabParentView!!.getChildAt(position + 1)
            var afterViewWith = 0
            if (afterView != null) {
                afterViewWith = afterView.width
            }

            val startX =
                positionOffset * (positionViewWidth - (positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - mIndicatorLineWidth) / 2) + ((positionViewWidth - mIndicatorLineWidth) / 2).toFloat() + positionLeft.toFloat()
            val endX =
                positionOffset * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) + (positionView.right - (positionViewWidth - mIndicatorLineWidth) / 2)
            mScrollableLine!!.updateScrollLineWidth(startX, endX, mIndicatorLineColor)
        }
    }

    /**
     * indicatorLine 动态变化长度
     *
     * @param position
     * @param positionOffset
     */
    private fun dynamicScrollIndicator(position: Int, positionOffset: Float) {
        if (mTabParentView != null && mTabParentView!!.childCount > position) {
            val positionView = mTabParentView!!.getChildAt(position)
            val positionLeft = positionView.left
            val positionViewWidth = positionView.width
            val afterView = mTabParentView!!.getChildAt(position + 1)
            var afterViewWith = 0
            if (afterView != null) {
                afterViewWith = afterView.width
            }

            if (positionOffset <= 0.5) {
                val startX = ((positionViewWidth - mIndicatorLineWidth) / 2 + positionLeft).toFloat()
                val endX = 2 * positionOffset * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) + (positionView.right - (positionViewWidth - mIndicatorLineWidth) / 2)
                mScrollableLine!!.updateScrollLineWidth(startX, endX, mIndicatorLineColor)
            } else {
                val startX =
                    positionLeft.toFloat() + ((positionViewWidth - mIndicatorLineWidth) / 2).toFloat() + (positionOffset - 0.5).toFloat() * 2f *
                            (positionViewWidth - (positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - mIndicatorLineWidth) / 2).toFloat()
                val endX = (afterViewWith + positionView.right - (afterViewWith - mIndicatorLineWidth) / 2).toFloat()
                mScrollableLine!!.updateScrollLineWidth(startX, endX, mIndicatorLineColor)
            }
        }
    }

    interface OnItemTabClickListener {
        fun onItemTabClick(position: Int)
    }

    interface OnTabChangeListener {
        fun onChanger(position: Int)
    }

    fun setOnItemTabClickListener(onItemTabClickListener: OnItemTabClickListener) {
        mOnItemTabClickListener = onItemTabClickListener
    }

    fun setViewPager(viewPager: ViewPager?) {
        if (viewPager == null || viewPager.adapter == null) {
            throw RuntimeException("viewpager or pager adapter is null")
        }
        this.mViewPager = viewPager
        viewPager.addOnPageChangeListener(this)
        updateIndicator()

    }

    /**
     * 创建Indicator的View，即ScrollableLine，然后在ScrollableLine绘制Indicator
     * ScrollableLine的
     */
    fun addScrollableLine(): ScrollableLine {
        mScrollableLine = ScrollableLine(mContext!!)
        mScrollableLine!!.setIndicatorStyle(mIndicatorLineHeight, mIndicatorLineRadius)
        return mScrollableLine as ScrollableLine
    }

    fun updateIndicator() {
        if (mViewPager == null) {
            return
        }
        val pagerAdapter = mViewPager!!.adapter
        val pageCount = pagerAdapter!!.count

        val oldCount = mTabParentView!!.childCount
        if (oldCount > pageCount) {
            mTabParentView!!.removeViews(pageCount, oldCount - pageCount)
        }
        for (i in 0 until pageCount) {
            val isOldChild = i < oldCount
            val childView: View
            if (isOldChild) {
                childView = mTabParentView!!.getChildAt(i)
            } else {
                childView = createTabView(pagerAdapter, i)
            }
            if (childView is PagerTabView) {
                setTabStyle(childView, i, pagerAdapter)
                setTabViewLayoutParams(childView, i)
            } else {
                throw IllegalArgumentException("childView must be PageTabView")
            }
        }


        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (secondChange) {
                    val currentpos = mViewPager!!.currentItem
                    transformScrollIndicator(currentpos, 0f)
                    updateTitleStyle(currentpos)

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                } else {
                    val dialogWidth = mAutoScrollHorizontalScrollView!!.width
                    val tabWidth = mTabParentView!!.width
                    val childCount = mTabParentView!!.childCount
                    if (mIndicatorMode == INDICATOR_MODE_CENTER && childCount <= 4 && childCount > 0) {
                        val childWidth = dialogWidth / childCount
                        for (i in 0 until childCount) {
                            val childView = mTabParentView!!.getChildAt(i) as PagerTabView
                            if (i == childCount - 1) {
                                childView.layoutParams =
                                    LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f)
                            } else {
                                childView.layoutParams =
                                    LinearLayout.LayoutParams(childWidth, ViewGroup.LayoutParams.MATCH_PARENT)
                            }
                        }
                        var layoutParams: ViewGroup.LayoutParams? = mTabParentView!!.layoutParams
                        if (null == layoutParams) {
                            layoutParams = LinearLayout.LayoutParams(dialogWidth, ViewGroup.LayoutParams.MATCH_PARENT)
                        }
                        layoutParams.width = dialogWidth
                        mTabParentView!!.layoutParams = layoutParams
                    }

                    secondChange = true
                }


            }
        })
    }


    private fun setTabStyle(childView: PagerTabView, position: Int, pagerAdapter: PagerAdapter) {
        val title = pagerAdapter.getPageTitle(position)!!.toString()
        childView.setTitle(title)
        if (pagerAdapter is ImageTabPagerAdapter) {
            val iconFont = pagerAdapter.getPageImage(position)
            childView.setIconfont(iconFont)
        }
        if (position == 0) {
            childView.updateStyle(selectTextSize, selectTextColor, selectTextStyle)
        } else {
            childView.updateStyle(unSelectTextSize, unSelectTextColor, unSelectTextStyle)
        }
    }

    /**
     * 设置tabView的layoutParams和点击监听，该TabView可以是任何一个View，但是必须包含一个TextView用于显示title
     */
    fun setTabViewLayoutParams(pageTabView: PagerTabView, position: Int) {
        pageTabView.setPadding(tabToTab, 0, tabToTab, 0)
        pageTabView.setOnClickListener {
            if (mViewPager != null) {
                mViewPager!!.currentItem = position
                if (mOnItemTabClickListener != null) {
                    mOnItemTabClickListener!!.onItemTabClick(position)

                }
            }
        }
        // 如果沒有被添加过，则添加
        if (pageTabView.parent == null) {
            mTabParentView!!.addView(pageTabView)
        }
    }

    fun showTabDot(postion: Int) {
        val pagerTabView = mTabParentView!!.getChildAt(postion) as PagerTabView
        pagerTabView.showDot()
    }

    fun hideTabDot(postion: Int) {
        val pagerTabView = mTabParentView!!.getChildAt(postion) as PagerTabView
        pagerTabView.hideDot()
    }

    /**
     * 创建tab view
     */
    fun createTabView(pagerAdapter: PagerAdapter, position: Int): View {
        return PagerTabView(mContext!!)
    }

    companion object {
        /**
         * 指示器的模式
         */
        @Deprecated("")
        val INDICATOR_MODE_SCROLLABLE = 0          // 可滑动的
        @Deprecated("")
        val INDICATOR_MODE_FIXED = 1               // 不可滑动的，且均分
        @Deprecated("")
        val INDICATOR_MODE_SCROLLABLE_CENTER = 2   // 不可滑动，居中模式


        //都是滑动模式
        val INDICATOR_MODE_LEFT = 0    // 居左
        val INDICATOR_MODE_CENTER = 2  // 居中
        /**
         * 滑动条的滚动的模式
         */
        val INDICATOR_SCROLL_MODE_DYNAMIC = 0      // 动态变化模式（Indicator长度动态变化)
        val INDICATOR_SCROLL_MODE_TRANSFORM = 1    // 固定长度的移动模式（Indicator长度不变，移动位置变化）
    }
}

