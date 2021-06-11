package com.depromeet.linkzupzup.view.linkHistory

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.LinkHistoryDetailViewModel
import com.depromeet.linkzupzup.view.linkHistory.ui.LinkHistoryDetailUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LinkHistoryDetailActivity : BaseActivity<LinkHistoryDetailUI, LinkHistoryDetailViewModel>() {

    override var view: LinkHistoryDetailUI = LinkHistoryDetailUI()
    override fun onCreateViewModel(): LinkHistoryDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getLinkHistory()
    }

}