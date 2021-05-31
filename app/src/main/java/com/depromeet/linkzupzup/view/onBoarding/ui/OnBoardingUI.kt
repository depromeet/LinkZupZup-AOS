package com.depromeet.linkzupzup.view.onBoarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.OnBoardingViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.ui.theme.Gray10
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme

class OnBoardingUI  : BaseView<OnBoardingViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface {
                Box(modifier = Modifier.fillMaxSize()){
                    OnBoardingBody()
                }

            }
        }
    }
}

@Composable
fun OnBoardingBody(){
    Image(
        painter = painterResource(id = R.drawable.ic_onboarding_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopStart)
}


@Preview
@Composable
fun OnBoardingPreview(){
    LinkZupZupTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()){
                OnBoardingBody()
            }

        }
    }
}