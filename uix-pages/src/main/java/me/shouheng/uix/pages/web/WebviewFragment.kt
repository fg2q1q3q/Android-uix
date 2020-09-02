package me.shouheng.uix.pages.web

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import me.shouheng.uix.common.utils.ULog
import me.shouheng.uix.common.utils.UIXResUtils
import me.shouheng.uix.pages.R

/**
 * 网页浏览通用 Fragment. 该 Fragment 是对 AgentWeb 的一个封装，如果需要在项目
 * 中使用这个 Fragment，你需要在你的项目依赖中添加 AgentWeb 的依赖：
 *
 * ```groovy
 * implementation 'com.just.agentweb:agentweb:4.0.2'
 * ```
 *
 * 获取 AgentWeb 的最新版本可以参考 [Agent Web](https://github.com/Justson/AgentWeb)
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
    private var useDefaultOptionsMenu: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) root = inflater.inflate(R.layout.uix_fragment_web, null, false)
        val web = root!!.findViewById<View>(R.id.ll_container) as ViewGroup
        val errorView = LayoutInflater.from(context).inflate(
                R.layout.uix_layout_network_error_page, null, false)
        errorView.findViewById<View>(R.id.root).setBackgroundColor(UIXResUtils.getColor(
                if (isDarkTheme) R.color.uix_default_dark_bg_color else R.color.uix_default_light_bg_color))
        errorView.findViewById<AppCompatButton>(R.id.btn_retry).setBackgroundResource(
                if (isDarkTheme) R.drawable.uix_bg_retry_dark else R.drawable.uix_bg_retry)
        errorView.findViewById<AppCompatButton>(R.id.btn_retry).setTextColor(
                if (isDarkTheme) Color.WHITE else Color.BLACK)
        if (isDarkTheme) {
            errorView.findViewById<TextView>(R.id.tv_error_title)
                    .setTextColor(UIXResUtils.getColor(R.color.dark_theme_text_color_primary))
            errorView.findViewById<TextView>(R.id.tv_error_tips)
                    .setTextColor(UIXResUtils.getColor(R.color.dark_theme_text_color_primary))
        }
        mAgentWeb = getAgentWeb(web, errorView)
        errorView.findViewById<View>(R.id.btn_retry).setOnClickListener {
            mAgentWeb.urlLoader.reload()
        }
        return root
    }

    /**
     * 获取 AgentWeb 对象，可以通过重写这个方法来实现自己的 AgentWeb
     */
    protected fun getAgentWeb(container: ViewGroup, errorView: View): AgentWeb {
        return AgentWeb.with(this)
                .setAgentWebParent(container, -1, LinearLayout.LayoutParams(
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
    }

    /**
     * 在浏览器中打开
     */
    protected fun openInBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(i)
    }

    /**
     * 复制链接
     */
    protected fun copyLink(url: String) {
        val clipboardManager = (context!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager)
        clipboardManager.text = url
    }

    private var mWebChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            ULog.d("onProgressChanged:$newProgress  view:$view")
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (useDefaultOptionsMenu) inflater.inflate(R.menu.uix_web, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (url == null) return super.onOptionsItemSelected(item)
        if (item.itemId == R.id.uix_item_copy) {
            copyLink(url!!)
            return true
        } else if (item.itemId == R.id.uix_item_open) {
            openInBrowser(url!!)
            return true
        }
        return super.onOptionsItemSelected(item)
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
        private var useDefaultOptionsMenu: Boolean = true

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

        fun setUseDefaultOptionsMenu(useDefaultOptionsMenu: Boolean): Builder {
            this.useDefaultOptionsMenu = useDefaultOptionsMenu
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
            fragment.useDefaultOptionsMenu = useDefaultOptionsMenu
            return fragment
        }
    }
}
