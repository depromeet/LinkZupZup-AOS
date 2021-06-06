package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.AlarmUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmRegistEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmUpdateEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.extensions.mapToDataLayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AlarmDetailViewModel(private val alarmUseCases: AlarmUseCases): BaseViewModel() {

    private var _alarmList: MutableLiveData<ArrayList<WeeklyAlarm>> = MutableLiveData(arrayListOf())
    val alarmList: LiveData<ArrayList<WeeklyAlarm>> = _alarmList

    /**
     * 어플 알람 리스트 조회
     */
    fun getAlarmList(callback: ((ResponseEntity<ArrayList<AlarmEntity>>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(alarmUseCases.getAlarmList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({

                when (it.getStatus()) {
                    StatusConst.SELECT_SUSSCESS_STATUS -> {
                        _alarmList.value = it.data?.mapToDataLayer()

                        // TODO: 로직 추가 필요
                        callback?.invoke(it)
                    }
                }

                progressStatus(false)
            }, this@AlarmDetailViewModel::defaultThrowable))
    }

    /**
     * 어플 알람 등록
     */
    fun registAlarm(alarmInfo: AlarmUpdateEntity, callback: ((ResponseEntity<AlarmRegistEntity>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(alarmUseCases.registAlarm(alarmInfo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({

                // TODO: 로직 추가 필요
                callback?.invoke(it)
                progressStatus(false)
            }, this@AlarmDetailViewModel::defaultThrowable))
    }

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    fun getAlarmDetail(alarmId: Int, callback: ((ResponseEntity<AlarmEntity>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(alarmUseCases.getAlarmDetail(alarmId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({

                // TODO: 로직 추가 필요
                callback?.invoke(it)
                progressStatus(false)
            }, this@AlarmDetailViewModel::defaultThrowable))
    }

    /**
     * 특정 어플 알람 수정
     */
    fun updateAlarm(alarmId: Int, alarmInfo: AlarmUpdateEntity, callback: ((ResponseEntity<AlarmEntity>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(alarmUseCases.updateAlarm(alarmId, alarmInfo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({

                // TODO: 로직 추가 필요
                callback?.invoke(it)
                progressStatus(false)
            }, this@AlarmDetailViewModel::defaultThrowable))
    }

    /**
     * 특정 어플 알람 삭제
     */
    fun deleteAlarm(alarmId: Int, callback: ((ResponseEntity<String?>)->Unit)? = null) {
        progressStatus(true)
        addDisposable(alarmUseCases.deleteAlarm(alarmId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({

                // TODO: 로직 추가 필요
                callback?.invoke(it)
                progressStatus(false)
            }, this@AlarmDetailViewModel::defaultThrowable))
    }

}