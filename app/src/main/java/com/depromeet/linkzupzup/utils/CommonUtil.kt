package com.depromeet.linkzupzup.utils

import androidx.compose.ui.graphics.Color
import com.depromeet.linkzupzup.presenter.model.TagColor
import com.depromeet.linkzupzup.ui.theme.*
import java.util.*
import kotlin.collections.ArrayList

object CommonUtil {

    fun parseStr(colorStr: String): Color = Color(android.graphics.Color.parseColor(colorStr))

    private val TagColors: ArrayList<TagColor> = arrayListOf<TagColor>().apply {
        add(TagColor(TagBgColor01, TagTextColor01))
        add(TagColor(TagBgColor02, TagTextColor02))
        add(TagColor(TagBgColor03, TagTextColor03))
        add(TagColor(TagBgColor04, TagTextColor04))
        add(TagColor(TagBgColor05, TagTextColor05))
        add(TagColor(TagBgColor06, TagTextColor06))
        add(TagColor(TagBgColor07, TagTextColor07))
    }
    fun getRandomeTagColor(bound: Int = 7, colors: ArrayList<TagColor> = TagColors): TagColor {
        Random().nextInt(bound).let { randomNum ->
            return colors[randomNum].also { DLog.e("COLOR", "textColor: ${it.textColor}, bgColor: ${it.bgColor}") }
        }
    }

}