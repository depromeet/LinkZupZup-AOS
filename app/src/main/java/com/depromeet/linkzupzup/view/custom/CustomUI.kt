package com.depromeet.linkzupzup.view.custom

import android.util.Size
import android.widget.CompoundButton
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.component.SliderAdapter
import com.depromeet.linkzupzup.extensions.*
import com.depromeet.linkzupzup.utils.CommonUtil
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.utils.DateUtil
import com.depromeet.linkzupzup.utils.DeviceUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
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
                instanceCallback(this)
            }
        },
        update = { view ->
            updateCallback(view)
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
//                instanceCallback(this)
//            }
//        },
//        update = { view ->
//            updateCallback(view)
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
//                instanceCallback(this)
//                setOnScrollListener { numberPicker, i ->
//                    DLog.e("TEST", "scroll: $i")
//                }
//            }
//        },
//        update = { view ->
//            updateCallback(view)
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

            CustomTimePicker(onChangeListener = { type, timeVal ->

            })


            CustomToggle(modifier = Modifier.height(28.dp), datas = arrayListOf("주중", "주말"))

            val checked = remember { mutableStateOf(false) }
            CustomTextCheckBox(modifier = Modifier.height(20.dp),
                checked = checked.value,
                onChangeListener = {
                    DLog.e("TEST", if (it) "공휴일 알람 비활성" else "공휴일 알람 활성")
                })

            val datePickerItems = DateUtil.getDateList()
            CustomDatePicker(items = datePickerItems)


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
                instanceCallback(this)
            }
        },
        update = { view ->
            updateCallback(view)
        }
    )
}

