package com.depromeet.linkzupzup.view.alarm

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.AlarmDetailViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.alarm.ui.AlarmDetailUI
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AlarmDetailActivity : BaseActivity<AlarmDetailUI, AlarmDetailViewModel>() {

    override var view: AlarmDetailUI = AlarmDetailUI()
    override fun onCreateViewModel(): AlarmDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            getAlarmList {
                DLog.e("AlarmDetail", "${Gson().toJson(it)}")
            }
        }
    }

}