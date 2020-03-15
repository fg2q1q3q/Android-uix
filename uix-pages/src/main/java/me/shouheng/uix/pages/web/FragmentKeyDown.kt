package me.shouheng.uix.pages.web

import android.view.KeyEvent

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
