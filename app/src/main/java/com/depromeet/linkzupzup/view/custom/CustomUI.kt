package com.depromeet.linkzupzup.view.custom

import android.widget.CompoundButton
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.component.SliderAdapter
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.utils.DeviceUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * Modifier.width(46.dp) -> Thumb 여백 2dp로 인해 width를 딱맞게 지정할 경우, SwitchCompat이 짤리는 현상발생
 * height만 지정해서 사용해야 될것 같습니다.
 *
 * switchCompatInstance callback : SwitchCompat Instance를 참조하기 위한 용도이며,
 * 생성 시, SwitchCompat Instance를 반환합니다.
 *
 * updateCallback callback : View가 확장될 때 호출되는 update 콜백, 사용할 필요가 없을걸로 예상됩니다.
 */
@Composable
fun CustomSwitchCompat(modifier: Modifier = Modifier.height(24.dp),
                       @DrawableRes thumbDrawableRes: Int = R.drawable.custom_switch_compat_thumb,
                       @DrawableRes trackDrawableRes: Int = R.drawable.custom_switch_compat_track,
                       instanceCallback: (SwitchCompat)->Unit = {},
                       updateCallback: (SwitchCompat)->Unit = {},
                       checkedOnChangeListener: CompoundButton.OnCheckedChangeListener? = null) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->

            SwitchCompat(ctx).apply {
                thumbDrawable = ContextCompat.getDrawable(context, thumbDrawableRes)
                trackDrawable = ContextCompat.getDrawable(context, trackDrawableRes)
                checkedOnChangeListener?.let { listener -> setOnCheckedChangeListener(listener) }
                instanceCallback.invoke(this)
            }
        },
        update = { view ->
            updateCallback.invoke(view)
        }
    )

}




//@Composable
//fun CustomTimePicker(modifier: Modifier = Modifier,
//                     instanceCallback: (NumberPicker)->Unit = {},
//                     updateCallback: (NumberPicker)->Unit = {},
//                     onTimeChangedListener: TimePicker.OnTimeChangedListener) {
//    AndroidView(
//        modifier = modifier,
//        factory = { ctx ->
//            NumberPicker(ctx).apply {
//                instanceCallback.invoke(this)
//            }
//        },
//        update = { view ->
//            updateCallback.invoke(view)
//        }
//    )
//}
//
//@Composable
//fun CustomTextPicker(datas: Array<String>,
//                     modifier: Modifier = Modifier,
//                     instanceCallback: (NumberPicker)->Unit = {},
//                     updateCallback: (NumberPicker)->Unit = {},
//                     minLimit: Int = 0,
//                     maxLimit: Int = datas.size - 1,
//                     enableWrapSelectorWheel: Boolean = true,
//                     onValueChangeListener: NumberPicker.OnValueChangeListener? = null) {
//
//    AndroidView(
//        modifier = modifier,
//        factory = { ctx ->
//            NumberPicker(ctx).apply {
//                minValue = minLimit
//                maxValue = maxLimit
//                wrapSelectorWheel = enableWrapSelectorWheel
//                displayedValues = datas
//                onValueChangeListener?.let { listenr -> setOnValueChangedListener(listenr) }
//                instanceCallback.invoke(this)
//                setOnScrollListener { numberPicker, i ->
//                    DLog.e("TEST", "scroll: $i")
//                }
//            }
//        },
//        update = { view ->
//            updateCallback.invoke(view)
//        }
//    )
//}

@ExperimentalPagerApi
@Preview
@Composable
fun CustomPreView() {
    Surface(color = Color.White) {
        Column(Modifier.fillMaxSize()) {
//            CustomTextPicker(datas = arrayOf("오전", "오후", "기타")) { numberPicker, p0, p1 ->
//
//            }
//
//            CustomViewPicker(datas = arrayListOf("abcd", "efgh", "ijkl"))

            CustomTimePicker(onChangeListener = { amPm, hour, minute ->

            })

            CustomToogle(modifier = Modifier.height(28.dp), datas = arrayListOf("주중", "주말"))

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
}


@Composable
fun CustomViewPicker(datas: ArrayList<String>,
                     @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
                     modifier: Modifier = Modifier,
                     instanceCallback: (RecyclerView)->Unit = {},
                     updateCallback: (RecyclerView)->Unit = {},) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            RecyclerView(ctx).apply {

                // 첫번째 혹은 마지막 자식뷰를 가운데 위치까지 스크롤 시키기 위해 여백 추가
                DeviceUtils.getDeviceSize(ctx)?.let { point ->
                    val padding: Int = (if (orientation == RecyclerView.HORIZONTAL) point.x else point.y) / 2
                    val verticalPadding = if (orientation == RecyclerView.VERTICAL) padding else 0
                    val horizontalPadding = if (orientation == RecyclerView.HORIZONTAL) padding else 0
                    setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
                }
                // layoutManager = SliderLayoutManager(ctx, orientation)
                layoutManager = LinearLayoutManager(ctx, orientation, false)
                adapter = SliderAdapter { view ->
                    val childPosition = getChildLayoutPosition(view)
                    smoothScrollToPosition(childPosition)
                }.apply { setData(datas) }
                instanceCallback.invoke(this)
            }
        },
        update = { view ->
            updateCallback.invoke(view)
        }
    )
}

