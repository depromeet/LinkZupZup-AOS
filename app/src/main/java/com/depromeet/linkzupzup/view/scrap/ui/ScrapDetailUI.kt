package com.depromeet.linkzupzup.view.scrap.ui

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.clearMillis
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.extensions.toast
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.TagColor
import com.depromeet.linkzupzup.extensions.getAlarmDateStr
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.utils.DateUtil
import com.depromeet.linkzupzup.view.custom.BottomSheetCloseBtn
import com.depromeet.linkzupzup.view.custom.CustomDatePicker
import com.depromeet.linkzupzup.view.custom.CustomTimePicker
import com.depromeet.linkzupzup.view.main.ui.*
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.isFinalState
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import java.util.*

class ScrapDetailUI(private val clickListener: (Int) -> Unit): BaseView<ScrapDetailViewModel>() {

    companion object {
        const val SCRAP_MENU = 0
        const val SCRAP_ALARM = 1
        const val SCRAP_LINK_UPDATE = 2
    }

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                vm?.let { viewModel -> LinkScrapBottomSheet(viewModel, clickListener) }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun LinkScrapBottomSheet(viewModel: ScrapDetailViewModel, clickListener: (Int) -> Unit) {

    val (popupState, openPopup) = remember { mutableStateOf(false) }
    val linkInfo by viewModel.linkInfo.observeAsState(LinkData())

    /**
     * 삭제 다이얼로그
     */
    if (popupState) AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "삭제하시겠습니까?") },
        confirmButton = {
            Button(onClick = {
                viewModel.deleteLink(linkInfo.linkId) {
                    viewModel.setRefresh(true)
                    clickListener(R.id.activity_close)
                    /**
                     * TODO:리스트로 돌아가서 API 갱신 여부
                     */
                }
                openPopup(false)
            }) { Text("삭제") }
        }, dismissButton = {
            Button(onClick = {
                openPopup(false)
            }) { Text("취소") }
        })

    val (selected, setSelected) = remember(calculation = { mutableStateOf(0) })
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when(selected) {
                // 메뉴
                ScrapDetailUI.SCRAP_MENU -> LinkScrapMenuSheet(sheetState, coroutineScope) {
                    when (it) {
                        // 링크 수정
                        R.id.link_update -> {
                            coroutineScope.launch { sheetState.hide() }

                            coroutineScope.launch {
                                setSelected(ScrapDetailUI.SCRAP_LINK_UPDATE)
                                delay(1500)
                                sheetState.show()
                            }

                        }
                        // 링크 삭제
                        R.id.link_delete -> openPopup(true)
                        else -> {}
                    }
                }
                // 알람 등록
                ScrapDetailUI.SCRAP_ALARM -> ScrapAlarmBottomSheet(sheetState, coroutineScope, viewModel, linkInfo)
                // 링크 수정
                ScrapDetailUI.SCRAP_LINK_UPDATE -> ScrapLinkBottomSheet(sheetState, coroutineScope, viewModel, linkInfo)
            }
            //DLog.e("SHEET_CONTENT", "selected: $selected")
        },
        modifier = Modifier.fillMaxSize()) {

        Scaffold(topBar = { },
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()) {

            val middleTopPadding = 20.dp

            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White)) {

                // content
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)) {

                    val painter = rememberGlidePainter(request = linkInfo.imgURL.also { DLog.e("scrap",it) })
                    LaunchedEffect(painter) {
                        snapshotFlow { painter.loadState }
                            .filter { it.isFinalState() }
                            .collect {
                                when(it){
                                    is ImageLoadState.Empty, is ImageLoadState.Loading, is ImageLoadState.Error -> { painter.request = R.drawable.img_link_detail_placeholder }
                                    else -> {}
                                }
                            }
                    }

                    // top header
                    Image(painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center)

                    // body
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .absoluteOffset(y = -middleTopPadding)) {

                        Box(Modifier.padding(horizontal = 24.dp)) {
                            Image(painter= painterResource(id = R.drawable.ic_jubjub),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp))
                        }

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .absoluteOffset(y = middleTopPadding)) {

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 72.dp, end = 24.dp)
                                .height(44.dp)) {

                                Text(linkInfo.author,
                                    color = Color.Gray,
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0x878D91)),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .background(Color.White))

                                Spacer(Modifier.weight(1f))

                                Box(modifier = Modifier
                                    .size(44.dp)
                                    .fillMaxHeight()
                                    .noRippleClickable {
                                        /**
                                         * 메뉴 바텀 시트
                                         */
                                        coroutineScope.launch {
                                            setSelected(ScrapDetailUI.SCRAP_MENU)
                                            sheetState.show()
                                        }
                                    }) {

                                    Image(painter = painterResource(id = R.drawable.ic_gray_more),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(Color.White)
                                            .align(Alignment.Center))
                                }
                            }

                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 48.dp, bottom = 16.dp)) {

                                /**
                                 * 타이틀
                                 */
                                Text(linkInfo.linkTitle,
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 18.sp, lineHeight = 22.5.sp, color = Color(0xFF292A2B)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp))


                                Spacer(modifier = Modifier.height(8.dp))

                                Text(linkInfo.description,
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF878D91)),
                                    maxLines = 3,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp))

                                Spacer(modifier = Modifier.height(10.dp))

                                // MultiLineTagList(linkInfo.hashtags, contentPadding = PaddingValues(24.dp, 0.dp))
                                SingleLineTagList(linkInfo.hashtags, contentPadding = PaddingValues(24.dp, 0.dp))

                                Spacer(Modifier.weight(1f))

                                Card(elevation = 0.dp,
                                    modifier = Modifier
                                        .height(26.dp)
                                        .padding(start = 24.dp, end = 24.dp)
                                        .noRippleClickable {
                                            /**
                                             * 알람 등록 바텀 시트
                                             */
                                            coroutineScope.launch {
                                                setSelected(ScrapDetailUI.SCRAP_ALARM)
                                                sheetState.show()
                                            }
                                        }) {

                                    Column {

                                        Spacer(Modifier.height(5.dp))

                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(21.dp)) {

                                            Image(painter = painterResource(id = R.drawable.ic_link_alram_img),
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp))

                                            Spacer(Modifier.width(4.dp))

                                            Text("이 링크는 따로 알람을 받고싶어요!",
                                                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)))

                                        }
                                    }
                                }

                                Spacer(Modifier.height(20.dp))

                                Button(onClick = { DLog.e("Jackson", "click read button") },
                                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp)
                                        .padding(horizontal = 24.dp)
                                        .clickable { viewModel.moveWebViewPage(linkData = linkInfo) }) {

                                    Text("바로 읽기!",
                                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W700)), fontSize = 14.sp, lineHeight = 17.5.sp),
                                        textAlign = TextAlign.Center)
                                }


                            }
                        }
                    }
                }

                // close
                Box(modifier= Modifier
                    .size(64.dp)
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .noRippleClickable { clickListener(R.id.activity_close) }) {

                    Image(painter = painterResource(id = R.drawable.ic_gray_close),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp))
                }
            }
        }

    }
}


