package com.depromeet.linkzupzup.view.mydonut.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.depromeet.linkzupzup.presenter.MyDonutViewModel
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.view.mypage.ui.BackButton

class MyDonutUI : BaseView<MyDonutViewModel>() {
    @ExperimentalFoundationApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Color.White) {
                MyDonutBodyUI()
            }
        }
    }

}

@ExperimentalFoundationApi
@Preview
@Composable
fun MyDonutBodyUI(){
    Scaffold(
        topBar = { MyDonutTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)) {

            Information()
            DonutBadgeGridList()
            //NoDonut()

        }
    }
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

            BackButton(painterResource(id = R.drawable.ic_detail_back)){
                // finish activity
            }
        }

        Box(modifier = Modifier
            .align(Alignment.Center)){
            Text(
                text = "내 도넛 히스토리",
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
fun Information(){

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp)){

        Card(
            elevation = 0.dp,
            backgroundColor = Blue50Op8,
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)){

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_question),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp))

                Text(
                    text = "포인트와 도넛의 기준은?",
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
        .padding(vertical = 8.dp)){

        Card(
            elevation = 0.dp,
            backgroundColor = Blue50Op8,
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)){

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

@Preview
@ExperimentalFoundationApi
@Composable
fun DonutBadgeGridList(){
    val badges = (0..13).toList()

    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
        items(badges){

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp)){
                DonutBadgeCard()
            }
        }
    }
}

@Preview
@Composable
fun DonutBadgeCard(){

    Card(shape = RoundedCornerShape(0.dp),
        elevation = 0.dp,
        modifier = Modifier
            .width(106.dp)
            .height(130.dp)){
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)){

                Image(
                    painter = painterResource(id = R.drawable.ic_donut04),
                    contentDescription = null)
            }

            Text(
                text = "5,235p",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Gray100t,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700))))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)){
                Text(
                    text = "2021년 4월",
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















