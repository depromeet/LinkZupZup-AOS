package com.depromeet.linkzupzup.view.main

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.presenter.MainViewModel
import com.depromeet.linkzupzup.view.main.ui.MainUI
import com.depromeet.linkzupzup.view.main.ui.MainUI2
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainUI, MainViewModel>() {

    override var view: MainUI = MainUI()
    override fun onCreateViewModel(): MainViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}