package com.depromeet.linkzupzup.view.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.depromeet.linkzupzup.extensions.noRippleClickable
import com.depromeet.linkzupzup.R

/**
 * BottomSheet Close Button
 */
@Composable
fun BottomSheetCloseBtn(painter: Painter, onClick: ()->Unit){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .width(68.dp)
            .height(56.dp)
            .noRippleClickable(onClick = onClick)) {

        Spacer(Modifier.width(20.dp))

        Image(painter = painter,
            contentDescription = null,
            Modifier.size(24.dp))
    }
}

/**
 * BottomSheet를 만들경우, BottomSheetWithCloseDialog를 감쌀 경우,
 * BottomSheetCloseBtn을 별도로 선언하지 않아도 만들어집니다.
 * 코드 중복, 코드 재사용성을 위해 만들어두었습니다.
 */
@Composable
fun BottomSheetWithCloseDialog(
    onClosePressed: () -> Unit,
    closeImgPainter: Painter = painterResource(id = R.drawable.ic_black_close),
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit) {

    Box(modifier.fillMaxWidth()) {

        content()

        // 닫기 버튼
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            BottomSheetCloseBtn(painter = closeImgPainter, onClick = onClosePressed)
        }
    }
}