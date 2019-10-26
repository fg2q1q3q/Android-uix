package me.shouheng.uix.rv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import me.shouheng.uix.R
import me.shouheng.uix.config.EmptyLoadingStyle
import me.shouheng.uix.config.EmptyViewState

/**
 * 列表为空控件的一个实现类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-22 22:21
 */
class EmptyView : FrameLayout, IEmptyView {

    private var loadingStyle: Int = EmptyLoadingStyle.STYLE_ANDROID
        set(value) {
            field = value
            isAndroidStyle = loadingStyle == EmptyLoadingStyle.STYLE_ANDROID
            ivLoading.visibility = if (isAndroidStyle) View.GONE else View.VISIBLE
            pb.visibility = if (isAndroidStyle) View.VISIBLE else View.GONE
        }
    private var emptyViewState: Int = EmptyViewState.STATE_LOADING
        set(value) {
            field = value
            isAndroidStyle = loadingStyle == EmptyLoadingStyle.STYLE_ANDROID
            ivLoading.visibility = if (isAndroidStyle) View.GONE else View.VISIBLE
            pb.visibility = if (isAndroidStyle) View.VISIBLE else View.GONE
        }
    private var emptyImage: Int? = null
        set(value) {
            field = value
            if (emptyImage != null) ivEmpty.setImageResource(emptyImage!!)
            else ivEmpty.visibility = View.GONE
        }
    private var emptyTitle: String? = null
        set(value) {
            field = value
            tvEmptyTitle.text = value
        }
    private var emptyTitleColor: Int? = null
        set(value) {
            field = value
            if (value != null) tvEmptyTitle.setTextColor(value)
        }
    private var emptyDetails: String? = null
        set(value) {
            field = value
            tvEmptyDetail.text = value
        }
    private var emptyDetailsColor: Int? = null
        set(value) {
            field = value
            if (value != null) tvEmptyDetail.setTextColor(value)
        }
    private var emptyLoadingTips: String? = null
        set(value) {
            field = value
            tvLoading.text = value
        }
    private var emptyLoadingTipsColor: Int? = null
        set(value) {
            field = value
            if (value != null) tvLoading.setTextColor(value)
        }

    private lateinit var loading: View
    private lateinit var empty: View
    private lateinit var ivLoading: ImageView
    private lateinit var tvLoading: TextView
    private lateinit var pb: ProgressBar
    private lateinit var ivEmpty: ImageView
    private lateinit var tvEmptyTitle: TextView
    private lateinit var tvEmptyDetail: TextView

