package com.depromeet.linkzupzup.view.main.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.presenter.MainViewModel
import com.depromeet.linkzupzup.presenter.model.LinkData
import com.depromeet.linkzupzup.presenter.model.LinkHashData
import com.depromeet.linkzupzup.presenter.model.MainContentData
import com.depromeet.linkzupzup.presenter.model.TagColor2
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.DLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainUI2: BaseView<MainViewModel>() {

    @ExperimentalMaterialApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {

            Surface(color = Gray10) {
                Column(modifier = Modifier.fillMaxWidth()) {

                    val mainContentList : ArrayList<MainContentData<*>> = arrayListOf<MainContentData<*>>().apply {
                        // 상단 영역
                        add(MainContentData<Any>(MainContentData.MAIN_TOP_HEADER))
                        // 스크랩 링크 리스트
                        addAll(MainContentData.mockMainContentList(5))
                    }

                    MainBodyUI(mainContentList)
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun PreViewComposable() {
    val mainContentList : ArrayList<MainContentData<*>> = arrayListOf<MainContentData<*>>().apply {
        // 상단 영역
        add(MainContentData<Any>(MainContentData.MAIN_TOP_HEADER))
        // 스크랩 링크 리스트
        addAll(MainContentData.mockMainContentList(5))
    }
    MainBodyUI2(mainContentList)
}

@ExperimentalMaterialApi
@Composable
fun MainBodyUI2(contentDataList: ArrayList<MainContentData<*>>) {

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        topBar = { MainAppBar() },
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { BottomSheet2(bottomSheetScaffoldState,coroutineScope) },   // sheetContent -  Column scope
        sheetShape = RoundedCornerShape(topStartPercent = 5,topEndPercent = 5),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Gray0t,
        sheetGesturesEnabled = false,
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()){

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Transparent)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {

            // 메인 리스트
            LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
                    .drawWithCache {
                        val gradient = Brush.linearGradient(
                            colors = listOf(Color.Transparent, Gray10),
                            start = Offset(0f, size.height - 100.dp.toPx()),
                            end = Offset(0f, size.height)
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient)
                        }
                    }) {

                items(contentDataList) { contentData ->
                    when(contentData.type) {

                        // 상단 영역 ( 환영 문구 + 스크랩 링크 읽은 횟수 )
                        MainContentData.MAIN_TOP_HEADER -> {
                            MainHeaderCard("김나경")
                        }

                        // 스크랩한 링크 아이템
                        MainContentData.MAIN_LINK_ITEM -> (contentData.data as? LinkData)?.let { linkData ->
                            MainLinkCard(linkData)
                        }

                        else -> DLog.e("TEST", "empty")

                    }
                }
            }

            // 하단 링크 줍기 버튼
            Button(shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }) {

                Text("링크 줍기",
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

// 상단 툴바
@Composable
fun MainAppBar2(appBarColor: MutableState<Color> = remember { mutableStateOf(Gray10) }){
    val ctx = LocalContext.current
    TopAppBar(title = { },
        actions = {

            // 알람
            MainAppBarBtn(painterResource(id = R.drawable.ic_alram)) {
                Toast.makeText(ctx, "알람", Toast.LENGTH_SHORT).show()
            }

            // 랭킹
            MainAppBarBtn(painterResource(id = R.drawable.ic_ranking)) {
                Toast.makeText(ctx, "랭킹", Toast.LENGTH_SHORT).show()
            }

            // 마이페이지
            MainAppBarBtn(painterResource(id = R.drawable.ic_mypage)) {
                Toast.makeText(ctx, "마이페이지", Toast.LENGTH_SHORT).show()
            }

        },
        backgroundColor = appBarColor.value,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp))

}

// 상단 툴바 버튼
@Composable
fun MainAppBarBtn2(painter: Painter, onClick: ()->Unit) {
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .wrapContentSize()
            .clickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(40.dp)) {

            Image(
                painter = painter,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun MainHeaderCard2(name : String, padding: PaddingValues = PaddingValues(0.dp)){
    // in ColumnScope
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.Transparent)
                .padding(padding)) {

            Text("${name}님,\n어서오세요. 반갑습니다!",
                color = Gray100t,
                style = TextStyle(fontSize = 24.sp,
                    color = Gray100t,
                    lineHeight = 32.4.sp,
                    fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold,
                        weight = FontWeight.W700))))
        }

        // 유저 링크 읽은 횟수
        ReadProgress2(5)
    }

}

@Composable
fun ReadProgress2(readCnt: Int, padding: PaddingValues = PaddingValues(0.dp)){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(padding)) {

        Card(elevation = 0.dp,
            backgroundColor = Gray0t,
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Text(text = "오늘 ${readCnt}개만 읽어도 뿌듯! \uD83D\uDC4D\uD83D\uDC4D",
                    lineHeight = 17.5.sp,
                    style = TextStyle(fontSize = 12.sp,
                        color = Gray100t,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W300))))

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = 0.7f,
                    backgroundColor = Gray20,
                    color = Blue50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp))
            }

        }
    }
}

