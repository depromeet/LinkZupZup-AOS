package com.depromeet.linkzupzup.view.linkHistory.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.bottomSpacer
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.extensions.topSpacer
import com.depromeet.linkzupzup.architecture.presenterLayer.LinkHistoryDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MainContentData
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.view.main.ui.MainLinkCard

class LinkHistoryDetailUI: BaseView<LinkHistoryDetailViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                val contentList : ArrayList<MainContentData<*>> = arrayListOf<MainContentData<*>>().apply {
                    // 스크랩 링크가 없는 경우
                    add(MainContentData<Any>(MainContentData.EMPTY_TYPE))

                    // 스크랩 링크가 존재하는 경우
                    // addAll(MainContentData.mockMainContentList(4))
                }
                LinkHistoryBodyContent(contentList)
            }
        }
    }

}

@Composable
fun LinkHistoryBodyContent(items: ArrayList<MainContentData<*>>) {
    Scaffold(topBar = { LinkHistoryAppBar() },
        backgroundColor = Color.White) {

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)) {

            itemsIndexed(items) { index, item ->

                topSpacer(index, 16.dp)

                when(item.type){

                    // 스크랩한 링크가 없는 경우, 가이드 노출
                    MainContentData.EMPTY_TYPE -> LinkEmptyGuide()

                    // 스크랩한 링크 아이템
                    MainContentData.MAIN_LINK_ITEM -> (item.data as? LinkData)?.let { linkData ->
                        MainLinkCard(linkData = linkData)
                    }

                }

                bottomSpacer(index, items, 16.dp)

            }
        }
    }
}

@Composable
fun LinkHistoryAppBar(appBarColor: MutableState<Color> = remember { mutableStateOf(Color.White) }) {

    val ctx = LocalContext.current

    TopAppBar(title = {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)) {

                Text(text = "다 읽은 링크",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)),
                        fontSize = 14.sp,
                        lineHeight = 17.5.sp,
                        textAlign = TextAlign.Center
                    ))

            }
        },
        navigationIcon = {
            Card(elevation = 0.dp,
                modifier = Modifier.width(56.dp)
                    .height(52.dp)
                    .background(Color.White)
                    .noRippleClickable {
                        Toast.makeText(ctx, "뒤로가기", Toast.LENGTH_SHORT).show()
                    }) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.size(52.dp)) {

                    Spacer(Modifier.width(14.dp))

                    Image(painter = painterResource(id = R.drawable.ic_detail_back),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))

                }
            }
        },
        actions = {
            Spacer(Modifier.width(11.dp))
            Box(modifier = Modifier.size(56.dp))
        },
        backgroundColor = appBarColor.value,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color.White))
}

@Composable
fun LinkEmptyGuide() {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {

        Card(modifier = Modifier.fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0x144177F7))) {

                Text(text = "아직 다 읽은 링크가 없어요!\n링크를 추가해서 읽으면 포인트를 받을 수 있어요.",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF4076F6),
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.spoqa_hansansneo_regular,
                                weight = FontWeight.W400)
                        ),
                        textDecoration = TextDecoration.None,
                        shadow = Shadow(),
                        lineHeight = 16.8.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(vertical = 16.dp))

            }
        }
    }
}

@Preview
@Composable
fun LinkPreview() {
    LinkZupZupTheme {
        Surface(color = Color(0xFFE5E5E5)) {
            val contentList : ArrayList<MainContentData<*>> = arrayListOf<MainContentData<*>>().apply {
                // 스크랩 링크가 없는 경우
                add(MainContentData<Any>(MainContentData.EMPTY_TYPE))

                // 스크랩 링크가 존재하는 경우
                // addAll(MainContentData.mockMainContentList(5))
            }
            LinkHistoryBodyContent(contentList)
        }
    }
}