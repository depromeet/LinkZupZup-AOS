package com.depromeet.linkzupzup.architecture.presenterLayer

import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MyDonutViewModel(private val userUseCases: UserUseCases): BaseViewModel() {

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

                    

                    progressStatus(false)
                }, this@MyDonutViewModel::defaultThrowable))
        }
    }

}