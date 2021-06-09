package com.depromeet.linkzupzup.view.main.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.TagColor
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.*
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.custom.BottomSheetCloseBtn
import com.depromeet.linkzupzup.view.custom.CustomLinearProgressIndicator
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainUI(var clickListener: (id: Int, linkId: Int?) -> Unit, var userName : String): BaseView<MainViewModel>() {

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Gray10) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    vm?.run {

                        MainBodyUI(linkList = linkList, vm = this, clickListener = clickListener, userName = userName)
                    }

                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun BottomSheetPreview() {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    BottomSheet(sheetState,coroutineScope)
}

/* MainUI */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainBodyUI(linkList: LiveData<ArrayList<LinkData>>, vm : MainViewModel? = null, clickListener: (id: Int, linkId: Int?) -> Unit, userName: String){
    val list by linkList.observeAsState(arrayListOf())

    // 로그인 성공
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val todayCnt : Int = vm?.todayReadCnt?.observeAsState()?.value ?: 0

    ModalBottomSheetLayout(sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = { BottomSheet(sheetState,coroutineScope,vm) },
        modifier = Modifier.fillMaxSize()) {

        Scaffold(topBar = { MainAppBar(clickListener = clickListener) },
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {

                val columnModifier = if (list.size > 0) Modifier
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
                    }
                else Modifier.fillMaxWidth()

                // 메인 리스트
                LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = columnModifier) {

                    itemsWithHeaderIndexed (
                        items = list,
                        useHeader = true,
                        headerContent = { MainHeaderCard(name = userName, progress = 0.2f * todayCnt) }) { idx, linkItem ->
                        MainLinkCard(index = idx, linkData = linkItem, vm) {
                            clickListener(R.id.activity_move,it)
                        }
                    }
                }

                // empty guide
                if(list.size==0){ EmptyLinkGuideCard(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)) }

                Button(shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    onClick = {
                        coroutineScope.launch {
                            DLog.e("TEST","링크 줍기 클릭")
                            sheetState.show()
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
}

@Composable
fun MainAppBar(appBarColor : MutableState<Color> = remember { mutableStateOf(Gray10) }, clickListener: (id: Int, linkId: Int?) -> Unit){
    // in ColumnScope
    
    TopAppBar(title = {},
        actions = {

            // 알람
            MainAppBarBtn(painterResource(id = R.drawable.ic_alram)) {
                clickListener(R.drawable.ic_alram,null)
            }

            // 랭킹
            MainAppBarBtn(painterResource(id = R.drawable.ic_ranking)) {
                clickListener(R.drawable.ic_ranking,null)
            }

            // 마이페이지
            MainAppBarBtn(painterResource(id = R.drawable.ic_mypage)) {
                clickListener(R.drawable.ic_mypage,null)
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
fun MainAppBarBtn(painter: Painter, onClick: ()->Unit) {
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .wrapContentSize()
            .noRippleClickable { onClick() }) {

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
fun MainHeaderCard(name : String, progress: Float, padding: PaddingValues = PaddingValues(0.dp)){
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
        ReadProgress(progress)
    }
}

@Composable
fun ReadProgress(progress: Float, padding: PaddingValues = PaddingValues(0.dp)){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(padding)) {

        Card(elevation = 0.dp,
            backgroundColor = Gray0t,
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { }) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Text(text = "오늘 5개만 읽어도 뿌듯! \uD83D\uDC4D\uD83D\uDC4D",
                    lineHeight = 17.5.sp,
                    style = TextStyle(fontSize = 12.sp,
                        color = Gray100t,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W300))))

                Spacer(modifier = Modifier.height(8.dp))

                CustomLinearProgressIndicator(
                    progress = progress,
                    backgroundColor = Gray20,
                    color = Blue50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(horizontal = 10.dp))
            }

        }
    }
}

@Composable
fun MainLinkCard(index: Int, linkData: LinkData, viewModel: MainViewModel? = null, clickListener: (Int) -> Unit){

    val ctx = LocalContext.current
    val tagList : ArrayList<LinkHashData> = linkData.hashtags
    val metaTitle = remember { mutableStateOf(linkData.linkTitle)}
    val metaImgUrl = remember { mutableStateOf(linkData.imgURL)}
    val linkId = remember { mutableStateOf(linkData.linkId)}

    // meta data가 없으면 비동기로 호출하여 업데이트합니다.
    viewModel?.loadMetadata(index, linkData) {
        /**
         * UI 갱신이 되지 않아, 비동기 콜백으로 데이터를 교체하여 갱신하였습니다.
         */
        metaTitle.value = it.title
        metaImgUrl.value = it.imgUrl
    }

    val painter = rememberGlidePainter(request = metaImgUrl.value, fadeIn = true, previewPlaceholder = R.drawable.img_linklogo_placeholder)
//    when(painter.loadState) {
//        is ImageLoadState.Error -> {
//            DLog.e("TEST Glide", "Error")
//            //painter.request = Image(painter = painterResource(id = R.drawable.img_linklogo_placeholder), contentDescription = null)
//        }
//        is ImageLoadState.Empty -> {
//            DLog.e("TEST Glide", "Empty")
//            //painter.request = Image(painter = painterResource(id = R.drawable.img_linklogo_placeholder), contentDescription = null)
//        }
//        else -> {}
//    }

    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier.noRippleClickable { clickListener(linkId.value) }) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(96.dp)) {

            // 링크 썸네일 이미지
            Box {
                Image(contentDescription = null,
                    modifier = Modifier.size(96.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    painter =  rememberGlidePainter(request = metaImgUrl.value, fadeIn = true, previewPlaceholder = R.drawable.img_linklogo_placeholder))

                MainAlarmCard() // 리마인더 유무에 따라 현재 행 추가하시면 됩니다.
            }

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
                Text(text= metaTitle.value,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    style = TextStyle(fontSize = 12.sp,
                        color = Gray100t,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))), maxLines = 2, overflow = TextOverflow.Ellipsis)

                // 작성자
                Row(Modifier
                        .fillMaxWidth()
                        .height(16.dp)) {

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
fun MainHashtagCard(tagName : String, backColor : Color, textColor : Color){
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

@Preview
@Composable
fun MainAlarmCard(){
    Row(modifier = Modifier.wrapContentSize()
        .padding(4.dp)
        .background(Color.Transparent)){

        Card(elevation = 0.dp,
            shape = Shapes.small,
            modifier = Modifier.size(28.dp)){

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
                    .background(Blue20)
                    .padding(6.dp)){

                Image(painter = painterResource(id = R.drawable.ic_blue_alarm),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    alignment = Alignment.Center)
            }
        }
    }

}

/* BottomSheet */
@ExperimentalMaterialApi
@Composable
fun BottomSheet(sheetState : ModalBottomSheetState, coroutineScope : CoroutineScope, vm : MainViewModel? = null){
    val ctx = LocalContext.current
    val saveBtnColor = remember { mutableStateOf(Gray50t) }
    val saveTxtColor = remember { mutableStateOf(Gray70) }
    val linkUrl = remember { mutableStateOf("") }
    val liveCnt = (vm?.selectTagList?.value ?: arrayListOf()).mutableStateValue()

    // in Column Scope
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(580.dp)
        .padding(bottom = 16.dp)) {

        // 닫기 버튼
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {

            BottomSheetCloseBtn(painterResource(id = R.drawable.ic_close)){
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)){

            /* title */
            BottomHeaderCard()

            Spacer(Modifier.height(8.dp))

            /* Text field */
            CustomTextField(hintStr = "\uD83D\uDC49 링크주소를 여기에 붙여넣기 해주세요.",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 23.dp)){
                linkUrl.value = it

                if(it.isNullOrEmpty()){
                    saveBtnColor.value = Gray50t
                    saveTxtColor.value = Gray70
                }else if(it.isNotEmpty()){
                    saveBtnColor.value = Blue50
                    saveTxtColor.value = Color.White
                }
            }

            Spacer(Modifier.height(20.dp))

            /* 해시태그 선택 */
            BottomSheetSelect(liveCnt.value.size){
                vm?.insertSelectedTag(it)
            }

            Spacer(Modifier.height(24.dp))

            /* 커스텀 태그 입력 화면 */
            BottomSheetInputTag{ tagName ->
                vm?.insertSelectedTag(LinkHashData(0,tagName,"", CommonUtil.getRandomeTagColor()))
            }
        }

        /* 클릭된 해시태그 보여주는 열 */
        BottomSheetSelectedTagList(vm = vm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)){
            vm?.removeSelectedTag(it)
        }

        /* 하단 저장하기 버튼 */
        Button(shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = saveBtnColor.value, contentColor = saveTxtColor.value),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(start = 24.dp, end = 24.dp),
            onClick = {
                // link register api 호출
                vm?.let { viewModel ->

                    // 링크 저장 TODO : 추후 LinkRegisterEntity 개선 필요
                    viewModel.registerLink(LinkRegisterEntity(
                        linkURL = linkUrl.value,
                        hashtags = ArrayList(viewModel.selectTagList.value?.map { it.hashtagName }))){

                        coroutineScope.launch {
                            viewModel.getLinkList()
                            sheetState.hide()
                            Toast.makeText(ctx,"링크가 저장되었습니다!",Toast.LENGTH_SHORT).show()    // 2초
                        }
                    }
                }
            }) {

            Text("저장하기",
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

@Composable
fun BottomHeaderCard(padding: PaddingValues = PaddingValues(0.dp)){
    // in ColumnScope
    Column(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 23.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(padding)) {

            Text(
                text = "읽고 싶은 링크를\n추가해주세요!",
                color = Gray100t,
                style = TextStyle(fontSize = 24.sp,
                    color = Gray100t,
                    lineHeight = 32.4.sp,
                    fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold,
                        weight = FontWeight.W700))))
        }
    }
}

