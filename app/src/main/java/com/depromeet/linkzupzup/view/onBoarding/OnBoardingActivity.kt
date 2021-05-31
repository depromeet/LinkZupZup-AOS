package com.depromeet.linkzupzup.view.onBoarding

import android.content.Intent
import android.os.Bundle
import com.depromeet.linkzupzup.architecture.presenterLayer.OnBoardingViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.main.MainActivity
import com.depromeet.linkzupzup.view.onBoarding.ui.OnBoardingUI
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit

class OnBoardingActivity : BaseActivity<OnBoardingUI, OnBoardingViewModel>()  {

    override var view: OnBoardingUI = OnBoardingUI()
    override fun onCreateViewModel(): OnBoardingViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Observable.timer(2, TimeUnit.SECONDS)
//            .subscribe(this::nextPage)
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }

    private fun nextPage(time: Long) {
        Intent(this, MainActivity::class.java)
            .let(this::startActivity)
        finish()
    }

}