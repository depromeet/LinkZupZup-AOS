package com.depromeet.linkzupzup.view.scrap

import android.os.Bundle
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.view.scrap.ui.ScrapDetailUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ScrapDetailActivity : BaseActivity<ScrapDetailUI, ScrapDetailViewModel>() {

    override var view: ScrapDetailUI = ScrapDetailUI()
    override fun onCreateViewModel(): ScrapDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            val linkId = intent.getIntExtra(AppConst.LINK_ID, -1)
            val linkUrl = intent.getStringExtra(AppConst.LINK_URL) ?: ""
            getLinkDetail(linkId = linkId)
        }
    }

    fun onClick(){
        this.finish()
    }

}