@Composable
fun BottomSheetSelect(cnt: Int? = 0, onClick: (LinkHashData) -> Unit){
    val size = 3
    // val cnt = vm?.selectTagList?.observeAsState()?.value?.size

    val tc1 : List<LinkHashData> = listOf(
        LinkHashData(0,"디자인","",TagColor(TagBgColor01, TagTextColor01)),
        LinkHashData(1,"포트폴리오","",TagColor(TagBgColor02, TagTextColor02)),
        LinkHashData(2,"UX","",TagColor(TagBgColor03, TagTextColor03)),
        LinkHashData(3,"UI","",TagColor(TagBgColor04, TagTextColor04)),
        LinkHashData(4,"마케팅","",TagColor(TagBgColor05, TagTextColor05)),
        LinkHashData(5,"인공지능","",TagColor(TagBgColor06, TagTextColor06)))
    val tc2 : List<LinkHashData> = listOf(
        LinkHashData(6,"프론트 개발","",TagColor(TagBgColor07, TagTextColor07)),
        LinkHashData(7,"그로스 해킹","",TagColor(TagBgColor03, TagTextColor03)),
        LinkHashData(8,"Android","",TagColor(TagBgColor01, TagTextColor01)),
        LinkHashData(9,"스타트업","",TagColor(TagBgColor02, TagTextColor02)),
        LinkHashData(10,"ios","",TagColor(TagBgColor04, TagTextColor04)))

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(18.dp)
            .padding(horizontal = 23.dp)){

        Text(text = "해시태그를 선택해주세요.",
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 14.sp,
                color = Gray100t,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_bold,
                    weight = FontWeight.W700))))

        Text(text = "$cnt/$size",
            style = TextStyle(
                fontSize = 12.sp,
                color = Gray70,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W700))))
    }

    Spacer(Modifier.height(12.dp))

    LazyRow(modifier = Modifier.fillMaxWidth(),contentPadding = PaddingValues(23.dp,0.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)){
        items(tc1) { tag ->
            BottomSheetHashtagCard(tag){
                onClick(tag)
            }
        }
    }

    Spacer(Modifier.height(12.dp))

    LazyRow(modifier = Modifier.fillMaxWidth(),contentPadding = PaddingValues(23.dp,0.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)){
        items(tc2) { tag ->
            BottomSheetHashtagCard(tag){
                onClick(tag)
            }
        }
    }
}

