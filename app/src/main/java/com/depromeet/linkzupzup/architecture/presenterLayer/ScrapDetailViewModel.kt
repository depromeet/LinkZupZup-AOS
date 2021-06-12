package com.depromeet.linkzupzup.architecture.presenterLayer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.architecture.domainLayer.MetaUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.extensions.clearMillis
import com.depromeet.linkzupzup.extensions.getTotalTimeSum
import com.depromeet.linkzupzup.receiver.AlarmReceiver
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.webView.WebViewActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ScrapDetailViewModel(private val linkUseCases: LinkUseCases, private val metaUseCases: MetaUseCases, private val alarmManager: AlarmManager): BaseViewModel() {

    private var _isRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    fun setRefresh(refresh: Boolean) {
        _isRefresh.value = refresh
    }

    private var _linkInfo: MutableLiveData<LinkData> = MutableLiveData(LinkData())
    val linkInfo: LiveData<LinkData> = _linkInfo

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

    fun updateLink(linkRegisterEntity: LinkRegisterEntity, callback: ((ResponseEntity<LinkAlarmEntity>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(linkUseCases.updateLink(linkRegisterEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ response ->

                when(response.getStatus()) {
                    StatusConst.UPDATE_SUCCESS_STATUS -> {

                    }
                    else -> {}
                }

                toast(response.comment)
                callback?.invoke(response)
                progressStatus(false)
            }, this@ScrapDetailViewModel::defaultThrowable))
    }

    fun deleteLink(linkId: Int, callback: ((ResponseEntity<LinkAlarmEntity>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(linkUseCases.deleteLink(linkId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ response ->

                when(response.getStatus()) {
                    StatusConst.DELETE_SUCCESS_STATUS -> {

                    }
                    else -> {}
                }

                callback?.invoke(response)
                progressStatus(false)
            }, this@ScrapDetailViewModel::defaultThrowable))
    }


    // 개별 링크 알람 설정
    fun addPersonalLinkAlarm(ctx: Context, date: Calendar) {

        /**
         * requestCode는 향후 알람 취소 시, 알람의 키값으로 사용되며,
         * 관리가 용의하도록 알람을 설정하는 "년월시분초"를 모두 더한 상수값으로 사용하고자 합니다.
         * ex ) year + month + date + hour + minute + second
         */
        val createdDt = Calendar.getInstance()
        val requestCode = createdDt.getTotalTimeSum()
        // 푸시가 도착할 예약시간
        val timeMillis = date.clearMillis().timeInMillis
        val intent = Intent(ctx, AlarmReceiver::class.java).apply {
            // SQLite 테이블 설계 완료 이후 모델을 생성하여 전달하고자 합니다.
            putExtra("linkData", "linkData")
        }
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.time).let {
            DLog.e("addPersonalLinkAlarm", "add time, $it")
        }
        val pendingIntent = PendingIntent.getBroadcast(ctx, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Doze 모드에서 알람이 발생되지 않아, setExactAndAllowWhileIdle에서 setAlarmClock으로 대체
        // alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(timeMillis, pendingIntent), pendingIntent)
    }



    fun moveWebViewPage(linkData: LinkData) {
        getIntent(WebViewActivity::class.java)?.apply {
            putExtra(AppConst.LINK_ID, linkData.linkId)
            putExtra(AppConst.WEB_LINK_URL, linkData.linkURL)
            putExtra(AppConst.WEB_LINK_COMPLETED, linkData.completed)
        }?.let { movePageDelay(it, 300L, false) }
    }

}