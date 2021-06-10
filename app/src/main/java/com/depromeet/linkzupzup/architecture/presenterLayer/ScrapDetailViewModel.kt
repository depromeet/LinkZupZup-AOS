package com.depromeet.linkzupzup.architecture.presenterLayer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.architecture.domainLayer.MetaUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.extensions.clearMillis
import com.depromeet.linkzupzup.extensions.getTotalTimeSum
import com.depromeet.linkzupzup.architecture.presenterLayer.model.TagColor
import com.depromeet.linkzupzup.extensions.mapToPresenter
import com.depromeet.linkzupzup.receiver.AlarmReceiver
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.Single
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get
import java.text.SimpleDateFormat
import java.util.*

class ScrapDetailViewModel(private val metaUseCases: MetaUseCases): BaseViewModel() {

    private val alarmManager: AlarmManager by lazy { get(AlarmManager::class.java) }

    private var _metaInfo: MutableLiveData<LinkData> = MutableLiveData(LinkData())
    val metaInfo: LiveData<LinkData> = _metaInfo

    fun getTagList(): ArrayList<String> {
        return ArrayList(metaInfo.value?.hashtags?.map { it.hashtagName } ?: arrayListOf())
    }

    fun getRandomColor(): Single<TagColor> {
        return Single.just(CommonUtil.getRandomeTagColor())
    }

    fun getMetaInfo(linkUrl: String) {
        viewModelScope.launch {
            metaUseCases.getMetaData(linkUrl = linkUrl)?.let { meta ->
                _metaInfo.value = meta.mapToPresenter()
            }
        }
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

}