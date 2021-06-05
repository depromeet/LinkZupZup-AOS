package com.depromeet.linkzupzup.view.intro

import android.os.Bundle
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.IntroViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.intro.ui.IntroUI
import com.depromeet.linkzupzup.view.onBoarding.OnBoardingActivity
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.getViewModel

class IntroActivity : BaseActivity<IntroUI, IntroViewModel>()  {

    override var view: IntroUI = IntroUI()
    override fun onCreateViewModel(): IntroViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.stay, R.anim.stay)

        getFCMToken()

        movePageDelay(OnBoardingActivity::class.java, 2000, true)

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

    override fun onBackPressed() { /* super.onBackPressed() */ }

}