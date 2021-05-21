package com.depromeet.linkzupzup.view.custom

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun <T> MultiBottomSheet(scaffoldState: BottomSheetScaffoldState,
         coroutineScope: CoroutineScope,
         content: @Composable (currentBottomSheet: T?,
                    closeSheet: () -> Unit,
                    openSheet: (T)->Unit) -> Unit) {

    var currentBottomSheet: T? by remember{
        mutableStateOf(null)
    }
    if(scaffoldState.bottomSheetState.isCollapsed)
        currentBottomSheet = null

    val closeSheet: () -> Unit = {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.collapse()

        }
    }
    val openSheet: (T) -> Unit = {
        coroutineScope.launch {
            currentBottomSheet = it
            scaffoldState.bottomSheetState.expand() }

    }

    content(currentBottomSheet, closeSheet, openSheet)
}

/**
 * 화면마다 사용되는 BottomSheet 갯수나 argument 갯수, 종류가 다르므로
 * 화면 개발시 아래 양식을 참고하여 선언해주세요.
 */
//sealed class BottomSheetScreen {
//    class Screen1(var argument: Any? = null): BottomSheetScreen()
//    class Screen2(val argument: Any? = null): BottomSheetScreen()
//    class Screen3(val argument: Any? = null): BottomSheetScreen()
//}