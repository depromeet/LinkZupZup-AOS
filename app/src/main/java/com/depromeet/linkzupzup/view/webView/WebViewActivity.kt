package com.depromeet.linkzupzup.view.webView

import android.os.Bundle
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.WebViewViewModel
import com.depromeet.linkzupzup.view.webView.ui.WebViewUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class WebViewActivity : BaseActivity<WebViewUI,WebViewViewModel>() {
    override var view: WebViewUI = WebViewUI(::onClick)
    override fun onCreateViewModel(): WebViewViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.run {
            val linkId = getIntExtra(AppConst.WEB_LINK_ID, -1)
            val linkUrl = getStringExtra(AppConst.WEB_LINK_URL) ?: ""
            var isCompleted = getBooleanExtra(AppConst.WEB_LINK_READ, false)
            viewModel.setLinkId(linkId)
            viewModel.setLinkUrl(linkUrl)
            viewModel.setIsCompleted(isCompleted)
        }
    }

    private fun onClick(id: Int) = with(viewModel) {
        when (id) {
            R.id.activity_close -> {
                super.onBackPressed()
                overridePendingTransition(R.anim.stay, R.anim.act_slide_right_out)
            }
            else -> {}
        }
    }

    override fun onBackPressed() = view.webView?.run {
        if (canGoBack()) goBack() else super.onBackPressed()
    } ?: super.onBackPressed()

}