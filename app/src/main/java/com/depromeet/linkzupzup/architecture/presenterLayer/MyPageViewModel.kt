package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoResponseEntity
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyPageViewModel(private val userUseCases: UserUseCases) : BaseViewModel() {

    private var _myPageInfoResponse: MutableLiveData<MyPageInfoResponseEntity> = MutableLiveData()
    private var _nickName: MutableLiveData<String> = MutableLiveData("")
    private var _badgeUrl: MutableLiveData<String> = MutableLiveData("")
    private var _monthlyPoint: MutableLiveData<Int> = MutableLiveData(0)
    private var _totalReadCnt: MutableLiveData<Int> = MutableLiveData(0)
    private var _readCnt: MutableLiveData<Int> = MutableLiveData(0)

    val nickName: LiveData<String> = _nickName
    val badgeUrl: LiveData<String> = _badgeUrl
    val monthlyPoint: LiveData<Int> = _monthlyPoint
    val totalReadCnt: LiveData<Int> = _totalReadCnt
    val readCnt: LiveData<Int> = _readCnt


    fun getMyPageInfo() {
        progressStatus(true)
        addDisposable(userUseCases.getMyPageInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->

                val status = response.getStatus()
                when(status) {
                    StatusConst.SELECT_SUSSCESS_STATUS -> {

                        _myPageInfoResponse.value = response.data

                        _myPageInfoResponse.value?.apply {
                            _nickName.value = this.nickName
                            _badgeUrl.value = this.badge.badgeURL
                            _monthlyPoint.value = this.totalPoint
                            _totalReadCnt.value = this.totalReadCount
                            _readCnt.value = this.seasonCount

                            preference?.setUserName(this.nickName)
                        }
                    }
                    else -> { DLog.e("MyPage",response.comment) }
                }

                progressStatus(false)
            }, this@MyPageViewModel::defaultThrowable))
    }
}