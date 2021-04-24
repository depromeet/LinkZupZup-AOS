package com.depromeet.linkzupzup.presenter

import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.UserUseCases

class ScrapDetailViewModel(private val userUseCases: UserUseCases): BaseViewModel() {

    companion object {
        val TAG = ScrapDetailViewModel::class.java.simpleName
    }

}