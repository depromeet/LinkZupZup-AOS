package com.depromeet.linkzupzup.view.main

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.view.main.ui.MainUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainUI, MainViewModel>() {

    override var view: MainUI = MainUI()
    override fun onCreateViewModel(): MainViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            // 링크 리스트 호출
            getLinkList()
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        backPressHandler.onBackPressed()
    }
}