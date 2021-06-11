package com.depromeet.linkzupzup.view.alarm.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmUpdateEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.getReatDateStr
import com.depromeet.linkzupzup.architecture.presenterLayer.AlarmDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm.Companion.clone
import com.depromeet.linkzupzup.architecture.presenterLayer.model.getWeeklyday
import com.depromeet.linkzupzup.architecture.presenterLayer.model.getWeeklydayend
import com.depromeet.linkzupzup.architecture.presenterLayer.model.getWeeklyend
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.*
import com.depromeet.linkzupzup.ui.theme.BottomSheetShape
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.ui.theme.Round2RectShape
import com.depromeet.linkzupzup.view.custom.CustomSwitchCompat
import com.depromeet.linkzupzup.view.custom.CustomTextCheckBox
import com.depromeet.linkzupzup.view.custom.CustomTimePicker
import com.depromeet.linkzupzup.view.custom.WeeklyRepeatToggle
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
                vm?.let { viewModel ->
                    AlarmDetailBodyContent(viewModel= viewModel, clickListener = clickListener)
                }
            }
        }
    }

}

@Composable
fun AlarmDetailAppBar(appBarColor: MutableState<Color> = remember { mutableStateOf(Color(0xFFF8FAFB)) }, clickListener: (Int)->Unit = {}) {
    TopAppBar(title = { },
        navigationIcon = {
            Card(elevation = 0.dp,
                backgroundColor = Color(0xFFF8FAFB),
                modifier = Modifier.noRippleClickable { clickListener(R.id.activity_close) }) {

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
        modifier = Modifier.fillMaxWidth()
            .height(52.dp)
            .padding(start = 12.dp))
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AlarmDetailBodyContent(viewModel: AlarmDetailViewModel, clickListener: (Int) -> Unit = {}) {
    val alarmList by viewModel.alarmList.observeAsState(arrayListOf())
    val targetAlarm: MutableState<WeeklyAlarm> = remember { mutableStateOf(WeeklyAlarm()) }
    val (selected, setSelected) = remember(calculation = { mutableStateOf(0) })

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val userName = remember { mutableStateOf("") }
    viewModel.preference?.getUserName()?.let { name -> userName.value = name }

    ModalBottomSheetLayout(sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (selected) {
                0 -> AlarmDetailModalBottomSheetContent(sheetState,coroutineScope, viewModel, clickListener, WeeklyAlarm())
                1 -> AlarmDetailModalBottomSheetContent(sheetState,coroutineScope, viewModel, clickListener, targetAlarm.value)
            }
        },
        modifier = Modifier.fillMaxSize()) {

        Scaffold(topBar = { AlarmDetailAppBar(clickListener = clickListener) },
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()) {

                val columnModifier = if (alarmList.size > 0) Modifier
                    .fillMaxWidth()
                    .weight(1f) else Modifier.fillMaxWidth()

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = columnModifier) {

                    itemsWithHeaderIndexed (
                        items = alarmList,
                        useHeader = true,
                        headerContent = { TopHeaderCard(userName.value) }) { idx, alarm ->

                        WeeklyAlarmCard(index = idx, alarm = alarm, onClick = { item ->
                            coroutineScope.launch {
                                targetAlarm.value = item.clone()
                                setSelected(1)
                                sheetState.show()
                            }
                        }, switchCallback = { checked ->
                            alarm.enableAlarm = checked
                            viewModel.updateAlarm(alarm.alarmId, AlarmUpdateEntity(
                                enabled = alarm.enableAlarm,
                                notifyTime = alarm.dateTime,
                                repeatedDate = alarm.getRepeatedDate()
                            ))
                        })
                    }
                }

                // empty guide
                if (alarmList.size == 0) AlarmEmptyGuideCard(Modifier.fillMaxWidth()
                    .weight(1f))

                Box(modifier = Modifier.fillMaxWidth()
                    .height(68.dp)
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp), contentAlignment = Alignment.BottomCenter) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                        shape = RoundedCornerShape(4.dp),
                        elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        onClick = {
                            coroutineScope.launch {
                                setSelected(0)
                                sheetState.show()
                            }
                        }) {

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
fun AlarmEmptyGuideCard(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
            .padding(bottom = 24.dp)
            .background(Color.Transparent)) {

        // 도넛 이미지
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
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
fun TopHeaderCard(userName: String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .background(color = Color(0xFFF8FAFB))
            .height(96.dp)) {

        Text("${userName}님,\n알람을 설정하시겠어요?",
            color = Color(0xFF292A2B),
            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)))

    }
}

@ExperimentalFoundationApi
@Composable
fun WeeklyAlarmCard(index: Int, alarm: WeeklyAlarm, onClick: (item: WeeklyAlarm) -> Unit, switchCallback: (Boolean)->Unit) {

    // 알람 설정 유무
    val enableAlarm: MutableState<Boolean> = alarm.enableAlarm.mutableStateValue()
    val switchCompat: MutableState<SwitchCompat?> = remember { mutableStateOf(null) }

    Card(shape = RoundedCornerShape(4.dp),
        backgroundColor = Color.White,
        elevation = 2.dp,
        modifier = Modifier.combinedClickable(onClick = { onClick(alarm) })) {

        Box(Modifier.fillMaxWidth()
            .height(110.dp)) {

            Column(modifier = Modifier.fillMaxWidth()
                .height(110.dp)
                .padding(horizontal = 24.dp, vertical = 19.dp)) {

                val textColor = Color(if (enableAlarm.value) 0xFF292A2B else 0xFFCED3D6)
                val weeksTagBgColor = Color(if (enableAlarm.value) 0xFFEAEBEF else 0xFFF8FAFB)
                val weeksTagTextColor = Color(if (enableAlarm.value) 0xFF000000 else 0xFFCED3D6)
                val holidayTagBgColor = Color(if (enableAlarm.value) 0xFFFFEFF0 else 0xFFF8FAFB)
                val holidayTagTextColor = Color(if (enableAlarm.value) 0xFFF24147 else 0xFFCED3D6)

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .height(40.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_sun_img),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp))

                    Spacer(Modifier.width(4.dp))

                    // 오전 or 오후
                    Text(alarm.dateTime.getCalendar().timeBaseStr(),
                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = textColor))

                    Spacer(Modifier.width(8.dp))

                    // 알람 시간
                    Text(alarm.dateTime.getCalendar().timeStr(),
                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), textAlign = TextAlign.Start, fontSize = 32.sp, lineHeight = 10.sp, color = textColor),
                        modifier = Modifier.absoluteOffset(y = -(5).dp))

                    Spacer(Modifier.weight(1f))

                    Column(horizontalAlignment = Alignment.End,
                        modifier = Modifier.fillMaxHeight()
                            .padding(top = 10.dp)) {

                        CustomSwitchCompat(instanceCallback = {
                            switchCompat.value = it
                            it.isChecked = enableAlarm.value
                        }, checkedOnChangeListener = { view, isChecked ->
                            enableAlarm.value = isChecked
                            switchCallback(isChecked)
                        })
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()
                    .height(22.dp)) {

                    Card(shape = RoundedCornerShape(2.dp),
                        backgroundColor = weeksTagBgColor) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(alarm.weekDayStr(),
                                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = weeksTagTextColor),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.height(22.dp)
                                    .padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }

                    if (alarm.disableHoliday == 0) {

                        Spacer(Modifier.width(8.dp))

                        Card(shape = Round2RectShape,
                            backgroundColor = holidayTagBgColor) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("#공휴일에 울려요",
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = holidayTagTextColor),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.height(22.dp)
                                        .padding(horizontal = 8.dp, vertical = 4.dp))
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
fun AlarmDetailModalBottomSheetContent(bottomSheetState: ModalBottomSheetState, coroutineScope: CoroutineScope, viewModel: AlarmDetailViewModel, clickListener: (Int) -> Unit = {}, alarmData: WeeklyAlarm) {
    val alarmId = remember { mutableStateOf(-1) }
    val isNewRegist = remember { mutableStateOf(false) }
    val timeDate = remember { mutableStateOf(Calendar.getInstance()) }
    val weekdayend = remember { mutableStateOf(WeeklyAlarm.WEEKDAYS) }
    val holidayDisable = remember { mutableStateOf(false) }

    alarmId.value = alarmData.alarmId
    isNewRegist.value = alarmData.alarmId < 0
    timeDate.value = alarmData.dateTime.getCalendar()
    weekdayend.value = alarmData.weeklydayend
    holidayDisable.value = alarmData.disableHoliday.getBoolean()

    Column(modifier = Modifier.fillMaxWidth()
        .height(606.dp)
        .background(Color.White)) {

        // header, close btn
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .height(56.dp)) {

            Card(elevation = 0.dp,
                modifier = Modifier.width(68.dp)
                    .height(56.dp)
                    .noRippleClickable(coroutineScope) { bottomSheetState.hide() }) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                        .padding(start = 20.dp, end = 16.dp)) {

                    Image(painter = painterResource(id = R.drawable.ic_black_close),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
            }
        }

        // guide title
        Row(modifier = Modifier.fillMaxWidth()
            .padding(start = 24.dp, bottom = 28.dp)) {

            Text("언제마다\n알림을 받으시겠어요?",
                color = Color(0xFF292A2B),
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)))
        }

        // timepicker
        CustomTimePicker(date = timeDate.value,
            modifier = Modifier.fillMaxWidth()
                .height(210.dp)
                .padding(horizontal = 24.dp, vertical = 20.dp)) { type, timeVal ->

            when (type) {
                Calendar.AM_PM -> timeDate.value.apply { set(Calendar.AM_PM, timeVal) }
                Calendar.HOUR -> timeDate.value.apply { set(Calendar.HOUR, timeVal) }
                Calendar.MINUTE -> timeDate.value.apply { set(Calendar.MINUTE, timeVal) }
                else -> timeDate.value
            }
            alarmData.dateTime = timeDate.value.timeStr(isHourOfDay = true)
        }

        Column(Modifier.fillMaxWidth()
            .height(96.dp)
            .padding(horizontal = 24.dp, vertical = 20.dp)) {

            Text("반복 설정",
                color = Color(0xFF292A2B),
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)),
                modifier = Modifier.height(16.dp))

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .height(28.dp)) {

                WeeklyRepeatToggle(modifier = Modifier.height(28.dp), weekdayend = weekdayend.value) { weekday, weekend ->
                    weekdayend.value = getWeeklydayend(weekday, weekend)
                    alarmData.weeklydayend = weekdayend.value
                }

                Spacer(Modifier.weight(1f))

                CustomTextCheckBox(modifier = Modifier.height(20.dp),
                    holidayDisable = holidayDisable.value,
                    onChangeListener = {
                        alarmData.disableHoliday = it.getInt()
                        holidayDisable.value = it
                    })

            }
        }

        Spacer(Modifier.weight(1f))

        Column(Modifier.fillMaxWidth()
            .height(68.dp)
            .padding(start = 24.dp, top = 0.dp, end = 16.dp, bottom = 24.dp)) {

            Row(Modifier.fillMaxSize()) {

                if (alarmId.value >= 0) Row(Modifier.width(64.dp)
                    .fillMaxHeight()) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color(0xFF4076F6)),
                        shape = RoundedCornerShape(4.dp),
                        elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        border = BorderStroke(width = 1.dp, Color(0xFF4076F6)),
                        modifier = Modifier.fillMaxWidth().height(52.dp).padding(end = 12.dp),
                        onClick = {
                            viewModel.deleteAlarm(alarmId = alarmId.value) {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }
                                clickListener(R.id.alarm_delete)
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
                    modifier = Modifier.weight(1f).height(52.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (alarmData.alarmId < 0) {
                               /**
                                * 알람등록
                                */
                               viewModel.registAlarm(alarmInfo= AlarmUpdateEntity (
                                   enabled = true,
                                   notifyTime = timeDate.value.timeStr(isHourOfDay = true),
                                   repeatedDate = getReatDateStr(repeatValues = arrayListOf(alarmData.getWeeklyday(), alarmData.getWeeklyend()), disableHoliday = holidayDisable.value.getInt()))) {
                                   clickListener(R.id.alarm_save)
                               }
                            } else {
                                /**
                                 * 알람수정
                                 */
                                viewModel.updateAlarm(alarmId = alarmId.value, alarmInfo = AlarmUpdateEntity (
                                    enabled = true,
                                    notifyTime = timeDate.value.timeStr(isHourOfDay = true),
                                    repeatedDate = alarmData.getRepeatedDate())) {
                                    clickListener(R.id.alarm_update)
                                }
                            }
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