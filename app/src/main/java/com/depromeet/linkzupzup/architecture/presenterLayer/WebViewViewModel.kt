package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadResponseEntity
import com.depromeet.linkzupzup.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WebViewViewModel(private val linkUseCases: LinkUseCases) : BaseViewModel() {

    private var _linkUrl: MutableLiveData<String> = MutableLiveData()
    val linkUrl: LiveData<String> = _linkUrl

    private var _linkId: MutableLiveData<Int> = MutableLiveData()
    val linkId: LiveData<Int> = _linkId

    private var _todayReadResponseCnt: MutableLiveData<LinkReadResponseEntity> = MutableLiveData()
    val todayReadResponseCnt: LiveData<LinkReadResponseEntity> = _todayReadResponseCnt

    fun setLinkRead(linkId: Int){
        addDisposable(linkUseCases.setLinkRead(linkId = linkId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response->
                response.data?.let {
                    _todayReadResponseCnt.value = it
                }
            }, this@WebViewViewModel::defaultThrowable))
    }

    fun setLinkUrl(url: String) {
        _linkUrl.value = url
    }
    fun setLinkId(Id: Int) {
        _linkId.value = Id
    }
}