package com.depromeet.linkzupzup.view.linkHistory.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.bottomSpacer
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.extensions.topSpacer
import com.depromeet.linkzupzup.architecture.presenterLayer.LinkHistoryDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MainContentData
import com.depromeet.linkzupzup.ui.theme.Blue20
import com.depromeet.linkzupzup.ui.theme.Gray100t
import com.depromeet.linkzupzup.ui.theme.Gray70
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.main.ui.MainHashtagCard
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.isFinalState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter

class LinkHistoryDetailUI(private val clickListener: (Int) -> Unit): BaseView<LinkHistoryDetailViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                val contentList : ArrayList<MainContentData<*>> = arrayListOf<MainContentData<*>>().apply {
                    // ????????? ????????? ?????? ??????
                    add(MainContentData<Any>(MainContentData.EMPTY_TYPE))

                    // ????????? ????????? ???????????? ??????
                    // addAll(MainContentData.mockMainContentList(4))
                }
                vm?.let { viewModel -> LinkHistoryBodyContent(viewModel, clickListener = clickListener) }
            }
        }
    }

}

@Composable
fun LinkHistoryBodyContent(vm: LinkHistoryDetailViewModel, clickListener: (Int) -> Unit) {
    Scaffold(topBar = { LinkHistoryAppBar(clickListener = clickListener) },
        backgroundColor = Color.White) {

        val linkList by vm.linkList.observeAsState(arrayListOf())

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)) {

            itemsIndexed(linkList) { index, item ->

                topSpacer(index, 16.dp)
                HistoryLinkCard(index = index, linkData = item, viewModel =  vm){
                    vm.moveScrapDetail(item)
                }
                bottomSpacer(index, linkList, 16.dp)
            }
        }

        if (linkList.size == 0) LinkEmptyGuide()
    }
}

@Composable
fun LinkHistoryAppBar(appBarColor: MutableState<Color> = remember { mutableStateOf(Color.White) }, clickListener: (Int) -> Unit) {

    TopAppBar(title = {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)) {

                Text(text = "??? ?????? ??????",
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
                modifier = Modifier
                    .width(56.dp)
                    .height(52.dp)
                    .background(Color.White)
                    .noRippleClickable { clickListener(R.id.activity_close) }) {

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
        modifier = Modifier.fillMaxWidth()
            .height(52.dp)
            .background(Color.White))
}

@Composable
fun LinkEmptyGuide() {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 24.dp, start = 16.dp, end = 16.dp)) {

        Card(shape = RoundedCornerShape(8.dp),
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
                .height(64.dp)) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0x144177F7))) {

                Text(text = "?????? ??? ?????? ????????? ?????????!\n????????? ???????????? ????????? ???????????? ?????? ??? ?????????.",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF4076F6),
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_regular,
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

@Composable
fun HistoryLinkCard(index: Int, linkData: LinkData, viewModel: LinkHistoryDetailViewModel? = null, clickListener: (Int) -> Unit) {

    val tagList : ArrayList<LinkHashData> = linkData.hashtags
    val metaTitle = remember { mutableStateOf(linkData.linkTitle) }
    val metaImgUrl = remember { mutableStateOf(linkData.imgURL) }
    val linkId = remember { mutableStateOf(linkData.linkId)}
    val metaAuthor = remember { mutableStateOf(linkData.author) }

    // meta data ??? ????????? ???????????? ???????????? ?????????????????????.
    viewModel?.loadMetadata(index, linkData) {
        /**
         * UI ????????? ?????? ??????, ????????? ???????????? ???????????? ???????????? ?????????????????????.
         */
        metaTitle.value = it.title
        metaImgUrl.value = it.imgUrl
        metaAuthor.value = it.author
    }

    DLog.e("Main UI", metaAuthor.value)

    val painter = rememberGlidePainter(request = metaImgUrl.value, fadeIn = true, previewPlaceholder = R.drawable.img_link_placeholder)

    LaunchedEffect(painter) {
        snapshotFlow { painter.loadState }
            .filter { it.isFinalState() }
            .collect {
                when(it){
                    is ImageLoadState.Empty, is ImageLoadState.Error -> { painter.request = R.drawable.img_link_placeholder }
                    else -> {}
                }
            }
    }

    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier.noRippleClickable { clickListener(linkId.value) }) {

        Row(modifier = Modifier.fillMaxWidth()
            .background(Color.Transparent)
            .height(96.dp)) {

            // ?????? ????????? ?????????
            Box(modifier = Modifier.background(Blue20)
                .size(96.dp)) {
                Image(contentDescription = null,
                    modifier = Modifier.size(96.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    painter =  painter)

                // MainAlarmCard() // ???????????? ????????? ?????? ?????? ??? ??????????????? ?????????.
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(Modifier.fillMaxSize()){

                // ?????? ????????????
                LazyRow(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)){
                    items(tagList) { tag ->
                        MainHashtagCard(tagName = tag.hashtagName, backColor = tag.tagColor.bgColor, textColor = tag.tagColor.textColor)
                    }
                }

                Spacer(Modifier.height(8.dp))

                // ?????? ?????????
                Text(text= metaTitle.value,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    style = TextStyle(fontSize = 12.sp,
                        color = Gray100t,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))), maxLines = 2, overflow = TextOverflow.Ellipsis)

                // ?????????
                Row(Modifier.fillMaxWidth()
                    .height(16.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_jubjub),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                            .clip(CircleShape))

                    Spacer(Modifier.width(4.dp))

                    Text(text = metaAuthor.value,
                        modifier = Modifier.fillMaxHeight(),
                        lineHeight = 16.8.sp,
                        style = TextStyle(fontSize = 12.sp,
                            color = Gray70,
                            fontFamily = FontFamily(Font(
                                resId = R.font.spoqa_hansansneo_light,
                                weight = FontWeight.W300))), textAlign = TextAlign.Start)

                }
            }
        }
    }
}
