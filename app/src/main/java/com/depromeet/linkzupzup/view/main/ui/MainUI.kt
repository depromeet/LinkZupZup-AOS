package com.depromeet.linkzupzup.view.main.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.extensions.*
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.view.custom.BottomSheetCloseBtn
import com.depromeet.linkzupzup.view.custom.CustomLinearProgressIndicator
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.isFinalState
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.utils.DLog
import com.google.accompanist.imageloading.ImageLoadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect


class MainUI(private var clickListener: (Int) -> Unit, private var userName : String): BaseView<MainViewModel>() {

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Gray10) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    vm?.let { viewModel ->
                        MainBodyUI(viewModel = viewModel, clickListener = clickListener, userName = userName)
                    }
                }
            }
        }
    }
}

/* MainUI */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainBodyUI(viewModel : MainViewModel, clickListener: (Int) -> Unit, userName: String){
    val linkList by viewModel.linkList.observeAsState(arrayListOf())
    val (selected, setSelected) = remember(calculation = { mutableStateOf(0) })
    val (targetLink, updateLink) = LinkData().mutableStateValue()


    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val tagReadCnt = remember { mutableStateOf(0) }
    tagReadCnt.value = viewModel.preference?.getTodayCount() ?: 0   // 문제 있음

    DLog.e("MAIN", "LINK_SIZE: ${linkList.size}")

    ModalBottomSheetLayout(sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when(selected) {
                0 -> MainBottomSheet(sheetState, coroutineScope, viewModel, clickListener, targetLink)
                1 -> MainBottomSheet(sheetState, coroutineScope, viewModel, clickListener, targetLink)
            }
            DLog.e("SHEET_CONTENT", "selected: $selected")
        },
        modifier = Modifier.fillMaxSize()) {

        Scaffold(topBar = { MainAppBar(clickListener = clickListener) },
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()
                .background(color = Color.Transparent)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {

                val columnModifier = if (linkList.size > 0) Modifier.fillMaxWidth()
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
                        items = linkList,
                        useHeader = true,
                        headerContent = { MainHeaderCard(name = userName, progress = 0.2f * tagReadCnt.value) }) { idx, linkItem ->
                        MainLinkCard(index = idx, linkData = linkItem, viewModel = viewModel) {
                            viewModel.moveScrapDetail(linkItem)
                        }
                    }
                }

                // empty guide
                if (linkList.size == 0) EmptyLinkGuideCard(
                    Modifier.fillMaxWidth()
                        .weight(1f))

                Button(shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    onClick = {
//                        coroutineScope.launch {
//                            updateLink(LinkData())
//                            setSelected(0)
//                            sheetState.show()
//                        }
                        // RegistBottomDialog 열기




                    }) {

                    Text("링크 줍기",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 17.5.sp,
                            fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))))
                }
            }

        }
    }
}

@Composable
fun MainAppBar(appBarColor : MutableState<Color> = remember { mutableStateOf(Gray10) }, clickListener: (Int) -> Unit){
    TopAppBar(title = {},
        actions = {

            // 알람
            MainAppBarBtn(painterResource(id = R.drawable.ic_alram)) {
                clickListener(R.drawable.ic_alram)
            }

//            // 랭킹
//            MainAppBarBtn(painterResource(id = R.drawable.ic_ranking)) {
//                clickListener(R.drawable.ic_ranking)
//            }

            // 마이페이지
            MainAppBarBtn(painterResource(id = R.drawable.ic_mypage)) {
                clickListener(R.drawable.ic_mypage)
            }

            Spacer(modifier = Modifier.width(16.dp))
            
        },
        backgroundColor = appBarColor.value,
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
            .height(52.dp))
}