@ExperimentalPagerApi
@Composable
fun CustomTimePicker(modifier: Modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 24.dp),
    onChangeListener: (Int, Int, Int) -> Unit = { amPm, hour, minute -> }) {
    Box(modifier = modifier.height(170.dp), contentAlignment = Alignment.Center) {

        val amPmVal = remember { mutableStateOf(0) }
        val hourVal = remember { mutableStateOf(0) }
        val minuteVal = remember { mutableStateOf(0) }

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            Divider(color = Color(0xFFC4C4C4), thickness = 1.dp)

            Spacer(Modifier.weight(1f))

            Divider(color = Color(0xFFC4C4C4), thickness = 1.dp)
        }

        Row {

            // 오전, 오후
            arrayListOf("오후", "오전").let { amPms ->
                CustomImgTextPicker(imgs = arrayListOf(R.drawable.ic_sun_img, R.drawable.ic_sun_img),
                    txts = amPms,
                    spaceSize = 4.dp) { str, idx ->
                    amPmVal.value = idx
                    onChangeListener.invoke(amPmVal.value, hourVal.value, minuteVal.value)
                }
            }

            Spacer(Modifier.weight(1f))

            // 시간
            arrayListOf<String>().apply {
                (1..12).forEach { i -> add(String.format("%02d시", i)) }
            }.let { hours ->
                CustomTextPicker(datas = hours) { str, idx ->
                    hourVal.value = idx
                    onChangeListener.invoke(amPmVal.value, hourVal.value, minuteVal.value)
                }
            }

            // 분
            arrayListOf<String>().apply {
                (0..5).forEach { i -> add("${i}0분") }
            }.let { minutes ->
                CustomTextPicker(datas = minutes) { str, idx ->
                    minuteVal.value = idx
                    onChangeListener.invoke(amPmVal.value, hourVal.value, minuteVal.value)
                }
            }

        }
    }
}

@ExperimentalPagerApi
@Composable
fun CustomImgTextPicker(modifier: Modifier = Modifier.height(170.dp),
                        imgs: ArrayList<Int>,
                        txts: ArrayList<String>,
                        spaceSize: Dp = 4.dp,
                     cardModifier: Modifier = Modifier
                         .height(56.dp)
                         .padding(horizontal = 16.dp),
                     onChangeListener: (String, Int)->Unit = { str, idx -> }) {

    Box(modifier) {

        val txtsState = rememberPagerState(pageCount = txts.size)

        // https://google.github.io/accompanist/pager/#reacting-to-page-changes
        LaunchedEffect(txtsState) {
            snapshotFlow { txtsState.currentPage }.collect {
                onChangeListener.invoke(txts[it], it)
            }
        }

        Row (Modifier.fillMaxHeight()) {

            VerticalPager(state = txtsState, modifier = Modifier.fillMaxHeight()) { page ->
                txts[page].let { text ->

                    Row (verticalAlignment = Alignment.CenterVertically,
                        modifier = cardModifier
                            .graphicsLayer {

                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
//                                CommonUtil.lerp(
//                                        startValue = 0.85f,
//                                        endValue = 1f,
//                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                                )
//                                .also { scale ->
//                                    scaleX = scale
//                                    scaleY = scale
//                                }

                                alpha = CommonUtil.lerp(
                                    startValue = 0.5f,
                                    endValue = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )

                            }
                            .noRippleClickable {
                                GlobalScope.launch {
                                    txtsState.scrollToPage(
                                        page = currentPage,
                                        pageOffset = ((page + 1) / txts.size).toFloat()
                                    )
                                }
                            }) {

                        Image(painter = painterResource(id = imgs[page]),
                            contentDescription = null,
                            modifier = Modifier.size(width = 18.dp, height = 24.dp))

                        Spacer(Modifier.width(spaceSize))

                        Text(text,
                            style = TextStyle(fontFamily = FontFamily(Font(resId = if (page == txtsState.currentPage) R.font.spoqa_hansansneo_bold else R.font.spoqa_hansansneo_regular, weight = FontWeight.W700)), fontSize = 18.sp, lineHeight = 22.5.sp, color = Color(0xFF292A2B)),
                            textAlign = TextAlign.Center)

                    }
                }
            }
        }
    }
}


