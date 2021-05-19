package com.depromeet.linkzupzup.presenter

import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.LinkUseCases

class LinkHistoryDetailViewModel(private val linkUseCases: LinkUseCases): BaseViewModel() {

    companion object {
        val TAG = LinkHistoryDetailViewModel::class.java.simpleName
    }

}