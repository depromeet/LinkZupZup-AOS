package com.depromeet.linkzupzup.view.main

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.main.ui.MainUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainUI, MainViewModel>() {

    override var view: MainUI = MainUI()
    override fun onCreateViewModel(): MainViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel) {

            getLinkList()

            linkAlarmResponse.observe(this@MainActivity) {
                val status = Integer.parseInt(it.status)
                if (status in 200..299) {
                    DLog.e("TEST", "status: $status")

                    it.data?.let { data ->
                        DLog.e("TEST", "content size: ${data.content.size}")
                    }

                } else {
                    DLog.e("TEST", it.comment)
                }
            }

        }

    }

}