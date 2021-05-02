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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.presenter.MainViewModel
import com.depromeet.linkzupzup.presenter.model.TagColor2
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.DLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        sheetContent = { BottomSheet(bottomSheetScaffoldState,coroutineScope) },   // sheetContent -  Column scope
        sheetShape = RoundedCornerShape(5,5,0,0),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Gray0t,
        sheetGesturesEnabled = false,
        modifier = Modifier
            .fillMaxSize()
    ){
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
                        DLog.e("MainUI", "click add link button")
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
//                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
//                            bottomSheetScaffoldState.bottomSheetState.expand()
//                        else
//                            // 들어올 일 없음
//                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        } },
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

@ExperimentalMaterialApi
@Composable
fun BottomSheet(bottomSheetScaffoldState : BottomSheetScaffoldState,coroutineScope : CoroutineScope){
    val inputLink = remember { mutableStateOf(TextFieldValue()) }
    val inputTag = remember { mutableStateOf(TextFieldValue()) }
    val tc1 : List<TagColor2> = listOf(
        TagColor2("디자인",TagBgColor01, TagTextColor01),
        TagColor2("포트폴리오",TagBgColor02, TagTextColor02),
        TagColor2("UX",TagBgColor03, TagTextColor03),
        TagColor2("UI",TagBgColor04, TagTextColor04),
        TagColor2("마케팅",TagBgColor05, TagTextColor05),
        TagColor2("인공지능",TagBgColor06, TagTextColor06))
    val tc2 : List<TagColor2> = listOf(
        TagColor2("프론트 개발",TagBgColor07, TagTextColor07),
        TagColor2("그로스 해킹",TagBgColor03, TagTextColor03),
        TagColor2("Android",TagBgColor01, TagTextColor01),
        TagColor2("스타트업",TagBgColor02, TagTextColor02),
        TagColor2("ios",TagBgColor04, TagTextColor04))

    // in Column Scope
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(580.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp,20.dp,20.dp,0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                })
        }

        Text(
            text = "읽고 싶은 링크를\n추가해주세요!",
            style = TextStyle(
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_bold,
                    weight = FontWeight.W700)),
                fontSize = 24.sp,),
            modifier = Modifier.fillMaxWidth().padding(20.dp,0.dp))

        Spacer(modifier = Modifier.height(10.dp).padding(20.dp,0.dp))

        TextField(
            value = inputLink.value,
            onValueChange = { inputLink.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp,0.dp),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(
                    resId = R.font.spoqa_hansansneo_regular,
                    weight = FontWeight.W500)),
                fontSize = 12.sp,
                color = Gray100t),
            placeholder = {
                Text(
                    text = "링크주소를 여기에 붙여넣기 해주세요.",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500)),
                        fontSize = 12.sp,
                        color = Gray70),
                    lineHeight = 17.5.sp
                ) },


            leadingIcon = {
                Text(
                    text = "\uD83D\uDC49",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W500)),
                        fontSize = 12.sp)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Gray20,
                textColor = Gray100t
            )
        )

        Spacer(modifier = Modifier.height(20.dp).padding(20.dp,0.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp,0.dp)
        ){
            Text(
                text = "해시태그를 선택해주세요.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_bold,
                        weight = FontWeight.W700)),
                    fontSize = 14.sp,),
                modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "0",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W700)),
                        fontSize = 12.sp,
                    color = Gray70)
                )
                Text(
                    text = "/3",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(
                            resId = R.font.spoqa_hansansneo_regular,
                            weight = FontWeight.W700)),
                    fontSize = 12.sp,
                    color = Gray70)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp).padding(20.dp,0.dp))


        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(20.dp,0.dp,0.dp,0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(tc1) { tag ->
                BottomSheetHashtagCard(tagName = tag.tagName, backColor = tag.bgColor, textColor = tag.textColor)
            }
        }

        Spacer(modifier = Modifier.height(10.dp).padding(20.dp,0.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(20.dp,0.dp,0.dp,0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(tc2) { tag ->
                BottomSheetHashtagCard(tagName = tag.tagName, backColor = tag.bgColor, textColor = tag.textColor)
            }
        }
        Spacer(modifier = Modifier.height(15.dp).padding(20.dp,0.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(20.dp,0.dp)){
            Text(
                text = "원하시는 해시태그가 없으신가요?",
                style = TextStyle(
                    fontFamily = FontFamily(Font(
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W700)),
                    fontSize = 12.sp,
                    color = Gray70),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier.padding(20.dp)
        ){
            Button(
                onClick = {
                    DLog.e("MainUI", "BottomSheet/click save link button") },
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Gray50t, contentColor = Gray70),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)) {
                Text("저장하기",
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
                    color = textColor),
                modifier = Modifier.padding(5.dp,0.dp)
            )
        }

    }
}

@Composable
fun BottomSheetHashtagCard(tagName : String, backColor : Color, textColor : Color){
    Card(
        modifier = Modifier
            .height(32.dp),
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
                        resId = R.font.spoqa_hansansneo_regular,
                        weight = FontWeight.W300)),
                    fontSize = 12.sp,
                    color = textColor),
                modifier = Modifier.padding(8.dp,0.dp)
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