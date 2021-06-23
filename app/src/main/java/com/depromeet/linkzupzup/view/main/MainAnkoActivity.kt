package com.depromeet.linkzupzup.view.main

import android.os.Bundle
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.base.BaseAnkoActivity
import com.depromeet.linkzupzup.view.main.ui.MainAnkoUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainAnkoActivity : BaseAnkoActivity<MainAnkoUI, MainViewModel>() {

    override var view: MainAnkoUI = MainAnkoUI()
    override fun onCreateViewModel(): MainViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}