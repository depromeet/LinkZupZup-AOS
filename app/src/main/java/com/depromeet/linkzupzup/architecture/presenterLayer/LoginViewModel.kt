package com.depromeet.linkzupzup.architecture.presenterLayer

import android.content.Context
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.kakao.sdk.auth.model.OAuthToken

class LoginViewModel(private var userUseCase: UserUseCases) : BaseViewModel() {

    fun kakaoLogin(ctx: Context, callback: (token: OAuthToken?, error: Throwable?) -> Unit = { _,_ -> }) {
        userUseCase.kakaoLogin(ctx) { token, error ->
            if (error != null) {
                DLog.e("UserUseCase", "로그인 실패", error)
            }
            else if (token != null) {
                DLog.e("UserUseCase", "로그인 성공 ${token.accessToken}")
            }
            callback(token, error)
        }
    }

}