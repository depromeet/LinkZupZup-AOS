package com.depromeet.linkzupzup.view.mypage.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.presenter.MypageViewModel
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme

class MypageUI: BaseView<MypageViewModel>() {
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    vm?.let { viewModel -> MypageBodyUI(viewModel = viewModel) }

                }
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxWidth()) {
                MypageBodyUI(viewModel = MypageViewModel())
            }
        }
    }
}

@Composable
fun MypageBodyUI(viewModel: MypageViewModel){
    Scaffold(
        topBar = { MypageTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)) {

            MypageProfile(userName = "김나경")
        }
    }
}

@Composable
fun MypageTopBar(){
    val ctx = LocalContext.current
    Row(modifier = Modifier
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
fun MypageProfile(userName : String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically){

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
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)){

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {

        }
    }
}

@Composable
fun MyPageProfileCard(title: String, num : Int, unit : String){

    Column(modifier = Modifier.wrapContentSize()){
        Text(text = title,
            modifier = Modifier.padding(10.dp),
            style = TextStyle(fontSize = 10.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_light,
                    weight = FontWeight.W300))))
        Text(text = "${num}${unit}",
            style = TextStyle(fontSize = 10.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_light,
                    weight = FontWeight.W300))))
    }


}