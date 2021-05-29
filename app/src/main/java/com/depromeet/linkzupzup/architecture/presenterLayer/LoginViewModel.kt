package com.depromeet.linkzupzup.architecture.presenterLayer

import android.content.Context
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User

class LoginViewModel(private var userUseCase: UserUseCases) : BaseViewModel() {

    fun kakaoLogin(ctx: Context, callback: (token: OAuthToken?, error: Throwable?) -> Unit = { _,_ -> }) {
        userUseCase.kakaoLogin(ctx) { token, error ->
            if (error != null) DLog.e("UserUseCase", "로그인 실패", error)
            else if (token != null) {
                DLog.e("UserUseCase", "로그인 성공 ${token.accessToken}")
            }
            callback(token, error)
        }
    }

    fun getKakaoUserInfo(callback: (user: User?, error: Throwable?) -> Unit) {
        userUseCase.getKakaoUserInfo { user, error ->
            if (error != null) DLog.e("UserUseCase", "사용자 정보 요청 실패", error)
            else if (user != null) {
                DLog.e("UserUseCase", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            }
            callback(user, error)
        }
    }

}