package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.AppConst
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
import com.depromeet.linkzupzup.view.scrap.ScrapDetailActivity
import com.google.gson.Gson
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
     * ???????????? ????????? ?????? ????????? ??????
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

                    // ????????? ?????? ??????
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
                }, this@MainViewModel::defaultThrowable))
        }
    }

    private fun updateMetaData(urlList: ArrayList<String>, response: ResponseEntity<LinkAlarmDataEntity>, coroutineScope: CoroutineScope, listCallback: (Observable<ArrayList<LinkAlarmEntity>>) -> Unit): ResponseEntity<LinkAlarmDataEntity>  = response.apply {
        // ?????????, DB ??????
        coroutineScope.launch {
            linkUseCases.getMetaList(ArrayList(urlList)).let { metaList ->
                DLog.e("DB", "metaList: ${Gson().toJson(metaList)}")
                // meta data ??? ????????? ???????????? ??????
                data?.content = (ArrayList(data?.content?.map { item ->
                    item.apply {

                        // meta data ??????
                        metaList.find { m -> m.url == linkURL }?.let { target ->
                            metaTitle = target.title
                            metaDescription = target.content
                            metaImageUrl = target.imgUrl
                            metaAuthor = target.author

                            DLog.e("Main","?????? $metaAuthor ????????? ${target.author}")
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
                // ?????????, Meta data ??????
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
            extractUrlFormText(url)?.let { rightUrl ->
                getMetaDataFromUrl(rightUrl).let { metaData ->
                    // List UI??? ???????????? ?????? ??????
                    callback(metaData)
                    // ?????? db ??????
                    linkUseCases.insertMetaInfo(metaData)
                }
            }
        }
    }

    /**
     * ?????? ??????
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
     * ?????? ?????? ????????? ?????? ??????
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

    fun moveScrapDetail(linkData: LinkData) {
        getIntent(ScrapDetailActivity::class.java)?.apply {
            putExtra(AppConst.LINK_ID, linkData.linkId)
            putExtra(AppConst.LINK_URL, linkData.linkURL)
        }?.let { movePageDelay(it, 300L, false) }
    }

}
