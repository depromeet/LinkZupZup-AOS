package com.depromeet.linkzupzup.view.termsAndInfo

import android.os.Bundle
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.architecture.presenterLayer.TermsAndInfoViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.termsAndInfo.ui.TermsAndInfoUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TermsAndInfoActivity : BaseActivity<TermsAndInfoUI, TermsAndInfoViewModel>() {

    override var view: TermsAndInfoUI = TermsAndInfoUI()
    override fun onCreateViewModel(): TermsAndInfoViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linkUrl = intent.getStringExtra(AppConst.WEB_LINK_URL) ?: ""
        viewModel.setUrl(linkUrl)
    }

    override fun onBackPressed() = view.webView?.run {
        if (canGoBack()) goBack() else super.onBackPressed()
    } ?: super.onBackPressed()

}