package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoResponseEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MyPageData
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.login.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyPageViewModel(private val userUseCases: UserUseCases) : BaseViewModel() {

    private var _myPageInfoResponse: MutableLiveData<ResponseEntity<MyPageInfoResponseEntity>> = MutableLiveData()

    private var _myPageData: MutableLiveData<MyPageData> = MutableLiveData(MyPageData())
    val myPageData: LiveData<MyPageData> = _myPageData

    fun getMyPageInfo() {
        progressStatus(true)
        addDisposable(userUseCases.getMyPageInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->

                when(response.getStatus()) {
                    StatusConst.SELECT_SUSSCESS_STATUS -> {

                        _myPageInfoResponse.value = response

                        _myPageInfoResponse.value?.data?.let { entity ->
                            _myPageData.value = _myPageData.value?.apply {
                                this.userName = entity.nickName
                                this.badgeUrl = entity.badge.badgeURL
                                this.monthlyPoint = entity.totalPoint
                                this.readCnt = entity.seasonCount
                                this.totalReadCnt = entity.totalReadCount
                                this.alarmEnabled = entity.alarmEnabled
                            }

                            preference?.setUserName(entity.nickName)
                        }
                    }
                    else -> { DLog.e("MyPage",response.comment) }
                }

                progressStatus(false)
            }, this@MyPageViewModel::defaultThrowable))
    }

    /**
     * 푸시 알람 활성화 여부 (활성화 : T, 비활성화 : F)
     */
    fun setAlarmEnabled(alarmEnabled: String) {
        progressStatus(true)
        addDisposable(userUseCases.setAlarmEnabled(alarmEnabled = alarmEnabled)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->

                when(response.getStatus()) {
                    StatusConst.REGIST_SUCCESS_STATUS -> toast(response.comment)
                    else -> toast(response.comment)
                }
                progressStatus(false)
            }, this@MyPageViewModel::defaultThrowable))
    }

    fun logout() {
        (preference?.getLoginId() ?: -1).let { loginId ->
            if (loginId >= 0) {
                addDisposable(userUseCases.logout(loginId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { response ->
                        when (response.getStatus()) {
                            StatusConst.UPDATE_SUCCESS_STATUS -> {
                                toast(response.comment)
                                preference?.logout()
                                moveLoginPage()
                            }
                            else -> { toast(response.comment) }
                        }
                    })
            } else {
                preference?.logout()
                moveLoginPage()
            }

        }
    }

    private fun moveLoginPage() {
        getIntent(LoginActivity::class.java)?.apply {
        }?.let { movePageDelay(it, 300L, true) }
    }

}