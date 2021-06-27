package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.depromeet.linkzupzup.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TextSpinnerUI: AnkoComponent<ViewGroup> {

    lateinit var mCardView: CardView
    lateinit var mBodyLayout: LinearLayout
    lateinit var mTv: TextView
    lateinit var mUnderLIne: View

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
        cardView {
            mCardView = this
            lparams(width= matchParent, height= matchParent)
            backgroundColor = Color.TRANSPARENT

            mBodyLayout = verticalLayout {
                backgroundColor = Color.TRANSPARENT
                gravity = Gravity.CENTER

                mTv = textView {

                    typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_regular)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.spinner_text_size))
                    setLineSpacing(resources.getDimension(R.dimen.spinner_line_height), 1.0f)
                    textColor = Color.parseColor("#CED3D6")
                    gravity = Gravity.CENTER

                }.lparams(width= wrapContent, height= wrapContent)

                mUnderLIne = view { backgroundColor = Color.parseColor("#F1F2F5") }.lparams(width= matchParent, height= dip(1))

            }.lparams(width= matchParent, height= matchParent)
        }
    }
}