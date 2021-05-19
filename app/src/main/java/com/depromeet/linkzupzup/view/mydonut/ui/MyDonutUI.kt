package com.depromeet.linkzupzup.view.mydonut.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.depromeet.linkzupzup.ui.theme.Gray100t
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.view.mypage.ui.BackButton

class MyDonutUI : BaseView<MyDonutViewModel>() {
    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                MyDonutBodyUI()
            }
        }
    }

}

@Preview
@Composable
fun MyDonutBodyUI(){
    Scaffold(
        topBar = { MyDonutTopBar() },
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp,end = 16.dp, bottom = 16.dp)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)) {


        }
    }
}

@Composable
fun MyDonutTopBar(){
    val ctx = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)){

        Box{
            BackButton(painterResource(id = R.drawable.ic_detail_back)){
                Toast.makeText(ctx,"뒤로가기", Toast.LENGTH_SHORT).show()
            }
        }

        Box{
            Text(
                text = "내 도넛 히스토리",
                style = TextStyle(fontSize = 14.sp,
                    lineHeight = 16.sp,
                    color = Gray100t,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(resId = R.font.spoqa_hansansneo_bold,
                            weight = FontWeight.W700)
                    )
                )
            )
        }
    }


}