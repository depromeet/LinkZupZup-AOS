package com.depromeet.linkzupzup.view.webView

import android.os.Bundle
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.WebViewViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.webView.ui.WebViewUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class WebViewActivity : BaseActivity<WebViewUI,WebViewViewModel>() {
    override var view: WebViewUI = WebViewUI(::onClick)
    override fun onCreateViewModel(): WebViewViewModel = getViewModel()

    private var linkId: Int = 0
    private var linkUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!intent.hasExtra("LINK_ID") || !intent.hasExtra("LINK_URL")){
            DLog.e("WebView","No Link Id or Url")
            finish()
        }

        linkId = intent.getIntExtra("LINK_ID",0)
        linkUrl = intent.getStringExtra("LINK_URL").toString()

        DLog.e("WebView","${linkId} ${linkUrl}")
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