package com.depromeet.linkzupzup.view.login

import android.os.Bundle
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.architecture.presenterLayer.LoginViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.login.ui.LoginUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginActivity : BaseActivity<LoginUI, LoginViewModel>() {

    override var view: LoginUI = LoginUI(this::oauthClickListener)
    override fun onCreateViewModel(): LoginViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun oauthClickListener(type: String) {
        when (type) {
            AppConst.OAUTH_KAKAO_TYPE -> {
                viewModel.kakaoLogin(this@LoginActivity)
            }
        }
    }
}