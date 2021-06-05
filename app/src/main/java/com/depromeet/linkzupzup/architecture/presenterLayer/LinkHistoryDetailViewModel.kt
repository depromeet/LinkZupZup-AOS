package com.depromeet.linkzupzup.architecture.presenterLayer

import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases

class LinkHistoryDetailViewModel(private val linkUseCases: LinkUseCases): BaseViewModel() {

    companion object {
        val TAG = LinkHistoryDetailViewModel::class.java.simpleName
    }

}