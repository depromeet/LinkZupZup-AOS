package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.base.BaseViewModel

class TermsAndInfoViewModel: BaseViewModel() {

    private val _urlToRender: MutableLiveData<String> = MutableLiveData()
    var urlToRender: LiveData<String> = _urlToRender

    fun setUrl(url: String) {
        _urlToRender.value = url
    }

}