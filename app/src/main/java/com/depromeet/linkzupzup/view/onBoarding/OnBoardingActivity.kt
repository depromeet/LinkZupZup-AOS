package com.depromeet.linkzupzup.view.onBoarding

import android.os.Bundle
import com.depromeet.linkzupzup.architecture.presenterLayer.OnBoardingViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.onBoarding.ui.OnBoardingUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class OnBoardingActivity : BaseActivity<OnBoardingUI, OnBoardingViewModel>()  {

    override var view: OnBoardingUI = OnBoardingUI()
    override fun onCreateViewModel(): OnBoardingViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }

}