@Composable
fun MainLinkCard2(linkData: LinkData){

    val ctx = LocalContext.current
    val tagList : ArrayList<LinkHashData> = linkData.hashtags

    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier.clickable {
            Toast.makeText(ctx, "스크랩 링크 클릭", Toast.LENGTH_SHORT).show()
        }) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(96.dp)) {

            // 링크 썸네일 이미지
            Image(painter = painterResource(id = R.drawable.ic_link_profile_img),
                contentDescription = null,
                modifier = Modifier.size(96.dp))

            Spacer(modifier = Modifier.width(10.dp))

            Column(Modifier.fillMaxSize()){

                // 링크 해시태그
                LazyRow(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)){
                    items(tagList) { tag ->
                        MainHashtagCard(tagName = tag.hashtagName, backColor = tag.tagColor.bgColor, textColor = tag.tagColor.textColor)
                    }
                }

                Spacer(Modifier.height(8.dp))

                // 링크 타이틀
                Text(text="스타트업과 안맞는 대기업 임원 DNA는 어떻게 찾아낼까?",
                    modifier = Modifier.fillMaxSize().weight(1f),
                    style = TextStyle(fontSize = 12.sp,
                        color = Gray100t,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))), maxLines = 2, overflow = TextOverflow.Ellipsis)

                // 작성자
                Row(Modifier.fillMaxWidth().height(16.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_link_user_img),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape))

                    Spacer(Modifier.width(4.dp))

                    Text(text="글쓴이",
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


@Composable
fun MainHashtagCard2(tagName : String, backColor : Color, textColor : Color){
    Card(elevation = 0.dp,
        backgroundColor = backColor,
        modifier = Modifier.height(20.dp)) {

        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 6.dp)){
            Text(text = "#$tagName",
                style = TextStyle(
                    fontSize = 10.sp,
                    color = textColor,
                    fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_light, weight = FontWeight.W300))))
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomSheet2(bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope){
    val inputLink = remember { mutableStateOf(TextFieldValue()) }
    val inputTag = remember { mutableStateOf(TextFieldValue()) }
    val tc1 : List<TagColor2> = listOf(
        TagColor2("디자인",TagBgColor01, TagTextColor01),
        TagColor2("포트폴리오",TagBgColor02, TagTextColor02),
        TagColor2("UX",TagBgColor03, TagTextColor03),
        TagColor2("UI",TagBgColor04, TagTextColor04),
        TagColor2("마케팅",TagBgColor05, TagTextColor05),
        TagColor2("인공지능",TagBgColor06, TagTextColor06))
    val tc2 : List<TagColor2> = listOf(
        TagColor2("프론트 개발",TagBgColor07, TagTextColor07),
        TagColor2("그로스 해킹",TagBgColor03, TagTextColor03),
        TagColor2("Android",TagBgColor01, TagTextColor01),
        TagColor2("스타트업",TagBgColor02, TagTextColor02),
        TagColor2("ios",TagBgColor04, TagTextColor04))

    // in Column Scope
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(580.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 20.dp, 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    })
        }

        Text(
            text = "읽고 싶은 링크를\n추가해주세요!",
            style = TextStyle(
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_bold,
                    weight = FontWeight.W700)),
                fontSize = 24.sp,),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp))

        Spacer(modifier = Modifier
            .height(10.dp)
            .padding(20.dp, 0.dp))

        TextField(
            value = inputLink.value,
            onValueChange = { inputLink.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W500)),
                fontSize = 12.sp,
                color = Gray100t),
            placeholder = {
                Text(
                    text = "링크주소를 여기에 붙여넣기 해주세요.",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500)),
                        fontSize = 12.sp,
                        color = Gray70),
                    lineHeight = 17.5.sp
                ) },


            leadingIcon = {
                Text(
                    text = "\uD83D\uDC49",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500)),
                        fontSize = 12.sp)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Gray20,
                textColor = Gray100t
            )
        )

        Spacer(modifier = Modifier
            .height(20.dp)
            .padding(20.dp, 0.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp)
        ){
            Text(
                text = "해시태그를 선택해주세요.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_bold,
                        weight = FontWeight.W700)),
                    fontSize = 14.sp,),
                modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "0",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W700)),
                        fontSize = 12.sp,
                        color = Gray70)
                )
                Text(
                    text = "/3",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W700)),
                        fontSize = 12.sp,
                        color = Gray70)
                )
            }
        }

        Spacer(modifier = Modifier
            .height(10.dp)
            .padding(20.dp, 0.dp))


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(tc1) { tag ->
                BottomSheetHashtagCard2(tagName = tag.tagName, backColor = tag.bgColor, textColor = tag.textColor)
            }
        }

        Spacer(modifier = Modifier
            .height(10.dp)
            .padding(20.dp, 0.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(tc2) { tag ->
                BottomSheetHashtagCard2(tagName = tag.tagName, backColor = tag.bgColor, textColor = tag.textColor)
            }
        }
        Spacer(modifier = Modifier
            .height(15.dp)
            .padding(20.dp, 0.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(20.dp, 0.dp)){
            Text(
                text = "원하시는 해시태그가 없으신가요?",
                style = TextStyle(
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W700)),
                    fontSize = 12.sp,
                    color = Gray70),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier.padding(20.dp)
        ){
            Button(
                onClick = {
                    DLog.e("MainUI", "BottomSheet/click save link button") },
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Gray50t, contentColor = Gray70),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)) {
                Text("저장하기",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700)),
                        fontSize = 14.sp,
                        lineHeight = 17.5.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            }
        }

    }
}

@Composable
fun BottomSheetHashtagCard2(tagName : String, backColor : Color, textColor : Color){
    Card(
        modifier = Modifier
            .height(32.dp),
        elevation = 0.dp,
        backgroundColor = backColor
    ){
        Box(
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "#$tagName",
                style = TextStyle(
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W300)),
                    fontSize = 12.sp,
                    color = textColor),
                modifier = Modifier.padding(8.dp,0.dp)
            )
        }

    }
}