package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
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






    val linkAlarmResponse: MutableLiveData<ResponseEntity<LinkAlarmDataEntity>> = MutableLiveData()

    val _linkListLiveData: MutableLiveData<ArrayList<MainContentData<LinkData>>> = MutableLiveData()
    val linkListLiveData: LiveData<ArrayList<MainContentData<LinkData>>> get() = _linkListLiveData

    private val _linkList: ArrayList<MainContentData<LinkData>> = arrayListOf()
    private val _urlList: ArrayList<String> = arrayListOf()
    private val _metaList: MutableLiveData<ArrayList<LinkMetaInfoEntity>> = MutableLiveData(arrayListOf())

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

                    val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
//                    response.apply {
//                        data?.content?.map { it.linkURL }?.let { urlList ->
//                            coroutineScope.launch {
//                                // 의심 구간 1. list -> arrayList
//                                linkUseCases.getMetaList(ArrayList(urlList)).let { metaList ->
//                                    data = data?.content?.map {
//                                        val target = metaList.find { m -> m.url == it.linkURL }
//
//                                        it.apply {
//                                            target?.let { t ->
//                                                metaTitle = t.title
//                                                metaDescription = t.content
//                                                metaImageUrl = t.imgUrl
//                                            }
//                                        }
//                                    }?.let {
//                                        linkAlarmResponse.value =
//                                    }
//                                }
//
//                            }
//                        }
//                    }


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




                    // 1. 링크 API 정보 저장, url 저장
//                    it.data?.content?.forEach { linkAlarmEntity ->
//                        _linkList.add(MainContentData(MainContentData.MAIN_LINK_ITEM,LinkData(linkAlarmEntity)))
//                        _urlList.add(linkAlarmEntity.linkURL)
//
//                        DLog.e("TEST","add ${linkAlarmEntity.linkURL}")
//                    }
//
//                    // 2. metaInfo 확인 - runBlocking
//                    runBlocking {
//                        launch(Dispatchers.IO) {
//                            _metaList.value?.addAll(linkUseCases.getMetaList(_urlList))
//                            DLog.e("TEST","2.5 _metalist.value size = ${_metaList.value?.size}")
//                        }
//                    }
//
//
//                    DLog.e("TEST","3")
//
//                    // 3. 링크와 metaInfo 매핑
//                    _linkList.apply {
//                        this.map { link->
//                            _metaList.value?.let{ meta->
//                                meta.find { m-> m.url == link.data?.linkURL} ?.let{ find->
//                                    link.data?.setMetaInfo(find)
//                                    DLog.e("TEST","찾았다 ${find.url}")
//                                }?: run {
//                                    CoroutineScope(Dispatchers.IO).launch{
//                                        link.data?.linkURL?.let { url ->
//                                            DLog.e("TEST","링크 $url")
//                                            extractUrlFormText(url)?.let{ rightUrl ->
//                                                linkUseCases.insertMetaInfo(getMetaDataFromUrl(rightUrl))
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    // 4. 매핑된 링크만 UI에 먼저 반영
//                    _linkListLiveData.value?.addAll(_linkList)
//                    _linkListLiveData.value = _linkListLiveData.value


                    progressStatus(false)
                }, this@MainViewModel::defaultThrowable))
        }
    }


    fun getMetadata(url : String, callback: (LinkMetaInfoEntity) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            extractUrlFormText(url)?.let{ rightUrl ->
                getMetaDataFromUrl(rightUrl).let { metaData ->
                    linkUseCases.insertMetaInfo(metaData)
                    callback(metaData)
                }
            }
        }
    }

    fun insertLink(link : LinkData){
        // viewModel 에서 제공하는 coroutine scope
        // viewModelScope.launch {
        //     linkUseCases.insertLinkInfo(link = link)
        // }
    }
}
