package com.depromeet.linkzupzup.view.common.adapter

import android.content.Context
import android.view.ViewGroup
import com.depromeet.linkzupzup.base.BaseAdapter
import com.depromeet.linkzupzup.utils.DateUtil
import com.depromeet.linkzupzup.view.common.holder.DateUI
import com.depromeet.linkzupzup.view.common.holder.DateViewHolder
import java.util.*

class DateAdapter(override val ctx: Context) : BaseAdapter<DateViewHolder>() {

    var curPickPosition = 0
    var list: ArrayList<Pair<String, Calendar>> = DateUtil.getDateList()
    var clickListener: ((Int, Pair<String, Calendar>) -> Unit)? = null

    init { initList() }

    override fun onCreateBasicViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder
        = DateViewHolder(parent, DateUI()) { position, data ->
        curPickPosition = position
        clickListener?.invoke(position, data)
    }

    override fun onBindBasicItemView(holder: DateViewHolder?, position: Int) {
        holder?.onBind(list, position, position == curPickPosition)
    }

    fun initList() {
        list.clear()
        list.addAll(DateUtil.getDateList())
        setBasicItemCount(list.size)
    }

    fun setOnClickListener(onClickListener: (Int, Pair<String, Calendar>) -> Unit) {
        clickListener = onClickListener
    }

}