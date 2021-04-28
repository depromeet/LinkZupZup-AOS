package com.depromeet.linkzupzup.view.alarm

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.presenter.AlarmDetailViewModel
import com.depromeet.linkzupzup.view.alarm.ui.AlarmDetailUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AlarmDetailActivity : BaseActivity<AlarmDetailUI, AlarmDetailViewModel>() {

    override var view: AlarmDetailUI = AlarmDetailUI()
    override fun onCreateViewModel(): AlarmDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}