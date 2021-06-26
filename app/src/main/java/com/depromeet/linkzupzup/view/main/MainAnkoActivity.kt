package com.depromeet.linkzupzup.view.main

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.base.BaseAnkoActivity
import com.depromeet.linkzupzup.component.ListDecoration
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.view.main.ui.MainAnkoUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainAnkoActivity : BaseAnkoActivity<MainAnkoUI, MainViewModel>() {

    override var view: MainAnkoUI = MainAnkoUI()
    override fun onCreateViewModel(): MainViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}