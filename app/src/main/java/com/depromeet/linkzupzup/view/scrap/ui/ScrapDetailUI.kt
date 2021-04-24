package com.depromeet.linkzupzup.view.scrap.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.presenter.ScrapDetailViewModel
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.utils.DLog

class ScrapDetailUI: BaseView<ScrapDetailViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                BodyContent()
            }
        }
    }

}


@Composable
fun BodyContent() {
    val middleTopPadding = 20.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
    ) {


        // content
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            // top header
            Image(painter = painterResource(id = R.drawable.ic_scrap_detail_top_bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth())

            // body
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
                .absoluteOffset(y = -middleTopPadding)) {

                Image(painter= painterResource(id = R.drawable.ic_scrap_profile_img),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .absoluteOffset(y = middleTopPadding)) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 48.dp)
                        .height(44.dp)) {

                        Text("글쓴이",
                            color = Color.Gray,
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0x878D91)),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .background(Color.White)
                        )

                        Spacer(Modifier.weight(1f))

                        Box(modifier = Modifier
                            .size(44.dp)
                            .fillMaxHeight()
                            .clickable { DLog.e("Jackson", "more click") }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_gray_more),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color.White)
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 48.dp, bottom = 16.dp)) {

                        Text("스타트업과 안맞는 대기업 임원 DNA는 어떻게 찾아낼까?",
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 18.sp, lineHeight = 22.5.sp, color = Color(0xFF292A2B)),
                            modifier = Modifier.fillMaxWidth())

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("IT 기업을 중심으로 빠르게 사업을 실행을 하는 것이 사업의 중요한 경쟁력이 된다는 것은 이미 공감대가 만들어져 있다. 그리고 서바이벌을 고민해야 하는 스타트업에서는 그 중요성은 더욱더 크게 받...",
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF878D91)),
                            modifier = Modifier.fillMaxWidth(), maxLines = 3)

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)) {

                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .absolutePadding(0.dp, 0.dp, 0.dp, 0.dp)) {

                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                                TagView("#디자인", Color(0xFFFFECEC), Color(0xFFE88484))

                                Spacer(Modifier.width(4.dp))

                                TagView("#포트폴리오", Color(0xFFD9F8F4), Color(0xFF57B9AF))

                            }

                        }

                        Spacer(Modifier.weight(1f))

                        Row (
                            Modifier
                                .fillMaxWidth()
                                .height(21.dp)
                                .padding(top = 5.dp)
                                .clickable {
                                    DLog.e(
                                        "Jackson",
                                        "click link alram setting!"
                                    )
                                }) {

                            Image(painter = painterResource(id = R.drawable.ic_link_alram_img),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp))

                            Spacer(Modifier.width(4.dp))

                            Text("이 링크는 따로 알람을 받고싶어요!",
                                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .clickable { DLog.e("Jackson", "click setting link alram ") })

                        }

                        Spacer(Modifier.height(20.dp))

                        Button(onClick = { DLog.e("Jackson", "click read button") },
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue,
                                contentColor = Color.White),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)) {
                            Text("바로 읽기!",
                                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W700)), fontSize = 14.sp, lineHeight = 17.5.sp),
                                textAlign = TextAlign.Center)
                        }


                    }
                }
            }
        }


        // close
        Box(modifier= Modifier
            .padding(16.dp)
            .align(Alignment.TopEnd)
            .clickable { DLog.e("Jackson", "Click close button") }) {

            Image(
                painter = painterResource(id = R.drawable.ic_gray_close),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
            )
        }
    }
}

@Composable
fun TagView(tagStr: String, backgroundColor: Color, color: Color) {
    Card(Modifier.clickable { DLog.e("Jackson", "click, Tag")  },
        shape = RoundedCornerShape(2.dp),
        elevation = 0.dp,
        backgroundColor = backgroundColor) {
        Column(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)) {
            Text(tagStr,
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 2.sp, color = Color(0xFFE88484)),
                color = color,
                modifier = Modifier
                    .height(12.dp)
                    .absolutePadding(0.dp, 0.dp, 0.dp, 0.dp), maxLines = 1)

        }
    }
}

@Preview
@Composable
fun previewSample() {
    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            BodyContent()
        }
    }
}