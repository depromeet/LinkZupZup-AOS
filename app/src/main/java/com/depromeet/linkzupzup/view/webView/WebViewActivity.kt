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
            viewModel.setLinkUrl(getStringExtra(AppConst.WEB_LINK_URL) ?: "")
            viewModel.setLinkId(getIntExtra(AppConst.WEB_LINK_ID, -1))
        }
    }

    private fun onClick(id: Int) = with(viewModel) {
        when (id) {
            R.id.avtivity_close -> {
                super.onBackPressed()
                overridePendingTransition(R.anim.stay, R.anim.act_slide_right_out)
            }
            else -> {}
        }
    }
}