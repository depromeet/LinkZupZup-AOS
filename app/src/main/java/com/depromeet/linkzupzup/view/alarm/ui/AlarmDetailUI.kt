package com.depromeet.linkzupzup.view.alarm.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.depromeet.linkzupzup.extensions.timeBaseStr
import com.depromeet.linkzupzup.extensions.timeStr
import com.depromeet.linkzupzup.presenter.AlarmDetailViewModel
import com.depromeet.linkzupzup.presenter.model.WeeklyAlarm
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.utils.DLog
import java.util.*

class AlarmDetailUI: BaseView<AlarmDetailViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                vm?.getWeeklyAlarmList()?.let { alarmList ->
                    BodyContent(alarmList)
                }
            }
        }
    }

}

@Composable
fun AppBar() {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 16.dp)) {

        Card(elevation = 0.dp, backgroundColor = Color(0xFFE5E5E5)) {
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.size(40.dp)) {

                Image(painter = painterResource(id = R.drawable.ic_detail_back),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp))

            }
        }
    }
}

@Composable
fun BodyContent(alarms: ArrayList<WeeklyAlarm>) {
    val alarmList = remember { mutableStateOf(alarms) }
    Scaffold(topBar = { AppBar() },
        backgroundColor = Color(0xFFE5E5E5)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            TopHeaderCard()

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {

                items(alarmList.value) { alarm ->
                    WeeklyAlarmCard(alarm)
                }

            }

            
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)) {

                Button(onClick = { DLog.e("Jackson", "click read button") },
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF4076F6), contentColor = Color.White),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)) {
                    Text("알림 추가하기",
                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 14.sp, lineHeight = 17.5.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth())
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
            .background(color = Color(0xFFE5E5E5))
            .height(96.dp)) {

        Text("칠성파님,\n알람을 설정하시겠어요?",
            color = Color(0xFF292A2B),
            style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 24.sp, lineHeight = 32.4.sp, color = Color(0xFF292A2B)))

    }
}

@Composable
fun WeeklyAlarmCard(alarm: WeeklyAlarm) {

    Card(shape = RoundedCornerShape(4.dp),
        backgroundColor = Color.White,
        elevation = 2.dp,
        modifier = Modifier.clickable {

        }) {

        Box(Modifier.fillMaxWidth()
            .height(110.dp)) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(horizontal = 24.dp, vertical = 19.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
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

                    Spacer(Modifier.weight(1f))

                    Switch(checked = alarm.isEnableAlarm(),
                        onCheckedChange = { checked ->
                            alarm.enableAlarm = if (checked) 1 else 0
                            DLog.e("Jackso", "state: $checked")
                        }, colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFFFFFFF),
                            checkedTrackColor = Color(0xFF4076F6),
                            uncheckedThumbColor = Color(0xFFFFFFFF),
                            uncheckedTrackColor = Color(0xFFCED3D6)
                        ),
                        enabled = true,
                        modifier = Modifier
                            .width(46.dp)
                            .height(24.dp))

                }

                Spacer(Modifier.height(10.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp)) {

                    Card(shape = RoundedCornerShape(2.dp),
                        backgroundColor = Color(0xFFEAEBEF)) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(alarm.weekDayStr(),
                                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 10.sp, lineHeight = 14.sp, color = Color(0xFF000000)),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.height(22.dp)
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
                                    modifier = Modifier.height(22.dp)
                                        .padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                    }
                }
            }

            /**
             * 비활성화 블러
             */
            if (!alarm.isEnableAlarm()) Row(Modifier.fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xCCFFFFFF))) { }

        }
    }

}

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