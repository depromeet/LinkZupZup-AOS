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

class TagAdapter(override val ctx: Context, var tagType: Int = TAG_TYPE_SMALL) : BaseAdapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG_TYPE_SMALL = 101010
        const val TAG_TYPE_LARGE = TAG_TYPE_SMALL.plus(1)
    }

    private val DEFAULT_TAG_PADDING = PaddingValues(verticalPadding = ctx.dip(4), horizontalPadding = ctx.dip(6))
    private val DEFAULT_TAG_FONT_SET = FontValues(textSizeRes = R.dimen.default_tag_text_size, textLineHeightRes = R.dimen.default_tag_line_height, fontRes = R.font.spoqa_hansansneo_medium)
    private val LARGE_TAG_PADDING = PaddingValues(padding = ctx.dip(8))
    private val LARGE_TAG_FONT_SET = FontValues(textSizeRes = R.dimen.large_tag_text_size, textLineHeightRes = R.dimen.large_tag_line_height, fontRes = R.font.spoqa_hansansneo_medium)

    var list: ArrayList<LinkHashData> = arrayListOf()
    var clickListener: ((Int, LinkHashData) -> Unit)? = null

    override fun onCreateBasicViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = with(ctx) {
        when (viewType) {
            TAG_TYPE_LARGE -> TagViewHolder(parent, TagUI(), LARGE_TAG_PADDING, dip(2).toFloat(), LARGE_TAG_FONT_SET, clickListener)
            else -> TagViewHolder(parent, TagUI(), DEFAULT_TAG_PADDING, dip(2).toFloat(), DEFAULT_TAG_FONT_SET, clickListener)
        }
    }

    override fun onBindBasicItemView(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is TagViewHolder -> holder.onBind(list, position)
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int = tagType

    fun initList(list: ArrayList<LinkHashData>) {
        this.list.clear()
        this.list.addAll(list)
        setBasicItemCount(this.list.size)
    }

    fun setOnClickListener(onClickListener: (Int, LinkHashData) -> Unit) {
        clickListener = onClickListener
    }

}