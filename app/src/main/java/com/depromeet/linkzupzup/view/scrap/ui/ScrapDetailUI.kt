package com.depromeet.linkzupzup.view.scrap.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.extensions.toast
import com.depromeet.linkzupzup.presenter.ScrapDetailViewModel
import com.depromeet.linkzupzup.presenter.model.TagColor
import com.depromeet.linkzupzup.ui.theme.BottomSheetShape
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.utils.DateUtil
import com.depromeet.linkzupzup.view.custom.*
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ScrapDetailUI: BaseView<ScrapDetailViewModel>() {

    companion object {
        // menu
        const val SCRAP_ALARM_MENU_REGISTERED_TYPE = 0      // 링크가 등록된 상태 ( 링크 수정 )
        const val SCRAP_ALARM_MENU_UNREGISTERED_TYPE = 1    // 링크가 등록되지 않은 상태 ( 링크 등록 )

        // register or update
        const val SCRAP_ALARM_REGISTER_TYPE = 2             // 링크가 등록되지 않은 상태 ( 링크 등록으로 진입 )
        const val SCRAP_ALARM_UPDATE_TYPE = 3               // 링크가 등록된 상태 ( 링쿠 수정으로 진입 )
    }

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                bottomSheetTest(vm)
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun previewSample() {
    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            bottomSheetTest()
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun bottomSheetTest(viewModel: ScrapDetailViewModel? = null) {

    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val bottomSheetType = remember { mutableStateOf(ScrapDetailUI.SCRAP_ALARM_REGISTER_TYPE) }

    MultiBottomSheet(bottomSheetScaffoldState, coroutineScope) { currentBottomSheet: BottomSheetScreen?, closeSheet: () -> Unit, openSheet: (BottomSheetScreen) -> Unit ->

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                currentBottomSheet?.let { currentSheet ->
                    BottomSheetLayout(bottomSheetScaffoldState, coroutineScope, currentSheet)
                }
            },
            sheetShape = BottomSheetShape,
            sheetPeekHeight = 0.dp,
            sheetGesturesEnabled = false,
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

                    // top header
                    Image(painter = painterResource(id = R.drawable.ic_scrap_detail_top_bg),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth())

                    // body
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .absoluteOffset(y = -middleTopPadding)) {

                        Box(Modifier.padding(horizontal = 24.dp)) {
                            Image(painter= painterResource(id = R.drawable.ic_scrap_profile_img),
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

                                Text("글쓴이",
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
                                        bottomSheetType.value =
                                            ScrapDetailUI.SCRAP_ALARM_MENU_UNREGISTERED_TYPE
                                        openSheet(BottomSheetScreen.MenuScreen(bottomSheetType.value))
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

                                Text("스타트업과 안맞는 대기업 임원 DNA는 어떻게 찾아낼까?",
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 18.sp, lineHeight = 22.5.sp, color = Color(0xFF292A2B)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp))

                                Spacer(modifier = Modifier.height(8.dp))

                                Text("IT 기업을 중심으로 빠르게 사업을 실행을 하는 것이 사업의 중요한 경쟁력이 된다는 것은 이미 공감대가 만들어져 있다. 그리고 서바이벌을 고민해야 하는 스타트업에서는 그 중요성은 더욱더 크게 받...",
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF878D91)),
                                    maxLines = 3,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp))

                                Spacer(modifier = Modifier.height(20.dp))

                                MultiLineTagList(viewModel?.getTagList() ?: arrayListOf(), contentPadding = PaddingValues(24.dp, 0.dp))

                                Spacer(Modifier.weight(1f))

                                Card(elevation = 0.dp,
                                    modifier = Modifier
                                        .height(26.dp)
                                        .padding(start = 24.dp, end = 24.dp)
                                        .noRippleClickable {
                                            bottomSheetType.value =
                                                ScrapDetailUI.SCRAP_ALARM_REGISTER_TYPE
                                            openSheet(BottomSheetScreen.AlarmScreen(bottomSheetType.value))
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
                                        .padding(horizontal = 24.dp)) {
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
                    .clickable { toast(ctx, "닫기") }) {

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
fun ScrapMenuSheet(type: Int, bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope) {
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 164.dp, max = 228.dp)
            .padding(24.dp)) {

        val menuName = if (type == ScrapDetailUI.SCRAP_ALARM_MENU_REGISTERED_TYPE) "링크 수정" else "링크 등록"
        ScrapMenuBtn(menuName) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }

        Spacer(Modifier.height(12.dp))

        if (type == ScrapDetailUI.SCRAP_ALARM_MENU_REGISTERED_TYPE) {
            ScrapMenuBtn("링크 삭제") {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }

            Spacer(Modifier.height(12.dp))
        }

        ScrapMenuBtn("취소", Color.White, Color.Black) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
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
fun ScrapAlarmBottomSheet(type: Int, bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope, data: Any? = null) {
    // in Column Scope
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(606.dp)) {

        val ctx = LocalContext.current

        // 닫기 버튼
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {

            BottomSheetCloseBtn(painterResource(id = R.drawable.ic_close)){
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse() }
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

            val dateStr = "4월 10일 오전 12:50분"
            val dateLastStr = "에 알림이 울려요!"
            val alarmDateStr = "${dateStr}${dateLastStr}"

            AnnotatedString(
                text = "${dateStr}${dateLastStr}",
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
            ).let {
                Text(text = it)
            }
        }

        // Date Picker
        val datePickerItems = DateUtil.getDateList()
        val currentPickDate = remember { mutableStateOf(Calendar.getInstance()) }
        CustomDatePicker(pickDate = currentPickDate.value, items = datePickerItems, onClickListener = { idx: Int, date: Pair<String, Calendar> ->
            currentPickDate.value = date.second
        })

        // Time Picker
        CustomTimePicker(modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(horizontal = 24.dp, vertical = 20.dp)) { amPm, hour, minute ->
            DLog.e("TEST", "amPm: $amPm, hour: $hour, minute: $minute")
        }

        Spacer(Modifier.weight(1f))

        // 삭제, 저장하기 버튼
        Column(
            Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {

            Row(Modifier.fillMaxSize()) {

                if (type == ScrapDetailUI.SCRAP_ALARM_UPDATE_TYPE) Row(
                    Modifier
                        .width(64.dp)
                        .fillMaxHeight()) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color(0xFF4076F6)),
                        shape = RoundedCornerShape(4.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        ),
                        border = BorderStroke(width = 1.dp, Color(0xFF4076F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            DLog.e("Jackson", "save click read button")
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }) {

                        Image(painter = painterResource(id = R.drawable.ic_blue_trash),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp))

                    }
                }

                Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                    shape = RoundedCornerShape(4.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    onClick = {
                        DLog.e("Jackson", "save click read button")
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
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

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun BottomSheetLayout(bottomSheetScaffoldState: BottomSheetScaffoldState,
                coroutineScope: CoroutineScope,
                currentScreen: BottomSheetScreen) {

    when(currentScreen){
        is BottomSheetScreen.MenuScreen -> ScrapMenuSheet(currentScreen.type, bottomSheetScaffoldState, coroutineScope)
        is BottomSheetScreen.AlarmScreen -> ScrapAlarmBottomSheet(currentScreen.type, bottomSheetScaffoldState, coroutineScope)
    }
}

sealed class BottomSheetScreen {
    class MenuScreen(var type: Int): BottomSheetScreen()
    class AlarmScreen(val type: Int): BottomSheetScreen()
}