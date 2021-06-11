package com.depromeet.linkzupzup.architecture.presenterLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.DonutBadgeEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.DonutHistoryEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.DonutData
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MyDonutViewModel(private val userUseCases: UserUseCases): BaseViewModel() {

    private val _donutResponse: MutableLiveData<DonutHistoryEntity> = MutableLiveData()

    private val _donutList: MutableLiveData<ArrayList<DonutData>> = MutableLiveData(arrayListOf())
    var donutList: LiveData<ArrayList<DonutData>> = _donutList

    fun getDonutHistory(pageNumber: Int = 0, pageSize: Int = 12) {
        defaultParams().apply {
            put(ParamsInfo.PAGE_NUMBER, pageNumber)
            put(ParamsInfo.PAGE_SIZE, pageSize)
        }.let { params ->
            progressStatus(true)
            addDisposable(userUseCases.getDonutHistoryList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->

                    when(response.getStatus()){
                        StatusConst.SELECT_SUSSCESS_STATUS -> {
                            _donutResponse.value = response.data

                            _donutList.value = _donutList.value?.apply {
                                _donutResponse.value?.content?.map { DonutData(it) }?.let { this.addAll(it) }
                            }

                        }

                        else -> { DLog.e("MyDonut",response.comment) }
                    }


                    progressStatus(false)
                }, this@MyDonutViewModel::defaultThrowable))
        }
    }

}