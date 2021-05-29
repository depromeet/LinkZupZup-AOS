package com.depromeet.linkzupzup.view.intro

import android.os.Bundle
import com.depromeet.linkzupzup.architecture.presenterLayer.IntroViewModel
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.view.intro.ui.IntroUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class IntroActivity : BaseActivity<IntroUI, IntroViewModel>()  {

    override var view: IntroUI = IntroUI()
    override fun onCreateViewModel(): IntroViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}