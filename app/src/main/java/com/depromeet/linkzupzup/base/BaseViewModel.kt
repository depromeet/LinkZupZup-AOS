package com.depromeet.linkzupzup.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.depromeet.linkzupzup.component.PreferencesManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    var preference: PreferencesManager? = null
    var lifecycleOwner: LifecycleOwner? = null

    private val _progressStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressStatus: MutableLiveData<Boolean>
        get() = _progressStatus

    private val _keyboardStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    val keyboardStatus: MutableLiveData<Boolean>
        get() = _keyboardStatus

    // progress 노출 여부 제어
    fun progressStatus(state: Boolean) {
        progressStatus.value = state
    }

    // keyboard 노출 여부 제어
    fun keyboardStatus(isShow: Boolean) {
        keyboardStatus.value = isShow
    }

    /**
     * ViewModel의 Lifecycle이 끝날경우 구독에 대한 참조를 끊기위한 처리
     */
    private val disposables: CompositeDisposable = CompositeDisposable()
    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}