@ExperimentalPagerApi
@Composable
fun CustomTextPicker(modifier: Modifier = Modifier.height(170.dp),
                     datas: ArrayList<String>,
                     cardModifier: Modifier = Modifier
                         .height(56.dp)
                         .padding(horizontal = 16.dp),
                     onChangeListener: (String, Int)->Unit = { str, idx ->}) {

    Box(modifier) {

        val datasState = rememberPagerState(pageCount = datas.size)

        LaunchedEffect(datasState) {
            snapshotFlow { datasState.currentPage }.collect {
                onChangeListener.invoke(datas[it], it)
            }
        }

        Row (Modifier.fillMaxHeight()) {

            VerticalPager(state = datasState, modifier = Modifier.fillMaxHeight()) { page ->
                datas[page].let { text ->

                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = cardModifier
                            .graphicsLayer {

                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                                alpha = CommonUtil.lerp(
                                    startValue = 0.5f,
                                    endValue = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )

                            }
                            .noRippleClickable {
                                GlobalScope.launch {
                                    datasState.scrollToPage(
                                        page = currentPage,
                                        pageOffset = ((page + 1) / datas.size).toFloat()
                                    )
                                }
                            }) {

                        Text(text,
                            style = TextStyle(fontFamily = FontFamily(Font(resId = if (page == datasState.currentPage) R.font.spoqa_hansansneo_bold else R.font.spoqa_hansansneo_regular, weight = FontWeight.W700)), fontSize = 18.sp, lineHeight = 22.5.sp, color = Color(0xFF292A2B)),
                            textAlign = TextAlign.Center)

                    }
                }
            }
        }
    }
}

@Composable
fun CustomToogle(modifier: Modifier, spaceSize: Dp = 6.dp, datas: ArrayList<String>, onChangeListener: (Int) -> Unit = {}) {
    val selectedState = remember { mutableStateOf(0) }
    Row(modifier) {
        datas.forEachIndexed { index, txt ->
            val backgroundColor = if (selectedState.value == index) Color(0xFF4D5256) else Color(0xFFFFFFFF)
            val border = if (selectedState.value == index) null else BorderStroke(2.dp, Color(0xFFA9AFB3))
            val textColor = if (selectedState.value == index) Color(0xFFFFFFFF) else Color(0xFF878D91)

            Card(shape = RoundedCornerShape(4.dp), elevation = 0.dp, backgroundColor = backgroundColor, border = border,
                modifier = Modifier.fillMaxHeight().clickable {
                    if (selectedState.value != index) {
                        selectedState.value = if (selectedState.value == 0) 1 else 0
                        onChangeListener.invoke(index)
                    }
                }) {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)) {
                    Text(txt,
                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = textColor),
                        textAlign = TextAlign.Center)
                }
            }

            if (index < datas.size - 1) {
                Spacer(Modifier.width(spaceSize))
            }

        }
    }
}

@Composable
fun CustomTextCheckBox(modifier: Modifier = Modifier.height(20.dp), enableImg: Int, disableImg: Int, text: String, enableColor: Color = Color(0xFF4076F6), disableColor: Color = Color(0xFF878D91), onChangeListener: (Boolean) -> Unit = {}) {
    val checked = remember { mutableStateOf(false) }
    Card(elevation = 0.dp, backgroundColor = Color.Transparent, modifier = Modifier.wrapContentSize()
        .clickable {
            checked.value = !checked.value
            onChangeListener.invoke(!checked.value)
        }) {
        Row(modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Image(painter = painterResource(id = if (checked.value) enableImg else disableImg),
                contentDescription = null,
                modifier = Modifier.size(20.dp))

            Spacer(Modifier.width(4.dp))

            Text(text = text,
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = if (checked.value) enableColor else disableColor),
                textAlign = TextAlign.Center)

        }
    }
}