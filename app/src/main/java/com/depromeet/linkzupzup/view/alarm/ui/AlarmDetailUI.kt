package com.depromeet.linkzupzup.view.alarm.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.*
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
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.mutableStateValue
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.extensions.timeBaseStr
import com.depromeet.linkzupzup.extensions.timeStr
import com.depromeet.linkzupzup.presenter.AlarmDetailViewModel
import com.depromeet.linkzupzup.presenter.model.WeeklyAlarm
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.custom.CustomSwitchCompat
import com.depromeet.linkzupzup.view.custom.CustomTextCheckBox
import com.depromeet.linkzupzup.view.custom.CustomTimePicker
import com.depromeet.linkzupzup.view.custom.CustomToogle
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class AlarmDetailUI: BaseView<AlarmDetailViewModel>() {

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color(0xFFF8FAFB)) {
                vm?.getWeeklyAlarmList()?.let { alarmList ->
                    BodyContent(alarmList)
                }
            }
        }
    }

}

@Composable
fun AlarmDetailAppBar(appBarColor: MutableState<Color> = remember { mutableStateOf(Color(0xFFF8FAFB)) }) {
    val ctx = LocalContext.current
    TopAppBar(title = { },
        navigationIcon = {
            Card(elevation = 0.dp,
                backgroundColor = Color(0xFFF8FAFB),
                modifier = Modifier.noRippleClickable {
                    Toast.makeText(ctx, "뒤로가기", Toast.LENGTH_SHORT).show()
                }) {

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


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun BodyContent(alarms: ArrayList<WeeklyAlarm>) {
    val alarmList = remember { mutableStateOf(alarms) }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        topBar = { AlarmDetailAppBar() },
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { AlarmDetailBottomSheet(bottomSheetScaffoldState,coroutineScope) },   // sheetContent -  Column scope
        sheetShape = RoundedCornerShape(topStartPercent = 5,topEndPercent = 5),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Color(0xFFF8FAFB),
        sheetGesturesEnabled = false,
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            TopHeaderCard()

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {

                itemsIndexed(items= alarmList.value) { index, alarm ->
                    WeeklyAlarmCard(alarmList, index)
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)) {

                Button(onClick = {
                        DLog.e("Jackson", "click read button")
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
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

@Composable
fun WeeklyAlarmCard(list: MutableState<ArrayList<WeeklyAlarm>>, index: Int) {
    list.value[index].let { alarm ->

        // 알람 설정 유무
        val enableAlarm: MutableState<Boolean> = alarm.isEnableAlarm().mutableStateValue()
        alarm.enableAlarm = if (enableAlarm.value) 1 else 0

        Card(shape = RoundedCornerShape(4.dp),
            backgroundColor = Color.White,
            elevation = 2.dp,
            modifier = Modifier.clickable {

            }) {

            Box(Modifier.fillMaxWidth()
                .height(110.dp)) {

                Column(modifier = Modifier.fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 24.dp, vertical = 19.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .height(40.dp)) {

                        Image(painter = painterResource(id = R.drawable.ic_sun_img),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp))

                        Spacer(Modifier.width(4.dp))

                        Text(alarm.dateTime.timeBaseStr(),
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)))

                        Spacer(Modifier.width(8.dp))

                        Text(alarm.dateTime.timeStr(),
                            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), textAlign = TextAlign.Start, fontSize = 32.sp, lineHeight = 10.sp, color = Color(0xFF292A2B)),
                            modifier = Modifier.absoluteOffset(y = -(5).dp))

                    }

                    Spacer(Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth()
                        .height(22.dp)) {

                        Card(shape = RoundedCornerShape(2.dp),
                            backgroundColor = Color(0xFFEAEBEF)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(alarm.weekDayStr(),
                                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = Color(0xFF000000)),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .height(22.dp)
                                        .padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }

                        if (alarm.isHolidayUse == 1) {

                            Spacer(Modifier.width(8.dp))

                            Card(shape = RoundedCornerShape(2.dp),
                                backgroundColor = Color(0xFFFFEFF0)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("#공휴일에 울려요",
                                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = Color(0xFFF24147)),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .height(22.dp)
                                            .padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }
                        }
                    }
                }

                /**
                 * 비활성화 블러
                 */
                if (!enableAlarm.value) Row(Modifier.fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color(0xCCFFFFFF))) {}

                /**
                 * 카드가 비활성화 될경우 블러 위에 스위치가 표현되어야 하므로 상위 레이어에 배치.
                 */
                Column(horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxWidth()
                        .height(110.dp)
                        .padding(top = 29.dp, end = 24.dp)) {

                    CustomSwitchCompat(instanceCallback = { it.isChecked = enableAlarm.value },
                        checkedOnChangeListener = { view, isChecked ->
                            enableAlarm.value = isChecked
                        })
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
            modifier = Modifier.fillMaxWidth()
                .height(56.dp)) {

            Card(elevation = 0.dp,
                modifier = Modifier.width(68.dp)
                    .height(56.dp)
                    .noRippleClickable {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }) {

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
        CustomTimePicker(modifier = Modifier.fillMaxWidth()
            .height(210.dp)
            .padding(horizontal = 24.dp, vertical = 20.dp)) { amPm, hour, minute ->
            DLog.e("TEST", "amPm: $amPm, hour: $hour, minute: $minute")
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

        Column(Modifier.fillMaxWidth()
            .height(68.dp)
            .padding(start = 24.dp, top = 0.dp, end = 16.dp, bottom = 24.dp)) {

            Row(Modifier.fillMaxSize()) {

                val isAlready = remember { mutableStateOf(false) }
                if (isAlready.value) Row(Modifier.width(64.dp)
                    .fillMaxHeight()) {

                    Button(colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color(0xFF4076F6)),
                        shape = RoundedCornerShape(4.dp),
                        elevation = elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                        border = BorderStroke(width = 1.dp, Color(0xFF4076F6)),
                        modifier = Modifier.fillMaxWidth().height(52.dp).padding(end = 12.dp),
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
                    modifier = Modifier.weight(1f).height(52.dp),
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

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun previewSample() {

    val listDatas = arrayListOf<WeeklyAlarm>().apply {
        add(WeeklyAlarm("2021-04-28 08:30:00", 0, 1, 1))
        add(WeeklyAlarm("2021-04-28 08:30:00", 1, 1, 0))
        add(WeeklyAlarm("2021-04-28 08:30:00", 2, 0, 1))
    }

    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            BodyContent(listDatas)
        }
    }
}