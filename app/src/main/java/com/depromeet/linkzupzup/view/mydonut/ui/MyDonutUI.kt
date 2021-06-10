package com.depromeet.linkzupzup.view.mydonut.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.depromeet.linkzupzup.extensions.digitFormat1000
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.architecture.presenterLayer.MyDonutViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.DonutBadge
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MyDonutData
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.custom.BottomSheetCloseBtn
import com.depromeet.linkzupzup.view.main.ui.MainAppBar
import kotlinx.coroutines.launch

class MyDonutUI : BaseView<MyDonutViewModel>() {

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                val myDonutList : ArrayList<MyDonutData<*>> = arrayListOf<MyDonutData<*>>().apply{
                    // 데이터가 없는 경우
                    // add(MyDonutData<Any>(MyDonutData.NO_DONUT))

                    // 데이터가 있는 경우
                    addAll(MyDonutData.mockMyDonutContentList(24))
                }
                MyDonutBodyUI(myDonutList)
            }
        }
    }

}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MyDonutBodyUI(myDonutList: ArrayList<MyDonutData<*>>){
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        topBar = { MyDonutTopBar() },
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStartPercent = 5,topEndPercent = 5),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Gray0t,
        sheetGesturesEnabled = false,
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize(),
        sheetContent = { InfoBottomSheet(){
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            } }
        }){

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)) {

            Information(){
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            }
            DonutBadgeGridList(myDonutList)
        }
    }

    /* Scaffold(
        topBar = { MyDonutTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)) {

            Information()

            DonutBadgeGridList(myDonutList)

        }
    } */
}

@Composable
fun MyDonutTopBar(){
    val ctx = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)){

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()){

            MyDonutBackButton(painterResource(id = R.drawable.ic_detail_back)){
                // finish activity
            }
        }

        Box(modifier = Modifier.align(Alignment.Center)){
            Text(text = "내 도넛 히스토리",
                style = TextStyle(fontSize = 14.sp,
                    lineHeight = 16.sp,
                    color = Gray100t,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))
        }
    }


}



@Composable
fun MyDonutBackButton(painter: Painter, onClick : () -> Unit){
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .noRippleClickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .size(56.dp)
                .padding(start = 16.dp)) {

            Image(painter = painter,
                contentDescription = null,
                Modifier.size(24.dp))

        }
    }
}

@Composable
fun Information(onClick: () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, top = 12.dp, end = 16.dp)
        .noRippleClickable { onClick() }){

        Card(elevation = 0.dp,
            backgroundColor = Blue50Op8,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)){

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)){

                Image(painter = painterResource(id = R.drawable.ic_question),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp))

                Text(text = "포인트와 도넛의 기준은?",
                    style = TextStyle(fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Blue50,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_regular,
                                weight = FontWeight.W500))))

            }
        }
    }
}

@Preview
@Composable
fun NoDonut(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp)){

        Card(elevation = 0.dp,
            backgroundColor = Blue50Op8,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 6.dp)){

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)){

                Text(
                    text = "아직 도넛이 없어요!\n다음 달 1일에 내 포인트에 따른 도넛이 보일꺼에요.",
                    style = TextStyle(fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Blue50,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_regular,
                                weight = FontWeight.W500))))
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun DonutBadgeGridList(myDonutList: ArrayList<MyDonutData<*>>){
    val cellFixed = if (myDonutList.size == 1 && myDonutList[0].type == MyDonutData.NO_DONUT) MyDonutData.NO_DONUT
    else MyDonutData.HAVE_DONUT

    LazyVerticalGrid(
        cells = GridCells.Fixed(cellFixed),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.fillMaxWidth()) {

        items(myDonutList){ badge->
            when(badge.type){

                // 도넛이 없을 경우
                MyDonutData.NO_DONUT -> NoDonut()

                // 도넛이 있을 경우
                MyDonutData.HAVE_DONUT -> (badge.data as? DonutBadge)?.let{ badgeData ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(6.dp)) {
                        DonutBadgeCard(painterResource(id = badgeData.badgeResource),badgeData.point, badgeData.date)
                    }
                }

                else -> DLog.e("TEST","empty")
            }
        }
    }
}


@Composable
fun DonutBadgeCard(painter : Painter, point : Int, date : String){

    val pointStr = point.digitFormat1000()+"p"

    Card(shape = RoundedCornerShape(0.dp),
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 8.dp)) {

            // 뱃지
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)){

                Image(painter = painter,
                    contentDescription = null)
            }

            Spacer(Modifier.weight(1f))

            // 포인트
            Text(text = pointStr,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Gray100t,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))

            // 날짜
            Row(Modifier.fillMaxWidth()){
                Text(text = date,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Gray70,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_regular,
                                weight = FontWeight.W500))))
            }
        }
    }

}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun DonutPreview() {
    LinkZupZupTheme {
        Surface(color = Color.White) {
            val myDonutList : ArrayList<MyDonutData<*>> = arrayListOf<MyDonutData<*>>().apply{
                // 데이터가 없는 경우
                // add(MyDonutData<Any>(MyDonutData.NO_DONUT))

                // 데이터가 있는 경우
                addAll(MyDonutData.mockMyDonutContentList(48))
            }
            MyDonutBodyUI(myDonutList)
        }
    }
}


