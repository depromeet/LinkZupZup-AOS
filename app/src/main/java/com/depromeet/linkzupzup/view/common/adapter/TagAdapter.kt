package com.depromeet.linkzupzup.view.common.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.base.BaseAdapter
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.view.common.FontValues
import com.depromeet.linkzupzup.view.common.PaddingValues
import com.depromeet.linkzupzup.view.common.holder.TagUI
import com.depromeet.linkzupzup.view.common.holder.TagViewHolder

class TagAdapter(override val ctx: Context, var tagType: Int = TAG_TYPE_SMALL) : BaseAdapter() {

    companion object {
        const val TAG_TYPE_SMALL = 10101010
        const val TAG_TYPE_LARGE = 10101010
    }

    var list: ArrayList<LinkHashData> = arrayListOf()
    var clickListener: ((Int, LinkHashData) -> Unit)? = null

    override fun onCreateBasicViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = with(ctx) {
        when (viewType) {
            TAG_TYPE_LARGE -> TagViewHolder(parent, TagUI(paddingValues = PaddingValues(padding = dip(8)), radius = dip(2).toFloat(), fontSet = FontValues(textSize = dip(12).toFloat(), fontRes = R.font.spoqa_hansansneo_medium)), clickListener)
            else -> TagViewHolder(parent, TagUI(paddingValues = PaddingValues(verticalPadding = dip(4), horizontalPadding = dip(6)), radius = dip(2).toFloat(), fontSet = FontValues(textSize = dip(10).toFloat(), fontRes = R.font.spoqa_hansansneo_medium)), clickListener)
        }
    }

    override fun onBindBasicItemView(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is TagViewHolder -> holder.onBind(list, position)
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int = tagType

    fun addList(list: ArrayList<LinkHashData>) {
        this.list.clear()
        this.list.addAll(list)
        setBasicItemCount(this.list.size)
    }

    fun setOnClickListener(onClickListener: (Int, LinkHashData) -> Unit) {
        clickListener = onClickListener
    }

}