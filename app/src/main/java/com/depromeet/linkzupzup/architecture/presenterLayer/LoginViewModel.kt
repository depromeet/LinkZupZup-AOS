package com.depromeet.linkzupzup.architecture.presenterLayer

import android.content.Context
import com.depromeet.linkzupzup.StatusConst
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInUpEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    fun signInUp(signInUpEntity: SignInUpEntity, callback: (Int, ResponseEntity<SignResponseEntity>) ->Unit): Observable<ResponseEntity<SignResponseEntity>> {

        progressStatus(true)
        addDisposable(userUseCase.signInUp(signInUpEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->

                val status = response.getStatus()
                when(status) {
                    StatusConst.REGIST_SUCCESS_STATUS -> {
                        // 회원가입, 재호출 하여 로그인 처리

                        val _authorization = response.data?.token ?: ""
                        val _userId = response.data?.userId ?: 0
                        preference?.run {
                            setAuthorization(_authorization)
                            setUserId(_userId)
                        }
                        signInUp(signInUpEntity.apply {
                            token = _authorization
                            userId = _userId
                        }, callback)
                    }
                    StatusConst.SELECT_SUSSCESS_STATUS -> {
                        // 로그인 성공
                        preference?.setAuthorization(response.data?.token ?: "")
                        preference?.setUserId(response.data?.userId ?: 0)
                        callback(status, response)
                    }
                    else -> {
                        DLog.e("signInUp", response.comment)
                        callback(status, response)
                    }
                }
                progressStatus(false)
            }, this@LoginViewModel::defaultThrowable))

        return userUseCase.signInUp(signInUpEntity)
    }

}