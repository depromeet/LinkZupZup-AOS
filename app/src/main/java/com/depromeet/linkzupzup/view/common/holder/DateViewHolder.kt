package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.extensions.getDay
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor
import java.util.*

class DateViewHolder(
    parent: ViewGroup,
    val ui: DateUI,
    var clickListener: ((Int, Pair<String, Calendar>) -> Unit)? = null): RecyclerView.ViewHolder(ui.createView(AnkoContext.create(parent.context, parent))) {

    fun onBind(list: ArrayList<Pair<String, Calendar>>, position: Int, isPick: Boolean) = with(ui) {
        list[position].let { item ->

            mCardView.setOnClickListener { clickListener?.invoke(position, item) }

            mWeekTv.text = item.first
            mDayTv.backgroundColor = Color.TRANSPARENT

            if (isPick) {
                mDayTv.backgroundResource = R.drawable.blue_circle
                mDayTv.textColor = Color.WHITE
            } else {
                mDayTv.background = null
                mDayTv.textColor = Color.parseColor("#292A2B")
            }

            mDayTv.text = item.second.getDay().toString()

        }
    }
}