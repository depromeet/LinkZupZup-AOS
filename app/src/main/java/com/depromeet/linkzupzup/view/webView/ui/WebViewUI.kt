package com.depromeet.linkzupzup.view.webView.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.presenter.WebViewViewModel
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.view.mydonut.ui.MyDonutTopBar
import kotlinx.coroutines.launch

class WebViewUI : BaseView<WebViewViewModel>() {
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                Column(modifier = Modifier.fillMaxSize()) {
                    val openDialog = remember { mutableStateOf(false)  }

                    WebPageScreen(urlToRender = "https://www.google.com", openDialog = openDialog)
                    WebViewCustomDialog(openDialog = openDialog)
                }
            }
        }
    }
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebPageScreen(urlToRender: String, openDialog: MutableState<Boolean>) {
    Scaffold(
        topBar = { WebViewTopBar(openDialog = openDialog) },
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()) {

        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(urlToRender)
            }
        }, update = {
            it.loadUrl(urlToRender)
        })
    }

}


@Composable
fun WebViewTopBar(openDialog: MutableState<Boolean>){
    val ctx = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .background(Color.Transparent)){

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()){

            WebViewBackButton(painterResource(id = R.drawable.ic_detail_back)){
                // finish activity
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)){

            Text(text = "\uD83D\uDC40 다 읽었다면?",
                style = TextStyle(fontSize = 14.sp,
                    lineHeight = 12.sp,
                    color = Gray100t,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500))))

            Button(shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
                modifier = Modifier
                    .width(60.dp)
                    .height(36.dp),
                onClick = { openDialog.value = true }) {

                Text("완료!",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 17.5.sp,
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))
            }
        }
    }


}

@Composable
fun WebViewBackButton(painter: Painter, onClick : () -> Unit){
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .noRippleClickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.size(56.dp)) {

            Image(painter = painter,
                contentDescription = null,
                Modifier.size(24.dp))

        }
    }
}

@Composable
fun WebViewCustomDialog(openDialog : MutableState<Boolean>){

    if(openDialog.value){
        AlertDialog(
            modifier = Modifier
                .height(293.dp)
                .width(263.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            onDismissRequest = { openDialog.value = false },
            title = { DialogBody(234) },
            confirmButton = {

                Button(shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    onClick = { openDialog.value = false }) {

                    Text("확인",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 17.5.sp,
                            fontFamily = FontFamily(Font(
                                resId = R.font.spoqa_hansansneo_bold,
                                weight = FontWeight.W700))))
                }
            }
        )
    }

}


@Composable
fun DialogBody(readCount : Int){

    val thisWeekCount : String = "이번 달 읽은 링크 ${readCount}개"

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()) {

        Image(painter = painterResource(id = R.drawable.ic_donut05),
            contentDescription = null,
            modifier = Modifier
                .size(124.dp, 92.dp)
                .padding(bottom = 20.dp))

        Text(text ="\uD83D\uDD25 읽기 완료!",
            modifier = Modifier.padding(bottom=8.dp),
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 22.5.sp,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_bold,
                    weight = FontWeight.W700))))

        val thisWeekCountStr = AnnotatedString(
            text = thisWeekCount,
            spanStyles = listOf(
                AnnotatedString.Range(
                    start = 11,
                    end = thisWeekCount.length,
                    item = SpanStyle(color = Gray70,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))
            )
        )
        Text(thisWeekCountStr,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.8.sp,
                color= Gray70,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W700))))
    }

}

@Preview
@Composable
fun PreviewDialogBody(){
    Surface(Modifier.background(Color.White)) {
        DialogBody(readCount = 234)
    }
}