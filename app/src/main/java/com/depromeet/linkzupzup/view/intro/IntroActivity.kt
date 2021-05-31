package com.depromeet.linkzupzup.view.intro

import android.content.Intent
import android.os.Bundle
import com.depromeet.linkzupzup.architecture.presenterLayer.IntroViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.service.FCMService
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.intro.ui.IntroUI
import com.depromeet.linkzupzup.view.onBoarding.OnBoardingActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit

class IntroActivity : BaseActivity<IntroUI, IntroViewModel>()  {

    override var view: IntroUI = IntroUI()
    override fun onCreateViewModel(): IntroViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getFCMToken()

        Observable.timer(2, TimeUnit.SECONDS)
            .subscribe(this::nextPage)
    }

    private fun getFCMToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val token = it.result
            token?.let{
                pref.setFCMToken(token)
                DLog.d("FCM Token from fcm", token)
                DLog.d("FCM Token from pref",pref.getFCMToken())
            }

        }
    }

    private fun nextPage(time: Long) {
        Intent(this,OnBoardingActivity::class.java)
            .let(this::startActivity)
        finish()
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }

}