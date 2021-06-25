package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TagUI: AnkoComponent<ViewGroup> {

    lateinit var mCardView: CardView
    lateinit var mTagLayout: LinearLayout
    lateinit var mTv: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
        cardView {
            mCardView = this
            lparams(width= wrapContent, height= wrapContent)
            backgroundColor = Color.TRANSPARENT

            mTagLayout = linearLayout {
                backgroundColor = Color.WHITE

                mTv = textView {
                    lines = 1
                }.lparams(width= wrapContent, height= wrapContent)

            }.lparams(width= wrapContent, height= wrapContent)
        }
    }
}