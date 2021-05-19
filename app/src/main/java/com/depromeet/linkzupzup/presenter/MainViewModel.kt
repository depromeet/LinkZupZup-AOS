package com.depromeet.linkzupzup.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.UserUseCases
import com.depromeet.linkzupzup.extensions.schedulers
import com.depromeet.linkzupzup.presenter.model.LinkHashData
import com.depromeet.linkzupzup.presenter.model.TagColor
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.ui.theme.TagBgColor01
import com.depromeet.linkzupzup.ui.theme.TagTextColor01
import com.depromeet.linkzupzup.utils.DLog

class MainViewModel(private val userUseCases: UserUseCases): BaseViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    val userInfo: MutableLiveData<User?> = MutableLiveData()

    private val list = mutableListOf<LinkHashData>()
    private val selectedTagList = MutableLiveData<List<LinkHashData>>()
    val liveSelectedTagList: LiveData<List<LinkHashData>> = selectedTagList

    init {
        selectedTagList.value = list
    }

    fun addHashtag(item: LinkHashData) {
        list.add(item)
        selectedTagList.value = list
    }

    fun removeHashtag(item: LinkHashData) {
        list.remove(item)
        selectedTagList.value = list
    }

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