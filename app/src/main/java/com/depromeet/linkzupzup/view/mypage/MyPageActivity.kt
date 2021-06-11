package com.depromeet.linkzupzup.view.mypage

import android.os.Bundle
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.MyPageViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.mypage.ui.MyPageUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MyPageActivity: BaseActivity<MyPageUI, MyPageViewModel>() {

    override var view: MyPageUI = MyPageUI(this::onClickListener)
    override fun onCreateViewModel(): MyPageViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            // 마이페이지 정보 조회
            getMyPageInfo()
        }
    }

    private fun onClickListener(id: Int) {
        when (id) {
            R.id.activity_close -> onBackPressed()
            else -> {}
        }
    }

}