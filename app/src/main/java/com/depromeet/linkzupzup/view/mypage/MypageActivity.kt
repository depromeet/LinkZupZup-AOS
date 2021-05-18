package com.depromeet.linkzupzup.view.mypage

import android.os.Bundle
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.presenter.MypageViewModel
import com.depromeet.linkzupzup.view.mypage.ui.MypageUI
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MypageActivity: BaseActivity<MypageUI, MypageViewModel>() {
    override var view: MypageUI = MypageUI()
    override fun onCreateViewModel(): MypageViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}