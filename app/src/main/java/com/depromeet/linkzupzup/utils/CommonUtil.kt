package com.depromeet.linkzupzup.utils

import androidx.compose.ui.graphics.Color

object CommonUtil {

    fun parseStr(colorStr: String): Color = Color(android.graphics.Color.parseColor(colorStr))

}