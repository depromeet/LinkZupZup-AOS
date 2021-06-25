package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.depromeet.linkzupzup.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class DateUI: AnkoComponent<ViewGroup> {

    lateinit var mCardView: CardView
    lateinit var mWeekTv: TextView
    lateinit var mDayTv: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
        cardView {
            mCardView = this
            lparams(width= wrapContent, height= wrapContent)
            backgroundColor = Color.TRANSPARENT

            verticalLayout {
                backgroundColor = Color.TRANSPARENT
                gravity = Gravity.CENTER

                mWeekTv = textView {

                    typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_regular)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.date_week_text_size))
                    setLineSpacing(resources.getDimension(R.dimen.date_week_line_height), 1.0f)
                    textColor = Color.parseColor("#292A2B")
                    gravity = Gravity.CENTER

                }.lparams(width= matchParent, height= dip(16))

                view().lparams(width= dip(1), height= wrapContent, weight = 1f)

                mDayTv = textView {
                    backgroundResource = R.drawable.blue_circle

                    typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.date_day_text_size))
                    setLineSpacing(resources.getDimension(R.dimen.date_day_line_height), 1.0f)
                    textColor = Color.WHITE
                    gravity = Gravity.CENTER

                }.lparams(width= dip(24), height= dip(24))

            }.lparams(width= dip(46), height= dip(44))
        }
    }
}