package com.depromeet.linkzupzup.view.termsAndInfo

import android.os.Bundle
import com.depromeet.linkzupzup.architecture.presenterLayer.TermsAndInfoViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.extensions.toast
import com.depromeet.linkzupzup.view.termsAndInfo.ui.TermsAndInfoUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TermsAndInfoActivity : BaseActivity<TermsAndInfoUI, TermsAndInfoViewModel>() {

    override var view: TermsAndInfoUI = TermsAndInfoUI()
    override fun onCreateViewModel(): TermsAndInfoViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra("URL")?.let {
            toast(this,it)
            viewModel.setUrlToWebView(it)
        }

    }

}