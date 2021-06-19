package com.depromeet.linkzupzup.base

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.AnkoComponent

abstract class BaseAnkoView<VIEWMODEL: BaseViewModel>: AnkoComponent<Activity> {
    var lifecycleOwner: LifecycleOwner? = null
    var vm: VIEWMODEL? = null

    fun addDisposable(disposable: Disposable) {
        vm?.addDisposable(disposable)
    }
    fun defaultThrowable(throwable: Throwable) {
        vm?.defaultThrowable(throwable)
    }
}