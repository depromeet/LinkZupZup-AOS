package com.depromeet.linkzupzup.architecture.presenterLayer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagColor (
    var bgColor: Int,
    var textColor: Int): Parcelable