package me.shouheng.uix.page.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import me.shouheng.uix.R
import me.shouheng.uix.utils.UIXUtils

/**
 * 网页浏览通用 fragment
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-17 22:38
 */
open class WebviewFragment : Fragment(), FragmentKeyDown {

    private var root: View? = null
    private lateinit var mAgentWeb: AgentWeb
    private var isDarkTheme: Boolean = false
    @ColorInt private var indicatorColor: Int = Color.RED
    private var indicatorHeightDp: Int = 3
    private var url: String? = null
    private var openOtherPageWays: DefaultWebClient.OpenOtherPageWays? = null
    private var securityType: AgentWeb.SecurityType = AgentWeb.SecurityType.STRICT_CHECK

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.uix_fragment_web, null, false)
        }
        val web = root!!.findViewById<View>(R.id.ll_container) as ViewGroup
        val errorView = LayoutInflater.from(context).inflate(R.layout.uix_layout_network_error_page, null, false)
        errorView.findViewById<View>(R.id.root).setBackgroundColor(
                UIXUtils.getColor(if (isDarkTheme) R.color.dark_background_color else R.color.light_background_color))
        errorView.findViewById<AppCompatButton>(R.id.btn_retry).setBackgroundResource(
                if (isDarkTheme) R.drawable.uix_bg_retry_dark else R.drawable.uix_bg_retry)
        errorView.findViewById<AppCompatButton>(R.id.btn_retry).setTextColor(
                if (isDarkTheme) Color.WHITE else Color.BLACK)
        if (isDarkTheme) {
            errorView.findViewById<TextView>(R.id.tv_error_title).setTextColor(UIXUtils.getColor(R.color.dark_theme_text_color_primary))
            errorView.findViewById<TextView>(R.id.tv_error_tips).setTextColor(UIXUtils.getColor(R.color.dark_theme_text_color_primary))
        }
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web, -1, LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(indicatorColor, indicatorHeightDp)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(object : WebViewClient() { } )
                .setSecurityType(securityType)
                .setMainFrameErrorView(errorView)
                .setOpenOtherPageWays(openOtherPageWays)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url)
        errorView.findViewById<View>(R.id.btn_retry).setOnClickListener {
            mAgentWeb.urlLoader.reload()
        }
        return root
    }

    private var mWebChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            UIXUtils.d("onProgressChanged:$newProgress  view:$view")
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            (activity as? OnReceivedTitleListener)?.onReceivedTitle(title)
        }
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume() // 恢复
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onFragmentKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return mAgentWeb.handleKeyEvent(keyCode, event)
    }

    override fun onDestroyView() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroyView()
    }

    interface OnReceivedTitleListener {

        /**
         * callback when receive page title
         */
        fun onReceivedTitle(title: String)
    }

    class Builder {
        private var isDarkTheme: Boolean = false
        private var indicatorColor: Int = Color.RED
        private var indicatorHeightDp: Int = 3
        private var url: String? = null
        private var openOtherPageWays: DefaultWebClient.OpenOtherPageWays? = null
        private var securityType: AgentWeb.SecurityType = AgentWeb.SecurityType.STRICT_CHECK

        fun setDarkTheme(isDarkTheme: Boolean): Builder {
            this.isDarkTheme = isDarkTheme
            return this
        }

        fun setIndicatorColor(@ColorInt indicatorColor: Int): Builder {
            this.indicatorColor = indicatorColor
            return this
        }

        fun setIndicatorHeight(indicatorHeightDp: Int): Builder {
            this.indicatorHeightDp = indicatorHeightDp
            return this
        }

        fun setUrl(url: String): Builder {
            this.url = url
            return this
        }

        fun setOpenOtherPageWays(openOtherPageWays: DefaultWebClient.OpenOtherPageWays): Builder {
            this.openOtherPageWays = openOtherPageWays
            return this
        }

        fun setSecurityType(securityType: AgentWeb.SecurityType): Builder {
            this.securityType = securityType
            return this
        }

        fun build(): WebviewFragment {
            val fragment = WebviewFragment()
            fragment.isDarkTheme = isDarkTheme
            fragment.indicatorColor = indicatorColor
            fragment.indicatorHeightDp = indicatorHeightDp
            fragment.url = url
            fragment.openOtherPageWays = openOtherPageWays
            fragment.securityType = securityType
            return fragment
        }
    }
}

interface FragmentKeyDown {

    /**
     * The interaction method when the fragment key down, used to interact with webview fragment
     *
     * @param keyCode the key code
     * @param event   the key event
     * @return        is event handled
     */
    fun onFragmentKeyDown(keyCode: Int, event: KeyEvent?): Boolean
}
