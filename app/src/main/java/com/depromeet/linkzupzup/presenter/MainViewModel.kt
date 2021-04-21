package com.depromeet.linkzupzup.presenter

import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.UserUseCases
import com.depromeet.linkzupzup.extensions.schedulers
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.utils.DLog

class MainViewModel(private val userUseCases: UserUseCases): BaseViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    val userInfo: MutableLiveData<User?> = MutableLiveData()

    fun getUserInfo() {
        addDisposable(userUseCases.getUserInfo()
            .schedulers()
            .doOnSubscribe {
                progressStatus(true)
            }.doOnSuccess {
                progressStatus(false)
            }.subscribe({
                userInfo.value = it
            }) {
                DLog.e("getUserInfo", it.message ?: "")
                progressStatus(false)
            })
    }
}