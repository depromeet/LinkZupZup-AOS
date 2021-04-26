package com.depromeet.linkzupzup.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseView<VIEWMODEL: BaseViewModel> {
    var lifecycleOwner: LifecycleOwner? = null
    var vm: VIEWMODEL? = null
    @Composable
    abstract fun onCreateViewContent()

    fun addDisposable(disposable: Disposable) {
        vm?.addDisposable(disposable)
    }
    fun defaultThrowable(throwable: Throwable) {
        vm?.defaultThrowable(throwable)
    }
}