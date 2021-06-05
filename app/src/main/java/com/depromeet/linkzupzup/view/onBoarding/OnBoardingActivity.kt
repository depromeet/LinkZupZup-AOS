package com.depromeet.linkzupzup.view.onBoarding

import android.os.Bundle
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.OnBoardingViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.login.LoginActivity
import com.depromeet.linkzupzup.view.main.MainActivity
import com.depromeet.linkzupzup.view.onBoarding.ui.OnBoardingUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class OnBoardingActivity : BaseActivity<OnBoardingUI, OnBoardingViewModel>()  {

    override var view: OnBoardingUI = OnBoardingUI()
    override fun onCreateViewModel(): OnBoardingViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.stay, R.anim.stay)

        if (isLogin()) movePageDelay(MainActivity::class.java, 2000, true)
        else movePageDelay(LoginActivity::class.java, 2000, true)
    }

    override fun onBackPressed() { /* super.onBackPressed() */ }

}