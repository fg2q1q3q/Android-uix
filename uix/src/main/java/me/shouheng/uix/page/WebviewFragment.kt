package me.shouheng.uix.page

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
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
    @ColorInt
    private var indicatorColor: Int = Color.RED
    private var url: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.uix_fragment_web, null, false)
        }
        val web = root!!.findViewById<View>(R.id.ll_container) as ViewGroup
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web, -1, LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(indicatorColor, 3)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(object : WebViewClient() { } )
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.uix_layout_network_error_page, R.id.btn_retry)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url)
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

        private var indicatorColor: Int = Color.RED
        private var url: String? = null

        fun setIndicatorColor(@ColorInt indicatorColor: Int): Builder {
            this.indicatorColor = indicatorColor
            return this
        }

        fun setUrl(url: String): Builder {
            this.url = url
            return this
        }

        fun build(): WebviewFragment {
            val fragment = WebviewFragment()
            fragment.indicatorColor = indicatorColor
            fragment.url = url
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