@Composable
fun InfoBottomSheet(onClick: () -> Unit){

    Surface(modifier = Modifier.background(Color.White)) {

        InfoSheetHeader()

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(580.dp)
                .background(Color.Transparent)) {

            // 닫기 버튼
            Row(horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)) {

                BottomSheetCloseBtn(painterResource(id = R.drawable.ic_close)){
                    onClick()
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            // 메인 리스트
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithCache {
                        val gradient = Brush.linearGradient(
                            colors = listOf(Color.White, Color.Transparent),
                            start = Offset(0f, 0f),
                            end = Offset(0f, 64.dp.toPx())
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient)
                        }
                    }){
                itemsIndexed(MyDonutData.donutInfo){ index,info->
                    info?.let{
                        InfoDonutCard(painter = painterResource(info.badgeResource), info = info.getInfoStr())
                        if(index != MyDonutData.donutInfo.size-2){
                            Divider(color = Gray20,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp))
                        }
                    } ?: run{
                        AdditionalPointInfo()
                    }
                }
            }
        }
    }

}

@Composable
fun InfoSheetHeader(){
    // 로고가 닫기버튼 row 와 겹쳐있기 때문에 box
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)
        .background(Color.Transparent)){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(id = R.drawable.ic_small_logo_title),
                contentDescription = null,
                modifier = Modifier.size(123.dp,49.dp))

            val guideStr = AnnotatedString(
                text = "링크 1개를 읽을 때마다 100 point가 쌓여요!",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        start = 0,
                        end = 5,
                        item = SpanStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(
                                resId = R.font.spoqa_hansansneo_bold,
                                weight = FontWeight.W700)))
                    ),
                    AnnotatedString.Range(
                        start = 14,
                        end = 23,
                        item = SpanStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(
                                resId = R.font.spoqa_hansansneo_bold,
                                weight = FontWeight.W700)))
                    )
                )
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentWidth()) {

                Text(text = "\uD83C\uDF89",
                    style = TextStyle(fontSize = 18.sp,
                        lineHeight = 22.5.sp,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_bold,
                                weight = FontWeight.W500))))
                Text(
                    guideStr,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.8.sp,
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.spoqa_hansansneo_regular,
                                weight = FontWeight.W500
                            )
                        )
                    )
                )

                Text(text = "\uD83C\uDF89",
                    style = TextStyle(fontSize = 18.sp,
                        lineHeight = 22.5.sp,
                        fontFamily = FontFamily(
                            Font(resId = R.font.spoqa_hansansneo_bold,
                                weight = FontWeight.W500))))
            }
        }

    }
}

@Composable
fun InfoDonutCard(painter : Painter = painterResource(id = R.drawable.ic_donut01), info : String){
    Card(elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(Color.Transparent)){

        Row(horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)){
            Image(
                modifier = Modifier.size(72.dp,52.dp),
                painter = painter,
                contentDescription = null)
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "도넛 획득 조건",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.8.sp,
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))

                Text(
                    text = info,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.8.sp,
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500))))
            }
        }
    }

}

@Composable
fun AdditionalPointInfo(){

    val guideStr1 = AnnotatedString(
        text = "5번째, 10번째, 15번째 등 읽은 아티클 수가 5의 배수에 해당되는 경우 20 point가 추가로 적립되며 읽은 아티클의 수는 하루마다 갱신됩니다.",
        spanStyles = listOf(
            AnnotatedString.Range(
                start = 28,
                end = 33,
                item = SpanStyle(color = Color(0xff1f1f1f),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W500)))
            ),
            AnnotatedString.Range(
                start = 43,
                end = 51,
                item = SpanStyle(color = Color(0xff1f1f1f),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W500)))
            )
        )
    )

    val guideStr2 = AnnotatedString(
        text = "7일 연속으로 접속하여 아티클을 읽은 경우 700 point가 추가로 지급되며 지급 후 연속 일수는 초기화됩니다.",
        spanStyles = listOf(
            AnnotatedString.Range(
                start = 0,
                end = 5,
                item = SpanStyle(color = Color(0xff1f1f1f),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W500)))
            ),
            AnnotatedString.Range(
                start = 24,
                end = 33,
                item = SpanStyle(color = Color(0xff1f1f1f),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W500)))
            )
        )
    )


    Spacer(modifier = Modifier.height(27.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(172.dp)
            .background(Color(0xfff5f5f5))) {

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "포인트 추가 적립 안내",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W500))))

        Divider(color = Gray50t,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 20.dp,vertical = 12.dp))

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = guideStr1,
            style = TextStyle(color = Color(0xff1f1f1f),
                fontSize = 12.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_light,
                    weight = FontWeight.W300))))

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = guideStr2,
            style = TextStyle(color = Color(0xff1f1f1f),
                fontSize = 12.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_light,
                    weight = FontWeight.W300))))
    }
}