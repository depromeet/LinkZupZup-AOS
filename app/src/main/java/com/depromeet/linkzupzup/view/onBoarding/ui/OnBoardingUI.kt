package com.depromeet.linkzupzup.view.onBoarding.ui

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.depromeet.linkzupzup.architecture.presenterLayer.OnBoardingViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.ui.theme.Gray10
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme

class OnBoardingUI  : BaseView<OnBoardingViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Gray10) {
                Text(text = "onBoard")
            }
        }
    }
}