// 상단 툴바 버튼
@Composable
fun MainAppBarBtn(painter: Painter, onClick: ()->Unit) {
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier.wrapContentSize()
            .noRippleClickable { onClick() }) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.size(40.dp)) {

            Image(painter = painter,
                contentDescription = null,
                Modifier.size(24.dp))
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
fun MainLinkCard(index: Int, linkData: LinkData, viewModel: MainViewModel? = null, clickListener: (Int) -> Unit) {

    val tagList : ArrayList<LinkHashData> = linkData.hashtags
    val metaTitle = remember { mutableStateOf(linkData.linkTitle) }
    val metaImgUrl = remember { mutableStateOf(linkData.imgURL) }
    val linkId = remember { mutableStateOf(linkData.linkId)}
    val metaAuthor = remember { mutableStateOf(linkData.author) }

    // meta data 가 없으면 비동기로 호출하여 업데이트합니다.
    viewModel?.loadMetadata(index, linkData) {
        /**
         * UI 갱신이 되지 않아, 비동기 콜백으로 데이터를 교체하여 갱신하였습니다.
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

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(96.dp)) {

            // 링크 썸네일 이미지
            Box(modifier = Modifier
                .background(Blue20)
                .size(96.dp)) {
                Image(contentDescription = null,
                    modifier = Modifier.size(96.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    painter =  painter)

                // MainAlarmCard() // 리마인더 유무에 따라 현재 행 추가하시면 됩니다.
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(Modifier.fillMaxSize()){

                // 링크 해시태그
                LazyRow(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)){
                    items(tagList) { tag ->
                        MainHashtagCard(tagName = tag.hashtagName, backColor = tag.tagColor.bgColor.composeColor(), textColor = tag.tagColor.textColor.composeColor())
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
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_jubjub),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
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
    Row(modifier = Modifier
        .wrapContentSize()
        .padding(4.dp)
        .background(Color.Transparent)){

        Card(elevation = 0.dp,
            shape = Shapes.small,
            modifier = Modifier.size(28.dp)){

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
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
fun MainBottomSheet(sheetState : ModalBottomSheetState, coroutineScope : CoroutineScope, viewModel: MainViewModel, clickListener: (Int) -> Unit, linkData: LinkData) {

    val isNewRegister = remember { mutableStateOf(false) }
    val linkId = remember { mutableStateOf(-1) }
    val hashtags = remember { mutableStateListOf<LinkHashData>() }
    val (linkUrl, setLink) = remember { mutableStateOf(linkData.linkURL) }

    val ctx = LocalContext.current
    val saveBtnColor = if (!linkUrl.isNullOrEmpty()) Blue50 else Gray50t
    val saveTxtColor = if (!linkUrl.isNullOrEmpty()) Color.White else Gray70

    linkId.value = linkData.linkId
    isNewRegister.value = linkData.linkId < 0
    hashtags.addAll(linkData.hashtags)

    // DLog.e("BottomSheet", "liveData: ${Gson().toJson(linkData)}, linkId: ${linkId.value}, linkUrl: ${linkUrl.value}, hashtags: ${Gson().toJson(hashtags)}")

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

            BottomSheetCloseBtn(painterResource(id = R.drawable.ic_close)) {
                coroutineScope.launch { sheetState.hide() }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {

            /**
             * Header Title
             */
            BottomHeaderCard()

            Spacer(Modifier.height(8.dp))

            /**
             * 링크 주소 입력창
             */
            CustomTextField(txt = linkUrl,
                hintStr = "\uD83D\uDC49 링크주소를 여기에 붙여넣기 해주세요.",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 24.dp)) {
                setLink(it)
                DLog.e("TEST MainUI 텍트스갱신","string: ${it}, url change : $linkUrl")

                // DLog.e("MAIN_TEST", "url: $it")
                // DLog.e("hashtags", "${Gson().toJson(hashtags)}")
            }

            Spacer(Modifier.height(40.dp))

            /**
             * 해시태그 선택
             */
            BottomSheetSelect(cnt = hashtags.size) { target ->
                updateHashtags(hashtags, target)
                // DLog.e("HASH_TAG", "${Gson().toJson(target)}")
                // DLog.e("hashtags", "${Gson().toJson(hashtags)}")
            }

            Spacer(Modifier.height(24.dp))

            /**
             * 커스텀 태그 입력 화면
             */
            BottomSheetInputTag { tagName ->
                updateHashtags(hashtags, LinkHashData(hashtagName = tagName))
                // DLog.e("CUSTOM_TAG", "$tagName")
                // DLog.e("hashtags", "${Gson().toJson(hashtags)}")
            }

        }

        /**
         * 선택된 해시태그 리스트
         */
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)) {

            items(items = hashtags, itemContent = { tag ->
                // DLog.e("HASH_TAG", "tagName: ${tag.hashtagName}, tag: ${Gson().toJson(tag)}")
                // DLog.e("hashtags", "${Gson().toJson(hashtags)}")
                Card(modifier = Modifier
                    .height(32.dp)
                    .noRippleClickable { updateHashtags(hashtags, tag) },
                    shape = RoundedCornerShape(2.dp),
                    backgroundColor = tag.tagColor.bgColor.composeColor(),
                    elevation = 0.dp) {

                    Box(contentAlignment = Alignment.Center){
                        Row(modifier = Modifier.padding(8.dp,0.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically){

                            Text(text = "#${tag.hashtagName}",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = tag.tagColor.textColor.composeColor(),
                                    fontFamily = FontFamily(Font(
                                        resId = R.font.spoqa_hansansneo_regular,
                                        weight = FontWeight.W500))))

                            Image(painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier.size(12.dp))

                        }
                    }
                }
            })
        }

        Spacer(modifier = Modifier.height(20.dp))

        /* 하단 저장하기 버튼 */
        Button(shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = saveBtnColor, contentColor = saveTxtColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(start = 24.dp, end = 24.dp),
            onClick = {
                // 링크 저장 TODO : 추후 LinkRegisterEntity 개선 필요
                if(!linkUrl.isNullOrEmpty()) {
                    viewModel.registerLink(LinkRegisterEntity(
                        linkURL = linkUrl,
                        hashtags = ArrayList(hashtags.map { it.hashtagName }))
                    ){
                        coroutineScope.launch {
                            toast(ctx,"링크가 저장되었습니다!")
                            viewModel.getLinkList()
                            sheetState.hide()
                        }
                    }
                }
            }) {

            Text("저장하기",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 17.5.sp,
                    fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))))
        }
    }
}

