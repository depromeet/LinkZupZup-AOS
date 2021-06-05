package com.depromeet.linkzupzup.architecture.presenterLayer

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.component.MetaDataManager.extractUrlFormText
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MainContentData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.User
import com.depromeet.linkzupzup.component.MetaDataManager.getMetaDataFromUrl
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class MainViewModel(private val linkUseCases: LinkUseCases): BaseViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName
        const val MAX_HASH_TAG_SELECT = 3
    }

    val userInfo: MutableLiveData<User?> = MutableLiveData()
    val selectTagList: MutableLiveData<ArrayList<LinkHashData>> = MutableLiveData(arrayListOf())


    val linkAlarmResponse: MutableLiveData<ResponseEntity<LinkAlarmDataEntity>> = MutableLiveData()

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
                .subscribe({ response ->
                    // linkAlarmResponse.value = response

                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    linkAlarmResponse.value = response.apply {
                        data = data?.apply {
                            // url 만 발췌
                            content.map { it.linkURL }.let { urlList ->
                                // 비동기 DB 조회
                                coroutineScope.launch {
                                    // 의심 구간 1. list -> arrayList
                                    linkUseCases.getMetaList(ArrayList(urlList)).let { metaList ->
                                        // data.content
                                        content = ArrayList(data?.content?.map {
                                            val target = metaList.find { m -> m.url == it.linkURL }

                                            it.apply {
                                                // url 이 일치할 경우에만 추가하기 위함
                                                target?.let { t ->
                                                    metaTitle = t.title
                                                    metaDescription = t.content
                                                    metaImageUrl = t.imgUrl
                                                }
                                            }
                                        } ?: arrayListOf<LinkAlarmEntity>())
                                    }
                                }
                            }
                        }
                    }

                    progressStatus(false)
                }, this@MainViewModel::defaultThrowable))
        }
    }


    fun getMetadata(url : String, callback: (LinkMetaInfoEntity) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            extractUrlFormText(url)?.let { rightUrl ->
                getMetaDataFromUrl(rightUrl).let { metaData ->
                    linkUseCases.insertMetaInfo(metaData)
                    callback(metaData)
                }
            }
        }
    }

    fun insertSelectedTag(tag: LinkHashData){
        viewModelScope.launch {
            selectTagList.value?.apply {
                if(this.size < Companion.MAX_HASH_TAG_SELECT && !this.contains(tag)){
                    this.add(tag)

                }
            }
            selectTagList.postValue(selectTagList.value)
        }
    }

    fun removeSelectedTag(tag: LinkHashData){
        viewModelScope.launch {
            selectTagList.value?.apply {
                if(this.contains(tag)){
                    this.remove(tag)
                }
            }
            selectTagList.postValue(selectTagList.value)
        }

    }

}
