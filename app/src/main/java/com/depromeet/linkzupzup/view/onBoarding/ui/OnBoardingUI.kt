package com.depromeet.linkzupzup.view.onBoarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.OnBoardingViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.ui.theme.WhiteOp85

class OnBoardingUI  : BaseView<OnBoardingViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface {
                Box(modifier = Modifier.fillMaxSize()){
                    OnBoardingBody()
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    OnBoardingContent()
                }

            }
        }
    }
}

@Composable
fun OnBoardingBody(){
    Image(
        painter = painterResource(id = R.drawable.img_onboarding_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopStart)
}

@Composable
fun OnBoardingContent(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 182.dp)
        .background(WhiteOp85)) {
        PushCard(
            title = "출퇴근, 짜투리 시간에 줍줍한 링크를!",
            content = "또 유투브보려고? 출근할 때 읽기로 했잖아 \uD83D\uDE22",
            time = "지금")

        PushCard(
            title = "개발과 디자인 사이의 차이를 어떻게 좁힐...",
            content = "웹툰 또 보게? 오늘은 이거 읽을거라며!",
            time = "20분 전",
            painter = painterResource(id = R.drawable.ic_link_profile_img))
    }
}

@Composable
fun PushCard(title : String, content : String, time : String, painter: Painter? = null){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 14.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)){

            Image(
                painter = painterResource(id = R.drawable.ic_jubjub),
                contentDescription = null,
                modifier = Modifier.size(20.dp))

            Text(buildAnnotatedString {

                withStyle(style = SpanStyle(
                            fontSize = 13.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(
                                        resId = R.font.spoqa_hansansneo_regular,
                                        weight = FontWeight.W500)))
                ){
                    append("줍줍 ")
                }

                withStyle(style = SpanStyle(
                    fontSize = 13.sp,
                    color = Color(0xff7A7A7A),
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W500)))
                ){
                    append("· $time")
                }

            })

        }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier= Modifier
            .fillMaxWidth()
            .padding(top = 9.dp, start = 1.dp, end = 2.dp)){
            Column(modifier = Modifier.weight(1f)) {
                Text(title,
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))

                Spacer(modifier = Modifier.height(5.dp))

                Text(content,
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        color = Color(0xff7A7A7A),
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500))))
            }

            painter?.let{
                Row(horizontalArrangement = Arrangement.End,
                    modifier = Modifier.width(50.dp)){

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.size(42.dp))
                }
            }
        }
    }
    Divider(color = Color(0xffCFCFCF), thickness = 1.dp)
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

@Preview
@Composable
fun PushCardPreview(){
    Surface(color = WhiteOp85) {
        PushCard(
            title = "개발과 디자인 사이의 차이를 어떻게 좁힐...",
            content = "웹툰 또 보게? 오늘은 이거 읽을거라며!",
            time = "20분 전",
            painter = painterResource(id = R.drawable.ic_link_profile_img))
    }

}