package com.depromeet.linkzupzup.architecture.presenterLayer

import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.base.BaseViewModel

class WebViewViewModel(private val linkUseCases: LinkUseCases) : BaseViewModel() {

    fun setLinkRead(): Boolean{
        //addDisposable(linkUseCases.)
        return true
    }

}