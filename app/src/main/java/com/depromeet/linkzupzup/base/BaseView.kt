package com.depromeet.linkzupzup.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner

abstract class BaseView<VIEWMODEL: BaseViewModel> {
    var lifecycleOwner: LifecycleOwner? = null
    var vm: VIEWMODEL? = null
    @Composable
    abstract fun onCreateViewContent()
}