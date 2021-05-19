package com.depromeet.linkzupzup.view.mypage

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.presenter.MyPageViewModel
import com.depromeet.linkzupzup.view.mypage.ui.MyPageUI
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MyPageActivity: BaseActivity<MyPageUI, MyPageViewModel>() {
    override var view: MyPageUI = MyPageUI()
    override fun onCreateViewModel(): MyPageViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}