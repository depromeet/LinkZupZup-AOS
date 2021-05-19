package com.depromeet.linkzupzup.view.mydonut

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.presenter.MyDonutViewModel
import com.depromeet.linkzupzup.view.mydonut.ui.MyDonutUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MyDonutActivity : BaseActivity<MyDonutUI, MyDonutViewModel>() {

    override var view: MyDonutUI = MyDonutUI()
    override fun onCreateViewModel(): MyDonutViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}