@Composable
fun BottomSheetInputTag(onClick: (String) -> Unit){
    val beforeClickStr  = "원하시는 해시태그가 없으신가요?"
    val afterClickStr = "원하는 해시태그가 없다면 적어주세요!"

    val isVisible = remember { mutableStateOf(false) }
    val clickStr = remember { mutableStateOf(beforeClickStr) }
    val strColor = remember { mutableStateOf(Gray70)}
    val tagName = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 23.dp)){
        Text(
            text = clickStr.value,
            modifier = Modifier.noRippleClickable {
                    isVisible.value = !isVisible.value
                    if(isVisible.value) {
                        clickStr.value = afterClickStr
                        strColor.value = Gray100t
                    }
                    else {
                        clickStr.value = beforeClickStr
                        strColor.value = Gray70
                    } },
            style = TextStyle(
                fontSize = 12.sp,
                color = strColor.value,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W700))))

        if(isVisible.value){
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)){

                CustomTextField(modifier = Modifier
                    .height(40.dp)
                    .weight(1f),
                    useClearBtn = true,
                    hintStr = "#"){
                    tagName.value = it
                }

                Card(modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically)
                    .noRippleClickable {
                        onClick(tagName.value)
                        tagName.value = ""
                    },
                    shape = RoundedCornerShape(4.dp),
                    backgroundColor = Gray70,
                    elevation = 0.dp){
                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.ic_white_plus),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp))
                    }
                }
                
            }
        }
    }
}

