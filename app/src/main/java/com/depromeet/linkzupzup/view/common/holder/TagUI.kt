package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.depromeet.linkzupzup.view.common.FontValues
import com.depromeet.linkzupzup.view.common.PaddingValues
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TagUI: AnkoComponent<ViewGroup> {

    var paddings: PaddingValues = PaddingValues()
    var radius: Float = 0f
    var fontSet: FontValues = FontValues()

    lateinit var mTagLayout: LinearLayout
    lateinit var mTv: TextView

    constructor()
    constructor(paddingValues: PaddingValues, radius: Float = 0f, fontSet: FontValues = FontValues()) {
        this.paddings.setValues(paddingValues)
        this.radius = radius
        this.fontSet = fontSet
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
        cardView {
            lparams(width= wrapContent, height= wrapContent)
            backgroundColor = Color.TRANSPARENT
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = this@TagUI.radius
                setColor(Color.TRANSPARENT)
            }

            mTagLayout = linearLayout {
                leftPadding = paddings.leftPadding
                topPadding = paddings.topPadding
                rightPadding = paddings.rightPadding
                bottomPadding = paddings.bottomPading
                backgroundColor = Color.WHITE

                mTv = textView {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSet.textSize)
                    // textColor = fontSet.textColor
                    typeface = ResourcesCompat.getFont(context, fontSet.fontRes)
                    lines = 1
                }.lparams(width= wrapContent, height= wrapContent)

            }.lparams(width= wrapContent, height= wrapContent)

        }
    }
}