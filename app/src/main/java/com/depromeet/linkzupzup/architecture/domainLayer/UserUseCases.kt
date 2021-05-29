package com.depromeet.linkzupzup.architecture.domainLayer

import android.content.Context
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.UserRepositoryImpl
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class UserUseCases(private val userRepositoryImpl: UserRepositoryImpl) {

    // Kakao Login
    fun kakaoLogin(ctx: Context, callback: (token: OAuthToken?, error: Throwable?) -> Unit) {

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(ctx)) {
            UserApiClient.instance.loginWithKakaoTalk(context = ctx, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context = ctx, callback = callback)
        }
    }

}