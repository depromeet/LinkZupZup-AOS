package com.depromeet.linkzupzup.utils

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.graphics.Color
import com.depromeet.linkzupzup.architecture.presenterLayer.model.TagColor
import com.depromeet.linkzupzup.ui.theme.*
import java.util.*
import kotlin.collections.ArrayList
import java.io.InputStream
import android.content.ClipData







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
            return colors[randomNum]
        }
    }

    fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
        return startValue + fraction * (endValue - startValue)
    }

    /** Linear interpolation between `startValue` and `endValue` by `fraction`.  */
    fun lerp(startValue: Int, endValue: Int, fraction: Float): Int {
        return startValue + Math.round(fraction * (endValue - startValue))
    }

    fun <T> getPropertyValue(ctx: Context, key: String): T? {
        val inputStream: InputStream = ctx.assets.open("config.properties")
        try {
            Properties().apply {
                load(inputStream)
            }.let { props -> return props.getProperty(key, "") as T }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream.close()
        }
    }

    fun saveClipboard(ctx: Context, label: String, text: String) {
        val clipboardManager = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clipData)
    }
}