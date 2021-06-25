package com.depromeet.linkzupzup.view.webView.ui

import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.architecture.presenterLayer.WebViewViewModel
import com.depromeet.linkzupzup.extensions.mutableStateValue
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.DLog

class WebViewUI(var clickListener: (Int)->Unit = {}) : BaseView<WebViewViewModel>() {

    var webView: WebView? = null

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                Column(modifier = Modifier.fillMaxSize()) {
                    val openDialog = remember { mutableStateOf(false)  }

                    vm?.let { viewModel ->
                        val linkId by viewModel.linkId.observeAsState(-1)
                        val linkUrl = viewModel.linkUrl.value ?: "https://www.naver.com"
                        val readCount by viewModel.todayReadCnt.observeAsState(0)
                        val isCompleted by viewModel.isCompleted.observeAsState(false)

                        val completed = remember { mutableStateOf(isCompleted) }

                        Scaffold(
                            backgroundColor = Color.Transparent,
                            modifier = Modifier.fillMaxSize(),
                            topBar = { WebViewTopBar(completed, clickListener = clickListener){
                                viewModel.setLinkRead(linkId = linkId){
                                    openDialog.value = true
                                } }
                            }) {

                            AndroidView(factory = {
                                WebView(it).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    webViewClient = WebViewClient()
                                    settings.apply {
                                        javaScriptEnabled = true    // 자바스크립트 실행 허용
                                        javaScriptCanOpenWindowsAutomatically = false   // 자바스크립트에서 새창 실행 허용
                                        setSupportMultipleWindows(false)    // 새 창 실행 허용
                                        loadWithOverviewMode = true // 메타 태그 허용
                                        useWideViewPort = true  // 화면 사이즈 맞추기 허용
                                        setSupportZoom(true)    // 화면 줌 허용
                                        builtInZoomControls = false // 화면 확대 축소 허용 여부
                                        domStorageEnabled = true    // 로컬저장소 허용
                                    }

                                    webView = this
                                    DLog.e("loadUrl", "link: $linkUrl")
                                    postDelayed({ loadUrl(linkUrl) }, 300)
                                }
                            }, update = {
                                DLog.e("loadUrl", "update, link: $linkUrl")
                                it.postDelayed({ it.loadUrl(linkUrl) }, 300)
                            })
                        }

                        WebViewCustomDialog(viewModel, readCount = readCount, openDialog = openDialog, completed)

                    }
                }
            }
        }
    }
}


@Composable
fun WebViewTopBar(isCompleted: MutableState<Boolean>, clickListener: (Int) -> Unit, onReadClick: () -> Unit){

    val readBtnColor = if (!isCompleted.value) Blue50 else Gray50t
    val readTxtColor = if (!isCompleted.value) Color.White else Gray70
    val readBtnEnabled = !isCompleted.value


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .background(Color.Transparent)){

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()){

            WebViewBackButton(painterResource(id = R.drawable.ic_detail_back)){
                clickListener(R.id.activity_close)
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
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = readBtnColor, contentColor = readTxtColor),
                modifier = Modifier
                    .width(60.dp)
                    .height(36.dp),
                enabled = readBtnEnabled,
                onClick = {
                    onReadClick()
                }) {

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
fun WebViewCustomDialog(vm:WebViewViewModel, readCount: Int, openDialog : MutableState<Boolean>, isCompleted: MutableState<Boolean>){
    if (openDialog.value) CustomDialog(readCount = readCount){
        openDialog.value = false
        vm.setIsCompleted(true)
        isCompleted.value = true
    }
}

@Composable
fun CustomDialog( readCount: Int, onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = onDismissRequest) {
        DialogBody(readCount, onDismissRequest)
    }
}


@Composable
fun DialogBody(readCount : Int, onClick: () -> Unit){

    val thisWeekCount = "이번 달 읽은 링크 ${readCount}개"

    Card(shape = Shapes.small,
        modifier = Modifier
            .height(293.dp)
            .width(263.dp)
            .background(Color.Transparent)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)){

            Spacer(modifier = Modifier.height(24.dp))

            Image(painter = painterResource(id = R.drawable.ic_donut05),
                contentDescription = null,
                modifier = Modifier
                    .size(124.dp, 92.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Text(text ="\uD83D\uDD25 읽기 완료!",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 22.5.sp,
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_bold,
                        weight = FontWeight.W700))))

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(24.dp))

            Button(shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = onClick) {

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
    }

}

@Preview
@Composable
fun PreviewDialogBody(){
    Surface(Modifier.background(Color.White)) {
        DialogBody(readCount = 234, onClick = { })
    }
}