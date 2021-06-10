package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData.Companion.converter
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.component.MetaDataManager.extractUrlFormText
import com.depromeet.linkzupzup.component.MetaDataManager.getMetaDataFromUrl
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class MainViewModel(private val linkUseCases: LinkUseCases): BaseViewModel() {

    private var _selTagList: MutableLiveData<ArrayList<LinkHashData>> = MutableLiveData(arrayListOf())
    val selTagList: LiveData<ArrayList<LinkHashData>> = _selTagList

    private var _linkAlarmResponse: MutableLiveData<ResponseEntity<LinkAlarmDataEntity>> = MutableLiveData()
    val linkAlarmResponse: LiveData<ResponseEntity<LinkAlarmDataEntity>> = _linkAlarmResponse
    private var _linkList: MutableLiveData<ArrayList<LinkData>> = MutableLiveData(arrayListOf())
    val linkList: LiveData<ArrayList<LinkData>> = _linkList

    /**
     * 사용자가 저장한 링크 리스트 조회
     * completed=F
     * pageNumber=0
     * pageSize=1
     */
    fun getLinkList(pageNumber: Int = 0, pageSize: Int = 10, completed: String = "", callback: ((ResponseEntity<LinkAlarmDataEntity>)->Unit)? = null) {
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
                    val coroutineScope = CoroutineScope(Dispatchers.IO)

                    // 리스트 링크 발췌
                    val urlList = ArrayList(response.data?.content?.map { it.linkURL } ?: arrayListOf())

                    _linkAlarmResponse.value = updateMetaData(urlList, response, coroutineScope) { listObs ->
                        listObs.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe { list ->
                            _linkList.postValue(list.converter())
                            callback?.invoke(response)
                        }
                    }.updatePersonalData(urlList)

                    progressStatus(false)
                }, this@MainViewModel::defaultThrowable))
        }
    }
    fun ResponseEntity<LinkAlarmDataEntity>.updatePersonalData(urlList: ArrayList<String>): ResponseEntity<LinkAlarmDataEntity> {

        return this
    }
    private fun updateMetaData(urlList: ArrayList<String>, response: ResponseEntity<LinkAlarmDataEntity>, coroutineScope: CoroutineScope, listCallback: (Observable<ArrayList<LinkAlarmEntity>>) -> Unit): ResponseEntity<LinkAlarmDataEntity>  = response.apply {
        // 비동기, DB 조회
        coroutineScope.launch {
            linkUseCases.getMetaList(ArrayList(urlList)).let { metaList ->

                // meta data 가 주입된 리스트로 갱신
                data?.content = (ArrayList(data?.content?.map { item ->
                    item.apply {

                        // meta data 주입
                        metaList.find { m -> m.url == linkURL }?.let { target ->
                            metaTitle = target.title
                            metaDescription = target.content
                            metaImageUrl = target.imgUrl
                        }
                    }

                } ?: arrayListOf<LinkAlarmEntity>())).also { list ->
                    listCallback(Observable.create {
                        it.onNext(list)
                    })
                }
            }
        }
    }

    fun loadMetadata(index: Int, linkData: LinkData, callback: (LinkMetaInfoEntity)->Unit = {}) = with(linkData) {
        if(!isMetaSet()) {
            addDisposable(Observable.create<LinkMetaInfoEntity> {
                // 비동기, Meta data 조회
                getMetadata(linkData.linkURL) { metaData ->
                    it.onNext(metaData)
                    it.onComplete()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { metaData ->
                _linkList.value = _linkList.value?.apply {
                    get(index).let {
                        it.linkTitle = metaData.title
                        it.description = metaData.content
                        it.imgURL = metaData.imgUrl
                    }
                }
                callback(metaData)
            })
        }
    }

    private fun getMetadata(url : String, callback: (LinkMetaInfoEntity) -> Unit){
        extractUrlFormText(url)?.let{ rightUrl ->
            getMetaDataFromUrl(rightUrl).let { metaData ->
                // List UI를 갱신하기 위해 콜백
                callback(metaData)
                // 별도 db 갱신
                viewModelScope.launch(Dispatchers.IO) { linkUseCases.insertMetaInfo(metaData) }
            }
        }
    }

    /**
     * 링크 저장
     */
    fun registerLink(linkInfo: LinkRegisterEntity, callback: ((ResponseEntity<LinkAlarmEntity>)->Unit)? = null) {
        progressStatus(true)
        DLog.e("RegisterLink",preference?.getAuthorization().toString())
        DLog.e("RegisterLink",preference?.getUserId().toString())
        addDisposable(linkUseCases.registerLink(linkInfo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->

                when(response.getStatus()) {
                    StatusConst.REGIST_SUCCESS_STATUS -> {
                        callback?.invoke(response)
                    }
                    StatusConst.REGIST_FAIL_STATUS -> {
                        DLog.e("registerLink", response.comment)
                    }
                    else -> {
                        DLog.e("registerLink", response.comment)
                    }
                }
                progressStatus(false)
            }, this@MainViewModel::defaultThrowable))
    }

    /**
     * 오늘 읽은 아티클 갯수 조회
     */
    fun getTodayLinkList(callback: ((ResponseEntity<Int>)->Unit)? = null){
        addDisposable(linkUseCases.getTodayReadCount()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ response ->
                preference?.setTodayCount(response.data ?: 0)
                callback?.invoke(response)
            }, this@MainViewModel::defaultThrowable))
    }

}
