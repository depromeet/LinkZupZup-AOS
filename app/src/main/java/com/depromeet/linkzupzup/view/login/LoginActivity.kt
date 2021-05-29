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

    private fun oauthClickListener(type: String) = with(viewModel) {
        when (type) {
            AppConst.OAUTH_KAKAO_TYPE -> {
                kakaoLogin(this@LoginActivity) { token, loginError ->
                    if (token != null) getKakaoUserInfo { user, meError ->
                        user?.run {
//                            id
//                            kakaoAccount?.email
//                            kakaoAccount?.profile?.nickname

                            /**
                             * 일단 납득은 안가지만 아래순으로 API를 호출해야될것같음
                             * 1. 이메일 아이디 찾기 API ( 가입유무 판별 )
                             * 2. 가입된 유저의 경우 로그인 API 호출
                             * 3. 가입되지 않은 유저의 경우, 회원가입 API 호출 이후 로그인 API 호출
                             *
                             * 아직 가입 유무 API를 개발 진행중이라 향후 로직 개발
                             */
                        }
                    }
                }
            }
        }
    }
}