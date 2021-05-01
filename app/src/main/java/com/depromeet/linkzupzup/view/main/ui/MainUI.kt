package com.depromeet.linkzupzup.view.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
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
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.presenter.MainViewModel
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.DLog

class MainUI: BaseView<MainViewModel>() {

    @ExperimentalMaterialApi
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column(modifier = Modifier.fillMaxWidth()) {

//                    vm?.let { viewModel -> UserUI(viewModel) }
//
//                    Button(onClick = {
//                        vm?.getUserInfo()
//                    }, modifier = Modifier.fillMaxWidth()) {
//                        Text("클릭", textAlign = TextAlign.Center)
//                    }
                    AfterLoginMainUI()

                }
            }
        }
    }

}

@Composable
fun UserUI(vm: MainViewModel) {
    val userInfo: MutableState<User?> = remember { mutableStateOf(null)}
    vm.userInfo.observe(vm.lifecycleOwner!!) { user ->
        userInfo.value = user
    }

    userInfo.value?.let { user ->
        UserLoginStateUI(user)
    } ?: NeedUserLoginUI()
}

@Composable
fun UserLoginStateUI(user: User) {
    val str = "name: ${user.name}, age: ${user.age}"
    Text(str, textAlign = TextAlign.Center, modifier = Modifier
        .fillMaxWidth()
        .height(60.dp))
}

@Composable
fun NeedUserLoginUI() {
    Text("로그인이 필요합니다.", textAlign = TextAlign.Center, modifier = Modifier
        .fillMaxWidth()
        .height(60.dp))
}

@ExperimentalMaterialApi
@Composable
@Preview
fun AfterLoginMainUI(){

    val linkList : List<String> = listOf("gg","Gg","Gg","gg")

    // 로그인 성공
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { /*TODO*/ },
        sheetPeekHeight = 0.dp,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray0t)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray10)
                .padding(15.dp)
        ) {
            AppBar()
            TopHeaderCard("김나경")
            ReadProgress()

            // for gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(0.dp, 15.dp)
            ){
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(linkList) { link->
                        LinkCard()
                    }
                }
                // gradient overlay
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .alpha(1f)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Gray10
                                )
                            )
                        )
                        .align(Alignment.BottomCenter)
                )
            }


            Button(
                onClick = {
                    DLog.e("MainUI", "click add link button") },
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Blue50, contentColor = Color.White),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)) {
                Text("링크 줍기",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                                resId = R.font.spoqa_hansansneo_bold,
                                weight = FontWeight.W700)),
                        fontSize = 14.sp,
                        lineHeight = 17.5.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            }
        }

    }
}

@Composable
fun AppBar(){
    // in ColumnScope
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp),
        horizontalArrangement = Arrangement.End
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_alram),
            contentDescription = null,
            Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_ranking),
            contentDescription = null,
            Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_mypage),
            contentDescription = null,
            Modifier.size(24.dp)
        )
    }
}

@Composable
fun TopHeaderCard(name : String){
    // in ColumnScope
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)) {

        Text("${name}님,\n어서오세요. 반갑습니다!",
            color = Gray100t,
            style = TextStyle(
                fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_bold,
                        weight = FontWeight.W700)),
                fontSize = 24.sp,
                lineHeight = 32.4.sp,
                color = Gray100t)
        )

    }
}

@Composable
fun BottomSheet(){

}

@Composable
fun ReadProgress(){
    // in ColumnScope
    Column(
        modifier = Modifier
            .background(
                color = Gray0t,
                shape = RoundedCornerShape(10)
            )
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "오늘 5개만 읽어도 뿌듯! \uD83D\uDC4D\uD83D\uDC4D",
            style = TextStyle(
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W300)),
                fontSize = 12.sp,
                color = Gray100t)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = 0.7f,
            modifier = Modifier
                .fillMaxWidth(),
            color = Blue50,
            backgroundColor = Gray20,
        )
    }
}

@Preview
@Composable
fun LinkCard(){
    val tagList : List<String> = listOf("gg","gg","gg")
    Card(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        shape = RoundedCornerShape(0),
        modifier = Modifier.clickable {}
    ) {
        
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)) {
            Image(
               painter = painterResource(id = R.drawable.ic_link_profile_img),
               contentDescription = null,
               modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    items(tagList) { tag ->
                        HashtagCard(tagName = "디자인", backColor = TagBgColor01, textColor = TagTextColor01)
                    }
                }

                Text(
                    text="스타트업과 안맞는 대기업 임원 DNA는 어떻게 찾아낼까?",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700)),
                        fontSize = 12.sp,
                        color = Gray100t))

                Row{
                    Image(
                        painter = painterResource(id = R.drawable.ic_link_user_img),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier=Modifier.size(10.dp))
                    Text(
                        text="글쓴이",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(
                                resId = R.font.spoqa_hansansneo_light,
                                weight = FontWeight.W300)),
                            fontSize = 12.sp,
                            color = Gray70)
                    )
                }
            }
        }
    }
}


@Composable
fun HashtagCard(tagName : String, backColor : Color, textColor : Color){
    Card(
        modifier = Modifier
            .width(46.dp)
            .height(20.dp),
        elevation = 0.dp,
        backgroundColor = backColor
    ){
        Box(
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "#$tagName",
                style = TextStyle(
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_light,
                        weight = FontWeight.W300)),
                    fontSize = 10.sp,
                    color = textColor)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    /**
     * 참고 : Preview하려는 Composable은 ViewModel을 참조할 수 없습니다.
     */
    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val isLoginState = true
                val user = User("Jackson", 31)
                if (isLoginState) UserLoginStateUI(user)
                else NeedUserLoginUI()
            }
        }
    }
}