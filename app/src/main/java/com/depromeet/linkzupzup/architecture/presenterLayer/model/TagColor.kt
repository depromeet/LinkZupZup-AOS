package com.depromeet.linkzupzup.architecture.presenterLayer.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagColor (
    var bgColor: Color,
    var textColor: Color): Parcelable