    private var isLoading: Boolean = false
    private var isAndroidStyle: Boolean = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.uix_layout_empty_view,this, true)

        loading = findViewById(R.id.ll_loading)
        empty = findViewById(R.id.ll_empty)
        ivLoading = findViewById(R.id.iv_loading)
        tvLoading = findViewById(R.id.tv_loading)
        pb = findViewById(R.id.pb)
        ivEmpty = findViewById(R.id.iv_empty)
        tvEmptyTitle = findViewById(R.id.tv_empty_title)
        tvEmptyDetail = findViewById(R.id.tv_empty_detail)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyView)
            loadingStyle = typedArray.getInt(R.styleable.EmptyView_empty_loading_style, EmptyLoadingStyle.STYLE_ANDROID)
            emptyViewState = typedArray.getInt(R.styleable.EmptyView_empty_state, EmptyViewState.STATE_LOADING)

            if (typedArray.hasValue(R.styleable.EmptyView_empty_image)) {
                emptyImage = typedArray.getResourceId(R.styleable.EmptyView_empty_image, -1)
            }

            emptyTitle = typedArray.getString(R.styleable.EmptyView_empty_title)
            emptyDetails = typedArray.getString(R.styleable.EmptyView_empty_detail)
            emptyLoadingTips = typedArray.getString(R.styleable.EmptyView_empty_loading_tips)

            if (typedArray.hasValue(R.styleable.EmptyView_empty_title_text_color))
                emptyTitleColor = typedArray.getColor(R.styleable.EmptyView_empty_title_text_color, 0)
            if (typedArray.hasValue(R.styleable.EmptyView_empty_detail_text_color))
                emptyDetailsColor = typedArray.getColor(R.styleable.EmptyView_empty_detail_text_color, 0)
            if (typedArray.hasValue(R.styleable.EmptyView_empty_loading_tips_text_color))
                emptyLoadingTipsColor = typedArray.getColor(R.styleable.EmptyView_empty_loading_tips_text_color, 0)
            typedArray.recycle()
        }

        tvLoading.text = emptyLoadingTips
        if (emptyLoadingTipsColor != null) tvLoading.setTextColor(emptyLoadingTipsColor!!)
        tvEmptyTitle.text = emptyTitle
        if (emptyTitleColor != null) tvEmptyTitle.setTextColor(emptyTitleColor!!)
        tvEmptyDetail.text = emptyDetails
        if (emptyDetailsColor != null) tvEmptyDetail.setTextColor(emptyDetailsColor!!)

        isAndroidStyle = loadingStyle == EmptyLoadingStyle.STYLE_ANDROID
        ivLoading.visibility = if (isAndroidStyle) View.GONE else View.VISIBLE
        pb.visibility = if (isAndroidStyle) View.VISIBLE else View.GONE
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.uix_dialog_loading)
        ivLoading.startAnimation(hyperspaceJumpAnimation)

        isLoading = emptyViewState == EmptyViewState.STATE_LOADING
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        empty.visibility = if (isLoading) View.GONE else View.VISIBLE

        if (emptyImage != null) ivEmpty.setImageResource(emptyImage!!)
        else ivEmpty.visibility = View.GONE
    }

    override fun showLoading() {
        isLoading = true
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        empty.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun showEmpty() {
        isLoading = false
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        empty.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun show() {
        this.visibility = View.VISIBLE
    }

    override fun hide() {
        this.visibility = View.GONE
    }

    override fun getView(): View = this

    class Builder(private var context: Context) {
        private var loadingStyle: Int = EmptyLoadingStyle.STYLE_ANDROID
        private var emptyViewState: Int = EmptyViewState.STATE_LOADING
        private var emptyImage: Int? = null
        private var emptyTitle: String? = null
        private var emptyTitleColor: Int? = null
        private var emptyDetails: String? = null
        private var emptyDetailsColor: Int? = null
        private var emptyLoadingTips: String? = null
        private var emptyLoadingTipsColor: Int? = null

        fun setLoadingStyle(@EmptyLoadingStyle loadingStyle: Int): Builder {
            this.loadingStyle = loadingStyle
            return this
        }

        fun setEmptyViewState(@EmptyLoadingStyle emptyViewState: Int): Builder {
            this.emptyViewState = emptyViewState
            return this
        }

        fun setEmptyImage(emptyImage: Int): Builder {
            this.emptyImage = emptyImage
            return this
        }

        fun setEmptyTitle(emptyTitle: String): Builder {
            this.emptyTitle = emptyTitle
            return this
        }

        fun setEmptyTitleColor(emptyTitleColor: Int): Builder {
            this.emptyTitleColor = emptyTitleColor
            return this
        }

        fun setEmptyDetails(emptyDetails: String): Builder {
            this.emptyDetails = emptyDetails
            return this
        }

        fun setEmptyDetailsColor(emptyDetailsColor: Int): Builder {
            this.emptyDetailsColor = emptyDetailsColor
            return this
        }

        fun setEmptyLoadingTips(emptyLoadingTips: String): Builder {
            this.emptyLoadingTips = emptyLoadingTips
            return this
        }

        fun setEmptyLoadingTipsColor(emptyLoadingTipsColor: Int): Builder {
            this.emptyLoadingTipsColor = emptyLoadingTipsColor
            return this
        }

        fun build(): EmptyView {
            val emptyView = EmptyView(context)
            emptyView.loadingStyle = loadingStyle
            emptyView.emptyViewState = emptyViewState
            emptyView.emptyImage = emptyImage
            emptyView.emptyTitle = emptyTitle
            emptyView.emptyTitleColor = emptyTitleColor
            emptyView.emptyDetails = emptyDetails
            emptyView.emptyDetailsColor = emptyDetailsColor
            emptyView.emptyLoadingTips = emptyLoadingTips
            emptyView.emptyLoadingTipsColor = emptyLoadingTipsColor
            return emptyView
        }
    }
}