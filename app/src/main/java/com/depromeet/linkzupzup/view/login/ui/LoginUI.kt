package com.depromeet.linkzupzup.view.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.LoginViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.ui.theme.Gray100t
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.ui.theme.Round8RectShape


class LoginUI(var loginClickListener: (String) -> Unit = {}): BaseView<LoginViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                LoginBody(loginClickListener)
            }
        }
    }

    @Composable
    fun LoginBody(loginClickListener: (String) -> Unit = {}) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White)) {

            // 로그인 배경 이미지
            Image(painter = painterResource(id = R.drawable.img_login_bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomStart))

            // 좌측 상단
            Column(Modifier.padding(start = 32.dp, top = 54.dp)) {

                Image(painter = painterResource(id = R.drawable.ic_small_logo_title),
                    contentDescription = null,
                    modifier = Modifier.size(84.dp, 32.dp))

                Spacer(Modifier.height(11.dp))

                Text("링크 스크랩\n리마인더\n줍줍",
                    style = TextStyle (
                        fontSize = 28.sp,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W400)),
                        color = Color.Black,
                    letterSpacing = (-0.08).sp
                    ))

            }

            Column(modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                .align(Alignment.BottomCenter)) {

                Card(shape = Round8RectShape,
                    elevation = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                        .height(64.dp)
                        .clickable { loginClickListener(AppConst.OAUTH_KAKAO_TYPE) }) {

                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                            .background(Color(0xFFFBEA4E))) {

                        Image(painter = painterResource(id = R.drawable.ic_kakao_logo),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp))

                        Spacer(Modifier.width(10.dp))

                        Text("카카오 계정으로 로그인",
                            style = TextStyle (
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)),
                                color = Gray100t))

                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun LoginPreview() {
        LoginBody()
    }
}