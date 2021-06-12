package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadEntity
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WebViewViewModel(private val linkUseCases: LinkUseCases) : BaseViewModel() {

    private var _linkUrl: MutableLiveData<String> = MutableLiveData()
    val linkUrl: LiveData<String> = _linkUrl

    private var _linkId: MutableLiveData<Int> = MutableLiveData()
    val linkId: LiveData<Int> = _linkId

    private var _todayReadCnt: MutableLiveData<Int> = MutableLiveData()
    val todayReadCnt: LiveData<Int> = _todayReadCnt

    private var _isCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val isCompleted: LiveData<Boolean> = _isCompleted

    fun setLinkRead(linkId: Int, callback:()->Unit){
        addDisposable(linkUseCases.setLinkRead(linkId = linkId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response->

                when(response.getStatus()){
                    StatusConst.UPDATE_SUCCESS_STATUS  -> {
                        response.data?.let {
                            _todayReadCnt.value = it.seasonCount
                            callback()
                            preference?.let { pref-> pref.setTodayCount( pref.getTodayCount() + 1 ) }
                        }
                    }
                    else -> { DLog.e("WebView response", response.comment) }
                }
            }, this@WebViewViewModel::defaultThrowable))
    }

    fun setLinkUrl(url: String) {
        _linkUrl.value = url
    }
    fun setLinkId(Id: Int) {
        _linkId.value = id
    }
    fun setIsCompleted(isCompleted: Boolean){
        _isCompleted.value = isCompleted
    }
}