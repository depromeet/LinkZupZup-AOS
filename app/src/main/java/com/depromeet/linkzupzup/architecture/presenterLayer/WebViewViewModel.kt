package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadEntity
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.extensions.mapToDataLayer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WebViewViewModel(private val linkUseCases: LinkUseCases) : BaseViewModel() {

    private var _linkUrl: MutableLiveData<String> = MutableLiveData()
    val linkUrl: LiveData<String> = _linkUrl

    private var _linkId: MutableLiveData<Int> = MutableLiveData()
    val linkId: LiveData<Int> = _linkId

    private var _todayReadCnt: MutableLiveData<LinkReadEntity> = MutableLiveData()
    val todayReadCnt: LiveData<LinkReadEntity> = _todayReadCnt

    fun setLinkRead(linkId: Int){
        addDisposable(linkUseCases.setLinkRead(linkId = linkId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response->
                response.data?.let {
                    _todayReadCnt.value = it
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