package com.depromeet.linkzupzup.view.mypage.ui

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.extensions.digitFormat1000
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.architecture.presenterLayer.MyPageViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MyPageData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.MyPageMenuData
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.view.custom.CustomSwitchCompat
import com.depromeet.linkzupzup.view.mydonut.MyDonutActivity
import com.google.accompanist.glide.rememberGlidePainter

class MyPageUI : BaseView<MyPageViewModel>() {
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = Gray10) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    vm?.let { MyPageBodyUI(viewModel = it) }
                }
            }
        }
    }
}

@Composable
fun MyPageBodyUI(viewModel: MyPageViewModel){

    val switchCompat: MutableState<SwitchCompat?> = remember { mutableStateOf(null) }

    Scaffold(topBar = { MyPageTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        
        Column(Modifier.fillMaxWidth()
            .background(Color.Transparent)) {

            /**
             * 상단 프로필
             */
            MyPageProfile(viewModel)

            Spacer(modifier = Modifier.height(30.dp))

            /**
             * 메뉴 리스트
             */
            LazyColumn(Modifier.fillMaxWidth()) {
                items(MyPageData.MENU_DATA){ menu->
                    when (menu.menuType) {
                        MyPageData.MENU_MOVE -> {
                            MyPageMenuCard(menuData = menu, clickListener = {
                                menu.moveClass?.let { cls ->
                                    with(viewModel) {
                                        getIntent(cls)?.apply {
                                            putExtra(AppConst.WEB_LINK_URL, menu.url)
                                        }?.let { intent ->
                                            movePageDelay(intent, 300L, false)
                                        }
                                    }

                                }
                            }) {
                                DetailBtn(painter = painterResource(id = R.drawable.ic_next))
                            }
                        }
                        MyPageData.MENU_TOGGLE -> {
                            MyPageMenuCard(menuData = menu) {
                                var state = false
                                CustomSwitchCompat(instanceCallback = {
                                    switchCompat.value = it
                                    it.isChecked = true
                                }, checkedOnChangeListener = { view, isChecked ->
                                    if (!state) { state = true }
                                    else viewModel.setAlarmEnabled((if (isChecked) "T" else "F"))
                                })
                            }
                        }
                        else -> {}
                    }
                }
            }

            /**
             * 로그아웃 버튼
             */
            Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 18.dp)){

                Text(text = "로그아웃",
                    style = TextStyle(fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Gray70,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W300))))
            }
        }
    }
}

@Composable
fun MyPageTopBar(){
    val ctx = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
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
        modifier = Modifier.fillMaxHeight()
            .wrapContentWidth()
            .noRippleClickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.size(40.dp)) {

            Image(painter = painter,
                contentDescription = null,
                Modifier.size(24.dp))

        }
    }
}

@Composable
fun MyPageProfile(vm: MyPageViewModel){
    val ctx = LocalContext.current
    val myPageData: MyPageData by vm.myPageData.observeAsState(MyPageData())

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp)){

        Image(painter = rememberGlidePainter(request = myPageData.badgeUrl, fadeIn = true),
            contentDescription = null,
            modifier = Modifier.size(64.dp, 48.dp)
                .padding(end = 10.dp))

        Text(
            text="${myPageData.userName}님",
            style = TextStyle(fontSize = 24.sp,
                lineHeight = 32.4.sp,
                fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_bold,
                weight = FontWeight.W700))))
    }

    Card(elevation = 0.dp,
        backgroundColor = Gray0t,
        shape = RoundedCornerShape(10),
        modifier = Modifier.fillMaxWidth()) {

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            item {
                MyPageProfileCard(
                    title = MyPageData.STR_THIS_WEEK_POINT.first,
                    num = myPageData.monthlyPoint,
                    unit = MyPageData.STR_THIS_WEEK_POINT.second)
            }
            item {
                MyPageProfileCard(
                    title = MyPageData.STR_TOTAL_READ_COUNT.first,
                    num = myPageData.readCnt,
                    unit = MyPageData.STR_TOTAL_READ_COUNT.second)
            }
            item {
                MyPageProfileCard(
                    title = MyPageData.STR_THIS_WEEK_READ_COUNT.first,
                    num = myPageData.totalReadCnt,
                    unit = MyPageData.STR_THIS_WEEK_READ_COUNT.second)
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(shape = RoundedCornerShape(4.dp),
        elevation = ButtonDefaults.elevation(0.dp),
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
fun MyPageProfileCard(title: String, num : Int? = 0, unit : String){
    val decNum = num?.digitFormat1000() ?: 0

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
fun MyPageMenuCard(menuData: MyPageMenuData, clickListener: (() -> Unit)? = null, content: @Composable () -> Unit){

    Row(modifier = Modifier.padding(5.dp)){
        Card(
            elevation = 2.dp,
            backgroundColor = Gray0t,
            shape = RoundedCornerShape(10),
            modifier = Modifier.fillMaxWidth()
                .height(56.dp)
                .noRippleClickable {
                    clickListener?.invoke()
                }
        ){

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 17.dp)){

                Text(text = menuData.menuTitle,
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W300))))

                content()

            }

        }
    }
}

@Composable
fun DetailBtn(painter: Painter){
    Card(elevation = 0.dp,
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        modifier = Modifier.wrapContentSize()) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize()) {

            Image(painter = painter,
                contentDescription = null,
                Modifier.size(24.dp))
        }
    }
}