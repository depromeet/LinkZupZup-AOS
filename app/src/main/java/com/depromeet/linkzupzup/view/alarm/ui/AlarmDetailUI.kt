package com.depromeet.linkzupzup.view.alarm.ui

import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.depromeet.linkzupzup.architecture.presenterLayer.AlarmDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.*
import com.depromeet.linkzupzup.ui.theme.BottomSheetShape
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.ui.theme.Round2RectShape
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.custom.CustomSwitchCompat
import com.depromeet.linkzupzup.view.custom.CustomTextCheckBox
import com.depromeet.linkzupzup.view.custom.CustomTimePicker
import com.depromeet.linkzupzup.view.custom.CustomToogle
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class AlarmDetailUI(var clickListener: (Int)->Unit = {}): BaseView<AlarmDetailViewModel>() {

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color(0xFFF8FAFB)) {
                vm?.run {
                    val alarmList by alarmList.observeAsState(arrayListOf())

                    // BodyContent(alarmList)
                    AlarmDetailBodyContent(alarmList, backClickListener = clickListener)
                }
            }
        }
    }

}

@Composable
fun AlarmDetailAppBar(appBarColor: MutableState<Color> = remember { mutableStateOf(Color(0xFFF8FAFB)) }, clickListener: (Int)->Unit = {}) {
    val ctx = LocalContext.current
    TopAppBar(title = { },
        navigationIcon = {
            Card(elevation = 0.dp,
                backgroundColor = Color(0xFFF8FAFB),
                modifier = Modifier.noRippleClickable { clickListener(R.drawable.ic_detail_back) }) {

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.size(40.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_detail_back),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))

                }
            }
        },
        backgroundColor = appBarColor.value,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 12.dp))
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AlarmDetailBodyContent(alarms: ArrayList<WeeklyAlarm>, backClickListener: (Int)->Unit = {}) {
    val alarmList = remember { mutableStateOf(alarms) }
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = { AlarmDetailModalBottomSheetContent(sheetState,coroutineScope) },
        modifier = Modifier.fillMaxSize()) {

        Scaffold(topBar = { AlarmDetailAppBar(clickListener = backClickListener) },
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {

                val columnModifier = if (alarmList.value.size > 0) Modifier
                    .fillMaxWidth()
                    .weight(1f)
                else Modifier.fillMaxWidth()

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = columnModifier) {

                    itemsWithHeaderIndexed (
                        items = alarmList.value,
                        useHeader = true,
                        headerContent = { TopHeaderCard() }) { idx, alarm ->
                            WeeklyAlarmCard(alarmList, idx)
                    }
                }
                if (alarmList.value.size <= 0) EmptyGuideCard(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp), contentAlignment = Alignment.BottomCenter) {

                    Button(onClick = { coroutineScope.launch { sheetState.show() } },
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                        shape = RoundedCornerShape(4.dp),
                        elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)) {

                        Text("알림 추가하기",
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 14.sp, lineHeight = 17.5.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())

                    }
                }
            }

        }
    }
}

@Composable
fun EmptyGuideCard(modifier: Modifier = Modifier) {
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

        Text("잊어버리지 않고 링크를 읽을 수 있도록\n원하는 시간에 알림을 받아보세요!\n\n\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47 ",
            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp),
            textAlign = TextAlign.Center,
            color = Color(0xFF4076F6))

    }
}

@Composable
fun TopHeaderCard() {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = Color(0xFFF8FAFB))
            .height(96.dp)) {

        Text("칠성파님,\n알람을 설정하시겠어요?",
            color = Color(0xFF292A2B),
            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)))

    }
}

