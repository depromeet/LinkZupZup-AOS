package com.depromeet.linkzupzup.view.mypage.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.presenter.MyPageViewModel
import com.depromeet.linkzupzup.presenter.model.MyPageData
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.view.mydonut.MyDonutActivity
import java.text.DecimalFormat

class MyPageUI: BaseView<MyPageViewModel>() {
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Gray10) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    val myPageContentList : ArrayList<MyPageData<*>> = arrayListOf<MyPageData<*>>().apply{
                        // 이번 달 내 포인트
                        add(MyPageData<Any>(MyPageData.THIS_WEEK_POINT,9999))
                        // 전체 읽은 수
                        add(MyPageData<Any>(MyPageData.TOTAL_READ_COUNT,9999))
                        // 이번 달 읽은 수
                        add(MyPageData<Any>(MyPageData.THIS_WEEK_READ_COUNT,100))
                    }

                    vm?.let { viewModel -> MyPageBodyUI(myPageContentList = myPageContentList, viewModel = viewModel) }
                }
            }
        }
    }
}

@Composable
fun MyPageBodyUI(myPageContentList: ArrayList<MyPageData<*>>, viewModel: MyPageViewModel){
    Scaffold(
        topBar = { MyPageTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp,end = 16.dp, bottom = 16.dp)) {
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)) {

            MyPageProfile(myPageContentList = myPageContentList, userName = "김나경")

            MyPageMenu()
        }
    }
}

@Composable
fun MyPageTopBar(){
    val ctx = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)){

        BackButton(painterResource(id = R.drawable.ic_detail_back)){
            Toast.makeText(ctx,"뒤로가기",Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun BackButton(painter: Painter, onClick : () -> Unit){
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .wrapContentSize()
            .clickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .size(40.dp)) {

            Image(
                painter = painter,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun MyPageProfile(myPageContentList: ArrayList<MyPageData<*>>, userName : String){
    val ctx = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)){

        Image(
            painter = painterResource(id = R.drawable.ic_donut04),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp, 48.dp)
                .padding(end = 10.dp))

        Text(
            text="${userName}님",
            style = TextStyle(fontSize = 24.sp,
                lineHeight = 32.4.sp,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold,
                weight = FontWeight.W700))))
    }

    Card(
        elevation = 0.dp,
        backgroundColor = Gray0t,
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()){

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            items(myPageContentList){ contentData->
                when(contentData.type){

                    MyPageData.THIS_WEEK_POINT->{
                        MyPageProfileCard(
                            title = MyPageData.STR_THIS_WEEK_POINT.first,
                            num = contentData.data,
                            unit = MyPageData.STR_THIS_WEEK_POINT.second)
                    }

                    MyPageData.TOTAL_READ_COUNT->{
                        MyPageProfileCard(
                            title = MyPageData.STR_TOTAL_READ_COUNT.first,
                            num = contentData.data,
                            unit = MyPageData.STR_TOTAL_READ_COUNT.second)
                    }

                    MyPageData.THIS_WEEK_READ_COUNT->{
                        MyPageProfileCard(
                            title = MyPageData.STR_THIS_WEEK_READ_COUNT.first,
                            num = contentData.data,
                            unit = MyPageData.STR_THIS_WEEK_READ_COUNT.second)
                    }

                }

            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        onClick = {
            ctx.startActivity(Intent(ctx,MyDonutActivity::class.java))
        }) {

        Text("내 도넛 히스토리",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 17.5.sp,
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_bold,
                    weight = FontWeight.W700))))
    }
}

@Composable
fun MyPageProfileCard(title: String, num : Int, unit : String){
    val decimalFormat = DecimalFormat("#,###,###")
    val decNum = decimalFormat.format(num)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)){

        Text(text = title,
            modifier = Modifier.padding(bottom = 4.dp),
            style = TextStyle(fontSize = 10.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_light,
                    weight = FontWeight.W300))))
        Text(text = "${decNum}${unit}",
            style = TextStyle(fontSize = 14.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold,
                    weight = FontWeight.W700))))
    }


}


@Composable
fun MyPageMenu(){
    
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
        .fillMaxWidth()
        .padding(top = 40.dp)) {

        items(MyPageData.MENU_DATA){ menu->
            MyPageMenuCard(menu.first, menu.second)
        }
    }


    Row(modifier = Modifier.fillMaxWidth().padding(top = 28.dp),
    horizontalArrangement = Arrangement.End){
        Text(
            text = "로그아웃",
            style = TextStyle(fontSize = 12.sp,
                lineHeight = 16.sp,
                color = Gray70,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W300))))
    }
}


@Composable
fun MyPageMenuCard(menuName : String, menuType : Int){
    val ctx = LocalContext.current
    Card(
        elevation = 0.dp,
        backgroundColor = Gray0t,
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                Toast.makeText(ctx,"페이지 이동",Toast.LENGTH_SHORT).show() }){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 17.dp)){

            Text(
                text = menuName,
                modifier = Modifier.weight(1f),
                style = TextStyle(fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W300))))

            when(menuType){
                MyPageData.MENU_DETAIL ->
                    DetailBtn(painter = painterResource(id = R.drawable.ic_next))
                MyPageData.MENU_TOGGLE -> ToggleBtn()
            }
        }

    }
}

@Composable
fun DetailBtn(painter: Painter){
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .wrapContentSize()) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize()) {

            Image(
                painter = painter,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ToggleBtn(){

}