fun updateHashtags(hashtags : SnapshotStateList<LinkHashData>, target: LinkHashData) {
    with(hashtags) {
        apply {
            find { it.hashtagName == target.hashtagName }?.let {
                remove(it)
            } ?: let {
                if (size < AppConst.HASH_TAG_MAX_LIMIT) add(target)
                // else { /* 3개 이상 무시 */ }
            }
        }
    }
}

@Composable
fun BottomHeaderCard(padding: PaddingValues = PaddingValues(0.dp)){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 23.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(padding)) {

            Text(text = "읽고 싶은 링크를\n추가해주세요!",
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
fun BottomSheetSelect(cnt: Int, tagSizeLimit: Int = 3, onClick: (LinkHashData) -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(18.dp)
            .padding(horizontal = 23.dp)){

        Text(text = "해시태그를 선택해주세요.",
            modifier = Modifier.weight(1f),
            style = TextStyle(fontSize = 14.sp,
                color = Gray100t,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))))

        Text(text = "$cnt/$tagSizeLimit",
            style = TextStyle(fontSize = 12.sp,
                color = Gray70,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W700))))
    }

    Spacer(Modifier.height(12.dp))

    LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(23.dp,0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)){
        items(LinkHashData.tc1) { tag ->
            BottomSheetHashtagCard(tag){
                onClick(tag)
            }
        }
    }

    Spacer(Modifier.height(12.dp))

    LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(23.dp,0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)){
        items(LinkHashData.tc2) { tag ->
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

    Column(modifier = Modifier
        .fillMaxWidth()
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

                CustomTextField(hintStr = "#",
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)) {
                    tagName.value = it
                }

                Card(modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically)
                    .noRippleClickable {
                        onClick(tagName.value)
                        // tagName.value = ""
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
fun BottomSheetHashtagCard(tag: LinkHashData, isSelected: Boolean = false, onClick: (LinkHashData) -> Unit){
    Card(elevation = 0.dp,
        backgroundColor = tag.tagColor.bgColor.composeColor(),
        modifier = Modifier
            .height(32.dp)
            .clickable { onClick(tag) }){

        Box(contentAlignment = Alignment.Center){

            Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp,0.dp)){

                Text(text = "#${tag.hashtagName}",
                    style = TextStyle(fontSize = 12.sp,
                        color = tag.tagColor.textColor.composeColor(),
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W300))))

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
fun BottomSheetSelectedTagList(modifier: Modifier = Modifier.fillMaxWidth(), tagList: State<ArrayList<LinkHashData>>, onClick: (LinkHashData) -> Unit){
    val tags = tagList.value
    LazyRow(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)){
        items(tags){ tag ->
            BottomSheetHashtagCard(tag, isSelected = true){
                onClick(tag)
            }
        }
    }
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
fun CustomTextField(txt: String = "",
                    hintStr: String = "",
                    shape: Shape = RoundedCornerShape(4.dp),
                    backgroundColor: Color = Color(0xFFF1F2F5),
                    useClearBtn : Boolean = true,
                    modifier: Modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .heightIn(min = 20.dp, max = 100.dp),
                    onValueChange: (String) -> Unit = {}) {

    Card(modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        elevation = 0.dp) {

        val textState = remember { mutableStateOf(TextFieldValue(txt)) }

        val textModifier: Modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 44.dp)

        // clear or 입력후 엔터 입력 시, 키보드를 내리기 위해 사용
        val focusManager = LocalFocusManager.current

        Box(Modifier.fillMaxSize()) {

            // hint text
            if (textState.value.text.isNullOrEmpty()) Text(text = hintStr,
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
                value = textState.value,
                onValueChange = {
                    textState.value = it
                    onValueChange(it.text)
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
                if (textState.value.text.isNotEmpty()) Row(Modifier.fillMaxSize()) {

                    Spacer(Modifier.weight(1f))

                Column(verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(44.dp)
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                        .noRippleClickable {

                            textState.value = TextFieldValue("")
                            onValueChange(textState.value.text)
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