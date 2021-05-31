package com.depromeet.linkzupzup.architecture.domainLayer

import android.content.Context
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.UserRepositoryImpl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignUpEntity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import io.reactivex.Observable

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

    fun getKakaoUserInfo(callback: (user: User?, error: Throwable?) -> Unit) {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me(callback = callback)
    }

    fun signIn(signInEntity: SignInEntity): Observable<SignResponseEntity> {
        return userRepositoryImpl.signIn(signInEntity)
    }

    fun signUp(signUp: SignUpEntity): Observable<SignResponseEntity> {
        return userRepositoryImpl.signUp(signUp)
    }

}