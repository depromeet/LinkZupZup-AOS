package com.depromeet.linkzupzup.view.mydonut.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.depromeet.linkzupzup.extensions.digitFormat1000
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.presenter.MyDonutViewModel
import com.depromeet.linkzupzup.presenter.model.DonutBadge
import com.depromeet.linkzupzup.presenter.model.MyDonutData
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.DLog

class MyDonutUI : BaseView<MyDonutViewModel>() {

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

@ExperimentalFoundationApi
@Composable
fun MyDonutBodyUI(myDonutList: ArrayList<MyDonutData<*>>){
    Scaffold(
        topBar = { MyDonutTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxWidth()
            .background(Color.Transparent)) {

            Information()

            DonutBadgeGridList(myDonutList)

        }
    }
}

@Composable
fun MyDonutTopBar(){
    val ctx = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()
        .height(52.dp)){

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)
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
        modifier = Modifier.fillMaxHeight()
            .wrapContentWidth()
            .noRippleClickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.size(56.dp)
                .padding(start = 16.dp)) {

            Image(painter = painter,
                contentDescription = null,
                Modifier.size(24.dp))

        }
    }
}

@Composable
fun Information(){
    Row(modifier = Modifier.fillMaxWidth()
        .padding(start = 16.dp, top = 12.dp)){

        Card(elevation = 0.dp,
            backgroundColor = Blue50Op8,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
                .height(32.dp)){

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
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
    Row(modifier = Modifier.fillMaxWidth()
        .padding(top = 4.dp)){

        Card(elevation = 0.dp,
            backgroundColor = Blue50Op8,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 6.dp)){

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
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
                    Box(Modifier.fillMaxSize()
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
        modifier = Modifier.fillMaxWidth()
            .height(130.dp)) {

        Column(Modifier.fillMaxSize()
            .padding(top = 20.dp, bottom = 8.dp)) {

            // 뱃지
            Row(modifier = Modifier.fillMaxWidth()
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