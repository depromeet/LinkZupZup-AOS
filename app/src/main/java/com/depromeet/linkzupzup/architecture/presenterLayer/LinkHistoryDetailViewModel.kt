package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData.Companion.converter
import com.depromeet.linkzupzup.component.MetaDataManager
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.scrap.ScrapDetailActivity
import com.depromeet.linkzupzup.view.scrap.ScrapDetailAnkoActivity
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LinkHistoryDetailViewModel(private val linkUseCases: LinkUseCases): BaseViewModel() {

    companion object {
        val TAG = LinkHistoryDetailViewModel::class.java.simpleName
    }

    private var _linkAlarmResponse: MutableLiveData<ResponseEntity<LinkAlarmDataEntity>> = MutableLiveData()
    private var _linkList: MutableLiveData<ArrayList<LinkData>> = MutableLiveData(arrayListOf())
    val linkList: LiveData<ArrayList<LinkData>> = _linkList

    fun getLinkHistory(pageNumber: Int = 0, pageSize: Int = 10, completed: String = "T", callback: ((ResponseEntity<LinkAlarmDataEntity>)->Unit)? = null) {
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
                    }

                    progressStatus(false)
                }, this@LinkHistoryDetailViewModel::defaultThrowable))
        }
    }

    private fun updateMetaData(urlList: ArrayList<String>, response: ResponseEntity<LinkAlarmDataEntity>, coroutineScope: CoroutineScope, listCallback: (Observable<ArrayList<LinkAlarmEntity>>) -> Unit): ResponseEntity<LinkAlarmDataEntity>  = response.apply {
        // 비동기, DB 조회
        coroutineScope.launch {
            linkUseCases.getMetaList(ArrayList(urlList)).let { metaList ->
                DLog.e("DB", "metaList: ${Gson().toJson(metaList)}")
                // meta data 가 주입된 리스트로 갱신
                data?.content = (ArrayList(data?.content?.map { item ->
                    item.apply {

                        // meta data 주입
                        metaList.find { m -> m.url == linkURL }?.let { target ->
                            metaTitle = target.title
                            metaDescription = target.content
                            metaImageUrl = target.imgUrl
                            metaAuthor = target.author

                            DLog.e("Main","메타 $metaAuthor 엔티티 ${target.author}")
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
                            it.author = metaData.author
                            it.authorImgUrl = metaData.authorImgUrl
                        }
                    }
                    callback(metaData)
                })
        } else {
            getMetadata(linkData.linkURL) {
                callback(it)
            }
        }
    }

    private fun getMetadata(url : String, callback: (LinkMetaInfoEntity) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            MetaDataManager.extractUrlFormText(url)?.let { rightUrl ->
                MetaDataManager.getMetaDataFromUrl(rightUrl).let { metaData ->
                    // List UI를 갱신하기 위해 콜백
                    callback(metaData)
                    // 별도 db 갱신
                    linkUseCases.insertMetaInfo(metaData)
                }
            }
        }
    }

    fun moveScrapDetail(linkData: LinkData) {
        getIntent(ScrapDetailAnkoActivity::class.java)?.apply {
            putExtra(AppConst.LINK_ID, linkData.linkId)
            putExtra(AppConst.LINK_URL, linkData.linkURL)
        }?.let { movePageDelay(it, 300L, false) }
    }

}