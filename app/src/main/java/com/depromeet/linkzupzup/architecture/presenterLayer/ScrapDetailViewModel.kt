package com.depromeet.linkzupzup.architecture.presenterLayer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.architecture.domainLayer.MetaUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.extensions.clearMillis
import com.depromeet.linkzupzup.extensions.getTotalTimeSum
import com.depromeet.linkzupzup.architecture.presenterLayer.model.TagColor
import com.depromeet.linkzupzup.extensions.mapToDataLayer
import com.depromeet.linkzupzup.extensions.mapToPresenter
import com.depromeet.linkzupzup.receiver.AlarmReceiver
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.CommonUtil.process
import com.depromeet.linkzupzup.utils.DLog
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ScrapDetailViewModel(private val linkUseCases: LinkUseCases, private val metaUseCases: MetaUseCases): BaseViewModel() {

    private val alarmManager: AlarmManager by lazy { get(AlarmManager::class.java) }

    private var _linkInfo: MutableLiveData<LinkData> = MutableLiveData(LinkData())
    val linkInfo: LiveData<LinkData> = _linkInfo

    private var _metaInfo: MutableLiveData<LinkData> = MutableLiveData()
    val metaInfo: LiveData<LinkData> = _metaInfo

    fun getTagList(): ArrayList<String> {
        return ArrayList(metaInfo.value?.hashtags?.map { it.hashtagName } ?: arrayListOf())
    }

    fun getLinkDetail(linkId: Int){
        progressStatus(true)

        addDisposable(linkUseCases.getLinkDetail(linkId = linkId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->

                when(response.getStatus()) {
                    StatusConst.SELECT_SUSSCESS_STATUS -> {

                        response.data?.apply {

                            _linkInfo.value = LinkData(this).apply {
                                viewModelScope.launch {
                                    metaUseCases.getMetaData(linkURL)?.let { meta ->
                                        linkTitle = meta.title
                                        imgURL = meta.imgUrl
                                        description = meta.content
                                        author = meta.author

                                        DLog.e("Scrape Detail","${_linkInfo.value?.linkURL} ${_linkInfo.value?.linkTitle}")
                                    }
                                }
                            }
                        }
                    }
                    else -> {}
                }
                progressStatus(false)
            }, this@ScrapDetailViewModel::defaultThrowable))
    }

//    private fun ResponseEntity<LinkAlarmEntity>.updateMetaData(callback: ((LinkAlarmEntity)->Unit)? = null): ResponseEntity<LinkAlarmEntity> = apply {
//        viewModelScope.launch {
//            data?.run {
//                metaUseCases.getMetaData(linkUrl = linkURL)?.let { metaData ->
//                    metaTitle = metaData.title
//                    metaDescription = metaData.content
//                    metaImageUrl = metaData.imgUrl
//                    _metaInfo.value = metaData.mapToPresenter()
//                    callback?.invoke(this@run)
//                }
//            }
//        }
//    }
//
//
//    fun getMetaInfo(linkUrl: String, callback: ((LinkData)->Unit)? = null) {
//        viewModelScope.launch {
//            metaUseCases.getMetaData(linkUrl = linkUrl)?.let { meta ->
//                _metaInfo.value = meta.mapToPresenter().also {
//                    callback?.invoke(it)
//                }
//            }
//        }
//    }

    // ?????? ?????? ?????? ??????
    fun addPersonalLinkAlarm(ctx: Context, date: Calendar) {

        /**
         * requestCode??? ?????? ?????? ?????? ???, ????????? ???????????? ????????????,
         * ????????? ??????????????? ????????? ???????????? "???????????????"??? ?????? ?????? ??????????????? ??????????????? ?????????.
         * ex ) year + month + date + hour + minute + second
         */
        val createdDt = Calendar.getInstance()
        val requestCode = createdDt.getTotalTimeSum()
        // ????????? ????????? ????????????
        val timeMillis = date.clearMillis().timeInMillis
        val intent = Intent(ctx, AlarmReceiver::class.java).apply {
            // SQLite ????????? ?????? ?????? ?????? ????????? ???????????? ??????????????? ?????????.
            putExtra("linkData", "linkData")
        }
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.time).let {
            DLog.e("addPersonalLinkAlarm", "add time, $it")
        }
        val pendingIntent = PendingIntent.getBroadcast(ctx, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Doze ???????????? ????????? ???????????? ??????, setExactAndAllowWhileIdle?????? setAlarmClock?????? ??????
        // alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(timeMillis, pendingIntent), pendingIntent)
    }

}