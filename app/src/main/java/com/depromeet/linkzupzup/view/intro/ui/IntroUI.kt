package com.depromeet.linkzupzup.view.intro.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.IntroViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.ui.theme.Gray10
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme


class IntroUI : BaseView<IntroViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Gray10) {
                IntroBody()
            }
        }
    }
}

@Composable
fun IntroBody(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            modifier = Modifier.size(94.dp).align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_jubjub),
            contentDescription = null)
    }
}


@Preview
@Composable
fun IntroPreview(){
    LinkZupZupTheme {
        Surface(color = Gray10) {
            IntroBody()
        }
    }
}
