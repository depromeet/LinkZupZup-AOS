package com.depromeet.linkzupzup.base

import android.app.Activity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.dynamic.SupportFragmentWrapper
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.AnkoComponent

abstract class BaseAnkoView<VIEWMODEL: BaseViewModel>: AnkoComponent<Activity> {
    var lifecycleOwner: LifecycleOwner? = null
    var fragmentManager: FragmentManager? = null
    //lateinit var supportFragmentManager: SupportFrag
    var vm: VIEWMODEL? = null

    fun addDisposable(disposable: Disposable) {
        vm?.addDisposable(disposable)
    }
    fun defaultThrowable(throwable: Throwable) {
        vm?.defaultThrowable(throwable)
    }
}