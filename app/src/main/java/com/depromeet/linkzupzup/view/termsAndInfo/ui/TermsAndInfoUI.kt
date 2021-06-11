package com.depromeet.linkzupzup.view.termsAndInfo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.depromeet.linkzupzup.architecture.presenterLayer.TermsAndInfoViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme

class TermsAndInfoUI: BaseView<TermsAndInfoViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {

            }
        }
    }


}