@Composable
fun BottomSheetHashtagCard(tag: LinkHashData, isSelected : Boolean = false, onClick: () -> Unit){
    Card(
        elevation = 0.dp,
        backgroundColor = tag.tagColor.bgColor,
        modifier = Modifier
            .height(32.dp)
            .noRippleClickable { onClick() }){

        Box(contentAlignment = Alignment.Center){

            Row(modifier = Modifier.padding(8.dp,0.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically){

                Text(
                    text = "#${tag.hashtagName}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = tag.tagColor.textColor,
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500))))
                if(isSelected){
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp))
                }
            }

        }
    }
}

@Composable
fun BottomSheetSelectedTagList(modifier: Modifier = Modifier.fillMaxWidth(), vm: MainViewModel? = null, onClick: (LinkHashData) -> Unit){

    vm?.run {
        val tagList by selectTagList.observeAsState(arrayListOf())


        LazyRow(modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp)){
                items(tagList){ tag->
                    BottomSheetHashtagCard(tag, isSelected = true){
                        onClick(tag)
                    }
                }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun EmptyLinkGuideCard(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
            .background(Color.Transparent)) {

        // 도넛 이미지
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {

            Image(painter = painterResource(id = R.drawable.ic_donut05),
                contentDescription = null,
                modifier = Modifier.size(168.dp, 124.dp))
        }

        Text("링크를 저장하고 읽으면 포인트가 부여됩니다.\n아래 링크줍기 버튼을 눌러 링크를 저장해보세요!\n\n\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47 ",
            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp),
            textAlign = TextAlign.Center,
            color = Blue50)


    }
}

/**
 * 향후 Master 브런치와 머지 이후 Custom UI는 공통으로 관리하도록 옮기겠습니다.
 */
@Composable
fun CustomTextField(modifier: Modifier = Modifier
    .fillMaxWidth()
    .height(40.dp)
    .heightIn(min = 20.dp, max = 100.dp),
                    txt: String = "",
                    hintStr: String = "",
                    shape: Shape = RoundedCornerShape(4.dp),
                    backgroundColor: Color = Color(0xFFF1F2F5),
                    useClearBtn : Boolean = true,
                    onValueChange: (String) -> Unit = {}) {

    Card(modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        elevation = 0.dp) {

        val text = rememberSaveable { mutableStateOf(txt) }
        val textModifier: Modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 44.dp)

        // clear or 입력후 엔터 입력 시, 키보드를 내리기 위해 사용
        val focusManager = LocalFocusManager.current

        Box(Modifier.fillMaxSize()) {

            // hint text
            if (text.value.isNullOrEmpty()) Text(text = hintStr,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF878D91),
                    lineHeight = 16.8.sp,
                    fontFamily = FontFamily(
                        Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W500)
                    )
                ),
                maxLines = 1,
                modifier = textModifier.align(Alignment.Center))

            // edit text
            BasicTextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                    onValueChange(it)
                },
                modifier = textModifier.align(Alignment.Center),
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF292A2B),
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500)
                    ),
                    textDecoration = TextDecoration.None,
                    shadow = Shadow(),
                    lineHeight = 16.8.sp
                ),
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text)
            )

            if(useClearBtn){
                // clear btn
                if (text.value.isNotEmpty()) Row(Modifier.fillMaxSize()) {

                    Spacer(Modifier.weight(1f))

                Column(verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(44.dp)
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                        .noRippleClickable {
                            text.value = ""
                            onValueChange(text.value)
                            // 포커스 제거, 키보드 내리기!
                            focusManager.clearFocus()
                        }) {

                    Image(painter = painterResource(id = R.drawable.ic_gray_close),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))

                    }
                }
            }

        }
    }
}