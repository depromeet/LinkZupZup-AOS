package com.depromeet.linkzupzup.view.webView

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.WebViewViewModel
import com.depromeet.linkzupzup.view.webView.ui.WebViewUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class WebViewActivity : BaseActivity<WebViewUI,WebViewViewModel>() {
    override var view: WebViewUI = WebViewUI()
    override fun onCreateViewModel(): WebViewViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}