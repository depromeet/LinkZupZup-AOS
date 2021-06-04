package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.User
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.component.MetaDataManager.extractUrlFormText
import com.depromeet.linkzupzup.component.MetaDataManager.getMetaDataFromUrl
import io.reactivex.Observable
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

    private var _linkAlarmResponse: MutableLiveData<ResponseEntity<LinkAlarmDataEntity>> = MutableLiveData()
    val linkAlarmResponse: LiveData<ResponseEntity<LinkAlarmDataEntity>> = _linkAlarmResponse
    private var _linkList: MutableLiveData<ArrayList<LinkAlarmEntity>> = MutableLiveData(arrayListOf())
    val linkList: LiveData<ArrayList<LinkAlarmEntity>> = _linkList

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
                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    _linkAlarmResponse.value = updateMetaData(response, coroutineScope) { listObs ->
                        listObs.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe { list ->
                            _linkList.postValue(list)
                        }

                    }

                    progressStatus(false)
                }, this@MainViewModel::defaultThrowable))
        }
    }
    private fun updateMetaData(response: ResponseEntity<LinkAlarmDataEntity>, coroutineScope: CoroutineScope, listCallback: (Observable<ArrayList<LinkAlarmEntity>>) -> Unit): ResponseEntity<LinkAlarmDataEntity>  = response.apply {
        // 리스트 링크 발췌
        data?.content?.map { it.linkURL }?.let { urlList ->
            // 비동기, DB 조회
            coroutineScope.launch {
                linkUseCases.getMetaList(ArrayList(urlList)).let { metaList ->

                    // meta data가 주입된 리스트로 갱신
                    data?.content = (ArrayList(data?.content?.map { item ->
                        item.apply {

                            // meta data 주입
                            metaList.find { m -> m.url == item.linkURL }?.let { target ->
                                item.metaTitle = target.title
                                item.metaDescription = target.content
                                item.metaImageUrl = target.imgUrl
                            }

                        }
                    } ?: arrayListOf<LinkAlarmEntity>()))
                        .also { list ->

                            listCallback(Observable.create {
                                it.onNext(list)
                            })

                        }

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
                        it.metaTitle = metaData.title
                        it.metaDescription = metaData.content
                        it.metaImageUrl = metaData.imgUrl
                    }
                }?.also { list ->
                    _linkAlarmResponse.value = _linkAlarmResponse.value?.apply {
                        data?.content = list
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
                runBlocking {
                    CoroutineScope(Dispatchers.IO).launch {
                        linkUseCases.insertMetaInfo(metaData)
                    }
                }
            }
        }
    }
}