@ExperimentalPagerApi
@Composable
fun CustomTimePicker(date: Calendar = Calendar.getInstance(),
                     modifier: Modifier = Modifier
                         .fillMaxWidth()
                         .padding(horizontal = 24.dp),
                     onChangeListener: (Int, Int) -> Unit = { type, value -> }) {

    Box(modifier = modifier.height(170.dp), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            Divider(color = Color(0xFFF1F2F5), thickness = 1.dp)

            Spacer(Modifier.weight(1f))

            Divider(color = Color(0xFFF1F2F5), thickness = 1.dp)
        }

        Row {

            // 오전, 오후
            arrayListOf("\uD83C\uDF19 오후", "\u2600\uFE0F 오전").let { amPms ->
//                CustomImgTextPicker(imgs = arrayListOf(R.drawable.ic_sun_img, R.drawable.ic_sun_img),
//                    txts = amPms,
//                    spaceSize = 4.dp,
//                    modifier = Modifier.fillMaxHeight()) { str, idx ->
//                    amPmVal.value = idx
//                    onChangeListener(amPmVal.value, hourVal.value, minuteVal.value)
//                }
                val curIdx = if (date.get(Calendar.AM_PM) == Calendar.PM) 0 else 1
                CustomTextPicker(curIdx = curIdx, datas = amPms, modifier = Modifier.fillMaxHeight()) { str, idx ->
                    DLog.e("CustomTimePicker", "오전,오후, str: $str, idx: $idx")
                    onChangeListener(Calendar.AM_PM, if (idx == 0) Calendar.PM else Calendar.AM)
                }
            }

            Spacer(Modifier.weight(1f))

            // 시간
            arrayListOf<String>().apply {
                (1..12).forEach { i -> add(String.format("%02d시", i)) }
            }.let { hours ->
                val curIdx = when {
                    (date.get(Calendar.HOUR) == 0 || date.get(Calendar.HOUR) == 12) -> 11
                    else -> date.get(Calendar.HOUR) - 1
                }
                CustomTextPicker(curIdx = curIdx, datas = hours) { str, idx ->
                    DLog.e("CustomTimePicker", "시간, str: $str, idx: $idx")
                    onChangeListener(Calendar.HOUR, idx + 1)
                }
            }

            // 분
            arrayListOf<String>().apply {
                (0..5).forEach { i -> add("${i}0분") }
            }.let { minutes ->
                val curIdx = date.get(Calendar.MINUTE) / 10
                CustomTextPicker(curIdx = curIdx, datas = minutes) { str, idx ->
                    DLog.e("CustomTimePicker", "분, str: $str, idx: $idx")
                    onChangeListener(Calendar.MINUTE, DateUtil.datePickerMinutes[idx])
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
                onChangeListener(txts[it], it)
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
fun CustomTextPicker(curIdx: Int = 0,
                     datas: ArrayList<String>,
                     modifier: Modifier = Modifier.height(170.dp),
                     cardModifier: Modifier = Modifier
                         .height(56.dp)
                         .padding(horizontal = 16.dp),
                     onChangeListener: (String, Int)->Unit = { str, idx ->}) {

    Box(modifier) {

        val coroutineScope = rememberCoroutineScope()
        val datasState = rememberPagerState(pageCount = datas.size, initialPage = curIdx)

        LaunchedEffect(datasState) {
            snapshotFlow { datasState.currentPage }.collect { index ->
//                아래에서 위로 빠르게 스크롤 할 경우, 누락되는 이슈가 있음... 방법이 없나
//                PublishSubject.create<Unit>()
//                    .debounce(3000L, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe { onChangeListener(datas[index], index) }
                onChangeListener(datas[index], index)
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
                                coroutineScope.launch {
                                    datasState.animateScrollToPage(page = page)
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
fun CustomToggle(modifier: Modifier, spaceSize: Dp = 6.dp, datas: ArrayList<String>, checked: ArrayList<Int> = arrayListOf(), onChangeListener: (Int) -> Unit = {}) {
    if (checked.size < datas.size) repeat(datas.size) { checked.add(0) }
    val status: MutableState<ArrayList<Int>> = remember { mutableStateOf(arrayListOf<Int>().apply {
        repeat(datas.size) {
            add(checked[it])
        }
    }) }

    Row(modifier) {
        datas.forEachIndexed { index, txt ->

            val backgroundColor = if (status.value[index] == 1) Color(0xFF4D5256) else Color(0xFFFFFFFF)
            val border = if (status.value[index] == 1) null else BorderStroke(1.dp, Color(0xFFA9AFB3))
            val textColor = if (status.value[index] == 1) Color(0xFFFFFFFF) else Color(0xFF878D91)

            Card(shape = RoundedCornerShape(4.dp), elevation = 0.dp, backgroundColor = backgroundColor, border = border,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        status.value[index] = if (status.value[index] == 0) 1 else 0
                        status.value = status.value
                        onChangeListener(index)
                    }) {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)) {
                    Text(text = txt,
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
fun CustomToggle2(modifier: Modifier, spaceSize: Dp = 6.dp, toggleValue1: Boolean, toggleValue2: Boolean, onChangeListener: (Int, Int) -> Unit = { idx, status -> }) {

    var toggleValue = remember { mutableListOf(toggleValue1, toggleValue2) }
    toggleValue[0] = toggleValue1
    toggleValue[1] = toggleValue2

    Row(modifier) {
        val backgroundColor1 = if (toggleValue[0]) Color(0xFF4D5256) else Color(0xFFFFFFFF)
        val border1 = if (toggleValue[0]) null else BorderStroke(1.dp, Color(0xFFA9AFB3))
        val textColor1 = if (toggleValue[0]) Color(0xFFFFFFFF) else Color(0xFF878D91)

        val backgroundColor2 = if (toggleValue[1]) Color(0xFF4D5256) else Color(0xFFFFFFFF)
        val border2 = if (toggleValue[1]) null else BorderStroke(1.dp, Color(0xFFA9AFB3))
        val textColor2 = if (toggleValue[1]) Color(0xFFFFFFFF) else Color(0xFF878D91)

        Card(shape = RoundedCornerShape(4.dp), elevation = 0.dp, backgroundColor = backgroundColor1, border = border1,
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    // val temp = if (toggleStatus1.value == 1) 0 else 1
//                    toggleStatus1.value = !toggleStatus1.value
//                    onChangeListener(0, toggleStatus1.value.getInt())
                    toggleValue[0] = !toggleValue[0]
                    onChangeListener(0, toggleValue[0].getInt())
                }) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp)) {
                Text(text = "주중",
                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = textColor1),
                    textAlign = TextAlign.Center)
            }
        }

        Spacer(Modifier.width(spaceSize))

        Card(shape = RoundedCornerShape(4.dp), elevation = 0.dp, backgroundColor = backgroundColor2, border = border2,
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
//                    toggleStatus2.value = (!toggleStatus2.value)
//                    onChangeListener(1, toggleStatus2.value.getInt())
                    toggleValue[1] = !toggleValue[1]
                    onChangeListener(1, toggleValue[1].getInt())
                }) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp)) {
                Text(text = "주말",
                    style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = textColor2),
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun CustomTextCheckBox(modifier: Modifier = Modifier.height(20.dp), checked: Boolean = false, onChangeListener: (Boolean) -> Unit = {}) {
    val checkedState = remember { mutableStateOf(checked) }
    Card(elevation = 0.dp, backgroundColor = Color.Transparent, modifier = Modifier
        .wrapContentSize()
        .noRippleClickable {
            checkedState.value = !checkedState.value
            onChangeListener(!checkedState.value)
        }) {
        Row(modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Image(painter = painterResource(id = if (checkedState.value) R.drawable.ic_holiday_on else R.drawable.ic_holiday_off),
                contentDescription = null,
                modifier = Modifier.size(20.dp))

            Spacer(Modifier.width(4.dp))

            Text(text = "공휴일엔 알람 끄기",
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_medium, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = if (checkedState.value) Color(0xFF4076F6) else Color(0xFF878D91)),
                textAlign = TextAlign.Center)

        }
    }
}

@Composable
fun CustomDatePicker(pickDate: Calendar = Calendar.getInstance(),
                     items: ArrayList<Pair<String, Calendar>>,
                     modifier: Modifier = Modifier
                         .fillMaxWidth()
                         .background(Color(0xFFF8FAFB)),
                     onClickListener: (Int, Pair<String, Calendar>)->Unit = { i, d -> }) {
    Row(modifier) {
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        LazyRow(state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            itemsIndexed(items) { index, date ->
                CustomDatePickerItemView(pickDate = pickDate, index = index, data = date, onClickListener = { index, data ->
                    onClickListener(index, data)
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = index)
                    }
                })
            }
        }
    }
}

@Composable
fun CustomDatePickerItemView(pickDate: Calendar = Calendar.getInstance(), dpSize: Size = Size(46,44), index: Int, data: Pair<String, Calendar>, onClickListener: (Int, Pair<String, Calendar>)->Unit = { i, d -> }) {
    Card(shape = MaterialTheme.shapes.large,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier
            .size(dpSize.width.dp, dpSize.height.dp)
            .noRippleClickable {
                onClickListener(index, data)
            }) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)) {

            // week
            Text(text = data.first,
                style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular, weight = FontWeight.W400)), fontSize = 12.sp, lineHeight = 16.8.sp, color = Color(0xFF292A2B)),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(16.dp))

            Spacer(Modifier.weight(1f))

            Box(Modifier.size(24.dp)) {

                // val isNow = data.second.isToday()
                val isPickDate = data.second.compareDate(pickDate)
                val txtColor = if (isPickDate) Color.White else Color(0xFF292A2B)

                // circle background
                if (isPickDate) Surface(shape = CircleShape,
                    modifier = Modifier.size(24.dp)) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF4076F6))) {}
                }

                // date
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()) {

                    Text(text = "${data.second.getDay()}",
                        style = TextStyle(fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold, weight = FontWeight.W700)), fontSize = 12.sp, lineHeight = 16.8.sp, color = txtColor),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentWidth())

                }
            }

        }

    }
}

@Preview
@Composable
fun CustomLinearProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float = 0.7f,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(240.dp, ProgressIndicatorDefaults.StrokeWidth)
            .focusable()
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorBackground(backgroundColor, strokeWidth)
        drawLinearIndicator(0f, progress, color, strokeWidth)
    }
}

fun DrawScope.drawLinearIndicator(startFraction: Float, endFraction: Float, color: Color, strokeWidth: Float){
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    // Progress line
    drawLine(color, Offset(barStart, yOffset), Offset(barEnd, yOffset), strokeWidth, StrokeCap.Round) // round
}

private fun DrawScope.drawLinearIndicatorBackground(
    color: Color,
    strokeWidth: Float
) = drawLinearIndicator(0f, 1f, color, strokeWidth)    // round