/**
 * 링크 등록, 수정, 삭제 메뉴 시트
 */
@ExperimentalMaterialApi
@Composable
fun LinkScrapMenuSheet(sheetState: ModalBottomSheetState, coroutineScope: CoroutineScope, clickListener: (Int)->Unit) {
    Column(Modifier.fillMaxWidth()
        .heightIn(min = 164.dp, max = 228.dp)
        .padding(24.dp)) {

        ScrapMenuBtn("링크 수정") {
            coroutineScope.launch {
                clickListener(R.id.link_update)
                sheetState.hide()
            }
        }

        Spacer(Modifier.height(12.dp))

        ScrapMenuBtn("링크 삭제") {
            coroutineScope.launch {
                clickListener(R.id.link_delete)
                sheetState.hide()
            }
        }

        Spacer(Modifier.height(12.dp))

        ScrapMenuBtn("취소", Color.White, Color.Black) {
            coroutineScope.launch { sheetState.hide() }
        }
    }
}

/* BottomSheet */
@ExperimentalMaterialApi
@Composable
fun ScrapLinkBottomSheet(sheetState : ModalBottomSheetState, coroutineScope : CoroutineScope, viewModel: ScrapDetailViewModel, linkData: LinkData) {

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
                    backgroundColor = tag.tagColor.bgColor,
                    elevation = 0.dp) {

                    Box(contentAlignment = Alignment.Center){
                        Row(modifier = Modifier.padding(8.dp,0.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically){

                            Text(text = "#${tag.hashtagName}",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = tag.tagColor.textColor,
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
                // 링크 수정
                if(!linkUrl.isNullOrEmpty()) {
                    viewModel.updateLink(LinkRegisterEntity(
                        linkURL = linkUrl,
                        hashtags = ArrayList(hashtags.map { it.hashtagName }))
                    ){
                        coroutineScope.launch {
                            toast(ctx,"링크가 저장되었습니다!")
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

@Composable
fun ScrapMenuBtn(menuName: String, backgroundColor: Color = Color(0xFFEAF1FE), textColor: Color = Color(0xFF4076F6), clickListener: ()->Unit) {
    Card(shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .noRippleClickable(onClick = clickListener)) {

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {

            Text(text = menuName,
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 14.sp, lineHeight = 17.5.sp, color = textColor),
                textAlign = TextAlign.Center)

        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ScrapAlarmBottomSheet(sheetState : ModalBottomSheetState, coroutineScope: CoroutineScope, viewModel: ScrapDetailViewModel, linkData: LinkData) {
    // in Column Scope
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(606.dp)) {

        val ctx = LocalContext.current
        val currentPickDate = remember { mutableStateOf(Calendar.getInstance().clearMillis()) }
        val datePickerItems = DateUtil.getDateList()
        val dateStr = currentPickDate.value.getAlarmDateStr()

        val dateLastStr = "에 알림이 울려요!"
        val alarmDateStr = "${dateStr}${dateLastStr}"

        // 닫기 버튼
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {

            BottomSheetCloseBtn(painterResource(id = R.drawable.ic_close)){
                coroutineScope.launch { sheetState.hide() }
            }
        }

        // Alarm Guide Message
        Column(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 24.dp)) {

            Text(text = "이 아티클은 \n언제 읽으실건가요?",
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)),
                textAlign = TextAlign.Start)

            Spacer(Modifier.height(8.dp))

            AnnotatedString(
                text = alarmDateStr,
                spanStyles = listOf(

                    AnnotatedString.Range(SpanStyle(
                        color = Color(0xFF292A2B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700))
                    ), 0, dateStr.length - 1),

                    AnnotatedString.Range(SpanStyle(
                        color = Color(0xFF292A2B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W700))
                    ), dateStr.length, alarmDateStr.length - 1)

                )
            ).let { Text(text = it) }
        }


        // Date Picker
        CustomDatePicker(pickDate = currentPickDate.value, items = datePickerItems, onClickListener = { idx: Int, date: Pair<String, Calendar> ->
            currentPickDate.value = date.second
        })

        // Time Picker
        CustomTimePicker(date = currentPickDate.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(horizontal = 24.dp, vertical = 20.dp)) { type, timeVal ->

            currentPickDate.value = when (type) {
                Calendar.AM_PM -> currentPickDate.value.apply { set(Calendar.AM_PM, timeVal) }
                Calendar.HOUR -> currentPickDate.value.apply { set(Calendar.HOUR, timeVal) }
                Calendar.MINUTE -> currentPickDate.value.apply { set(Calendar.MINUTE, timeVal) }
                else -> currentPickDate.value
            }
        }

        Spacer(Modifier.weight(1f))

        // 삭제, 저장하기 버튼
        Column(
            Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {

            Row(Modifier.fillMaxSize()) {

                /**
                 * 링크 삭제하기
                 */
                if (linkData.linkId >= 0) Row(
                    Modifier
                        .width(64.dp)
                        .fillMaxHeight()) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color(0xFF4076F6)),
                        shape = RoundedCornerShape(4.dp),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        border = BorderStroke(width = 1.dp, Color(0xFF4076F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            DLog.e("Jackson", "save click read button")
                            coroutineScope.launch { sheetState.hide() }
                        }) {

                        Image(painter = painterResource(id = R.drawable.ic_blue_trash),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp))

                    }
                }

                /**
                 * 링크 저장하기
                 */
                Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                    shape = RoundedCornerShape(4.dp),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    onClick = {
                        DLog.e("Jackson", "save click read button")
                        coroutineScope.launch {
                            viewModel.addPersonalLinkAlarm(ctx, currentPickDate.value)
                            sheetState.hide()
                        }
                    }) {

                    Text("저장하기",
                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 14.sp, lineHeight = 17.5.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())

                }
            }
        }
    }
}

@Composable
fun SingleLineTagList(tags: ArrayList<LinkHashData>, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    // 링크 해시태그
    LazyRow(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = contentPadding){

        items(tags) { tag ->
            MainHashtagCard(tagName = tag.hashtagName, backColor = tag.tagColor.bgColor, textColor = tag.tagColor.textColor)
        }
    }
}

@Composable
fun MultiLineTagList(tags: List<String>, horizontalItemLimit: Int = 10, backgroundColor: Color = Color(0xFFAAAAAA), textColor: Color = Color(0xFFAAAAAA), contentPadding: PaddingValues = PaddingValues(0.dp)) {

    val colors = remember { mutableStateOf(arrayListOf<TagColor>().apply {
        tags.forEach { TagColor(backgroundColor, textColor) }
    }) }

    val firstLineLen = remember {
        mutableStateOf(
            if (tags.size <= horizontalItemLimit) tags.size
            else horizontalItemLimit)
    }

    val secondLineLen = remember {
        mutableStateOf(
            if (horizontalItemLimit < tags.size && tags.size >= horizontalItemLimit * 2) horizontalItemLimit * 2
            else tags.size)
    }

    Column {

        CoroutineScope(Dispatchers.Main).launch {
            arrayListOf<TagColor>().apply {
                tags.forEach { add(CommonUtil.getRandomeTagColor()) }
            }.let { colors.value = it }
        }

        TagList(tags.subList(0, firstLineLen.value), colors, contentPadding)
        if (secondLineLen.value > 0) {
            Spacer(Modifier.height(12.dp))
            TagList(tags.subList(horizontalItemLimit, secondLineLen.value), colors, contentPadding)
        }
    }
}


@Composable
fun TagList(tags: List<String>, colors: MutableState<ArrayList<TagColor>>, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    if (colors.value.size > 0) LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = contentPadding) {
        itemsIndexed(tags) { idx, tag ->
            colors.value[idx].let { tagColor ->
                TagView(idx, tag, tagColor.bgColor, tagColor.textColor)
            }
        }
    }
}

@Composable
fun TagView(idx: Int, tagStr: String, backgroundColor: Color = Color(0xFFAAAAAA), textColor: Color = Color(0xFFAAAAAA)) {
    val colors = remember { mutableStateOf(TagColor(backgroundColor, textColor)) }
    Card(Modifier.clickable { DLog.e("Jackson", "idx: $idx, click, Tag: $tagStr")  },
        shape = RoundedCornerShape(2.dp),
        elevation = 0.dp,
        backgroundColor = colors.value.bgColor) {

        Column(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)) {

            Text(tagStr,
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 2.sp, color = Color(0xFFE88484)),
                color = colors.value.textColor,
                modifier = Modifier
                    .height(12.dp)
                    .absolutePadding(0.dp, 0.dp, 0.dp, 0.dp), maxLines = 1)

        }
    }
}