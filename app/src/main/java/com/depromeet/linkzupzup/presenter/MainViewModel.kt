package com.depromeet.linkzupzup.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.LinkUseCases
import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import com.depromeet.linkzupzup.presenter.model.LinkData
import com.depromeet.linkzupzup.presenter.model.LinkHashData
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.utils.MetaDataUtil.extractUrlFormText
import com.depromeet.linkzupzup.utils.MetaDataUtil.getMetaDataFromUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class MainViewModel(private val linkUseCases: LinkUseCases): BaseViewModel() {

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






    val linkAlarmResponse: MutableLiveData<LinkAlarmResponseEntity> = MutableLiveData()

    /**
     * 사용자가 저장한 링크 리스트 조회
     * completed=F
     * pageNumber=0
     * pageSize=1
     */
    fun getLinkList(pageNumber: Int = 0, pageSize: Int = 10, completed: String = "") {
        defaultParams().apply {
            put(ParamsInfo.PAGE_NUMBER, pageNumber)
            put(ParamsInfo.PAGE_SIZE, pageSize)
            put(ParamsInfo.COMPLETED, completed)
        }.let { params ->
            progressStatus(true)
            addDisposable(linkUseCases.getLinkList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    linkAlarmResponse.value = it

                    progressStatus(false)
                }, this@MainViewModel::defaultThrowable))
        }
    }


    fun getMetadata(url : String){
        CoroutineScope(Dispatchers.IO).launch {
            extractUrlFormText(url)?.let{
                insertLink(getMetaDataFromUrl(it))  // jSoup
            }
        }
    }

    private fun insertLink(link : LinkData){
        // viewModel 에서 제공하는 coroutine scope
        viewModelScope.launch {
            linkUseCases.insertLinkInfo(link = link)
        }
    }
}