@ExperimentalFoundationApi
@Composable
fun WeeklyAlarmCard(list: MutableState<ArrayList<WeeklyAlarm>>, index: Int) {
    list.value[index].let { alarm ->

        val ctx = LocalContext.current
        // 알람 설정 유무
        val enableAlarm: MutableState<Boolean> = alarm.isEnableAlarm().mutableStateValue()
        alarm.enableAlarm = if (enableAlarm.value) 1 else 0
        val switchCompat: MutableState<SwitchCompat?> = remember { mutableStateOf(null) }

        Card(shape = RoundedCornerShape(4.dp),
            backgroundColor = Color.White,
            elevation = 2.dp,
            modifier = Modifier.combinedClickable(onClick = {
                switchCompat.let { it.value?.isChecked = it.value?.isChecked?.not() ?: false }
            }, onLongClick = {
                toast(ctx, "Long Click")
            })) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(110.dp)) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 24.dp, vertical = 19.dp)) {

                    val textColor = Color(if (enableAlarm.value) 0xFF292A2B else 0xFFCED3D6)
                    val weeksTagBgColor = Color(if (enableAlarm.value) 0xFFEAEBEF else 0xFFF8FAFB)
                    val weeksTagTextColor = Color(if (enableAlarm.value) 0xFF000000 else 0xFFCED3D6)
                    val holidayTagBgColor = Color(if (enableAlarm.value) 0xFFFFEFF0 else 0xFFF8FAFB)
                    val holidayTagTextColor = Color(if (enableAlarm.value) 0xFFF24147 else 0xFFCED3D6)

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)) {

                        Image(painter = painterResource(id = R.drawable.ic_sun_img),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp))

                        Spacer(Modifier.width(4.dp))

                        // 오전 or 오후
                        Text(alarm.dateTime.timeBaseStr(),
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = textColor))

                        Spacer(Modifier.width(8.dp))

                        // 알람 시간
                        Text(alarm.dateTime.timeStr(),
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), textAlign = TextAlign.Start, fontSize = 32.sp, lineHeight = 10.sp, color = textColor),
                            modifier = Modifier.absoluteOffset(y = -(5).dp))

                        Spacer(Modifier.weight(1f))

                        Column(horizontalAlignment = Alignment.End,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 10.dp)) {

                            CustomSwitchCompat(instanceCallback = {
                                    switchCompat.value = it
                                    it.isChecked = enableAlarm.value
                                }, checkedOnChangeListener = { view, isChecked ->
                                    enableAlarm.value = isChecked
                                })
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(22.dp)) {

                        Card(shape = RoundedCornerShape(2.dp),
                            backgroundColor = weeksTagBgColor) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(alarm.weekDayStr(),
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = weeksTagTextColor),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .height(22.dp)
                                        .padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }

                        if (alarm.isHolidayUse == 1) {

                            Spacer(Modifier.width(8.dp))

                            Card(shape = Round2RectShape,
                                backgroundColor = holidayTagBgColor) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("#공휴일에 울려요",
                                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = holidayTagTextColor),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .height(22.dp)
                                            .padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AlarmDetailBottomSheet(bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(606.dp)
        .background(Color.White)) {

        val ctx = LocalContext.current

        // header, close btn
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {

            Card(elevation = 0.dp,
                modifier = Modifier
                    .width(68.dp)
                    .height(56.dp)
                    .noRippleClickable {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 16.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_black_close),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
            }
        }

        // guide title
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 28.dp)) {

            Text("언제마다\n알림을 받으시겠어요?",
                color = Color(0xFF292A2B),
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)))
        }

        // timepicker
        val timeDate = remember { mutableStateOf(Calendar.getInstance()) }
        CustomTimePicker(modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(horizontal = 24.dp, vertical = 20.dp)) { type, timeVal ->

            when (type) {
                Calendar.AM_PM -> timeDate.value.apply { set(Calendar.AM_PM, timeVal) }
                Calendar.HOUR -> timeDate.value.apply { set(Calendar.HOUR, timeVal) }
                Calendar.MINUTE -> timeDate.value.apply { set(Calendar.MINUTE, timeVal) }
                else -> timeDate.value
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = 24.dp, vertical = 20.dp)) {

            Text("반복 설정",
                color = Color(0xFF292A2B),
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)),
                modifier = Modifier.height(16.dp))

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)) {

                val toogleValues = arrayListOf("주중", "주말")
                CustomToogle(modifier = Modifier.height(28.dp), datas = toogleValues, onChangeListener = {
                    DLog.e("TEST", toogleValues[it])
                })

                Spacer(Modifier.weight(1f))

                CustomTextCheckBox(modifier = Modifier.height(20.dp),
                    text = "공휴일엔 알람 끄기",
                    enableImg = R.drawable.ic_holiday_on,
                    disableImg = R.drawable.ic_holiday_off,
                    enableColor = Color(0xFF4076F6),
                    disableColor = Color(0xFF878D91),
                    onChangeListener = {
                        DLog.e("TEST", if (it) "공휴일 알람 비활성" else "공휴일 알람 활성")
                    })

            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(start = 24.dp, top = 0.dp, end = 16.dp, bottom = 24.dp)) {

            Row(Modifier.fillMaxSize()) {

                val isAlready = remember { mutableStateOf(false) }
                if (isAlready.value) Row(
                    Modifier
                        .width(64.dp)
                        .fillMaxHeight()) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color(0xFF4076F6)),
                        shape = RoundedCornerShape(4.dp),
                        elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        border = BorderStroke(width = 1.dp, Color(0xFF4076F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            DLog.e("Jackson", "save click read button")
                            coroutineScope.launch {
                                Toast.makeText(ctx, "삭제", Toast.LENGTH_SHORT).show()
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
                    elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    onClick = {
                        DLog.e("Jackson", "save click read button")
                        coroutineScope.launch {
                            Toast.makeText(ctx, "저장하기", Toast.LENGTH_SHORT).show()
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

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AlarmDetailModalBottomSheetContent(bottomSheetState: ModalBottomSheetState, coroutineScope: CoroutineScope) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(606.dp)
        .background(Color.White)
        .noRippleClickable { }) {

        val ctx = LocalContext.current

        // header, close btn
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {

            Card(elevation = 0.dp,
                modifier = Modifier
                    .width(68.dp)
                    .height(56.dp)
                    .noRippleClickable {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    }) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 16.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_black_close),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
            }
        }

        // guide title
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 28.dp)) {

            Text("언제마다\n알림을 받으시겠어요?",
                color = Color(0xFF292A2B),
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)))
        }

        // timepicker
        val timeDate = remember { mutableStateOf(Calendar.getInstance()) }
        CustomTimePicker(modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(horizontal = 24.dp, vertical = 20.dp)) { type, timeVal ->
            when (type) {
                Calendar.AM_PM -> timeDate.value.apply { set(Calendar.AM_PM, timeVal) }
                Calendar.HOUR -> timeDate.value.apply { set(Calendar.HOUR, timeVal) }
                Calendar.MINUTE -> timeDate.value.apply { set(Calendar.MINUTE, timeVal) }
                else -> timeDate.value
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = 24.dp, vertical = 20.dp)) {

            Text("반복 설정",
                color = Color(0xFF292A2B),
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)),
                modifier = Modifier.height(16.dp))

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)) {

                val toogleValues = arrayListOf("주중", "주말")
                CustomToogle(modifier = Modifier.height(28.dp), datas = toogleValues, onChangeListener = {
                    DLog.e("TEST", toogleValues[it])
                })

                Spacer(Modifier.weight(1f))

                CustomTextCheckBox(modifier = Modifier.height(20.dp),
                    text = "공휴일엔 알람 끄기",
                    enableImg = R.drawable.ic_holiday_on,
                    disableImg = R.drawable.ic_holiday_off,
                    enableColor = Color(0xFF4076F6),
                    disableColor = Color(0xFF878D91),
                    onChangeListener = {
                        DLog.e("TEST", if (it) "공휴일 알람 비활성" else "공휴일 알람 활성")
                    })

            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(start = 24.dp, top = 0.dp, end = 16.dp, bottom = 24.dp)) {

            Row(Modifier.fillMaxSize()) {

                val isAlready = remember { mutableStateOf(false) }
                if (isAlready.value) Row(
                    Modifier
                        .width(64.dp)
                        .fillMaxHeight()) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color(0xFF4076F6)),
                        shape = RoundedCornerShape(4.dp),
                        elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        border = BorderStroke(width = 1.dp, Color(0xFF4076F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(end = 12.dp),
                        onClick = {
                            DLog.e("Jackson", "save click read button")
                            coroutineScope.launch {
                                Toast.makeText(ctx, "삭제", Toast.LENGTH_SHORT).show()
                                bottomSheetState.hide()
                            }
                        }) {

                        Image(painter = painterResource(id = R.drawable.ic_blue_trash),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp))

                    }

                }

                Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                    shape = RoundedCornerShape(4.dp),
                    elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    onClick = {
                        DLog.e("Jackson", "save click read button")
                        coroutineScope.launch {
                            Toast.makeText(ctx, "저장하기", Toast.LENGTH_SHORT).show()
                            bottomSheetState.hide()
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun previewSample() {

    val listDatas = arrayListOf<WeeklyAlarm>().apply {
        add(WeeklyAlarm(dateTime = "2021-04-28 08:30:00", isWeekday = 0, isHolidayUse = 1, enableAlarm = 1))
        add(WeeklyAlarm(dateTime = "2021-04-28 08:30:00", isWeekday = 1, isHolidayUse = 1, enableAlarm = 0))
        add(WeeklyAlarm(dateTime = "2021-04-28 08:30:00", isWeekday = 2, isHolidayUse = 0, enableAlarm = 1))
    }

    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            AlarmDetailBodyContent(listDatas)
        }
    }
}