package com.depromeet.linkzupzup.view.common.holder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.utils.CommonUtil
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

class TagViewHolder(parent: ViewGroup, val ui: TagUI, var clickListener: ((Int, LinkHashData) -> Unit)? = null): RecyclerView.ViewHolder(ui.createView(AnkoContext.create(parent.context, parent))) {
    fun onBind(list: ArrayList<LinkHashData>, position: Int) = with(ui) {
        list[position].let { item ->

            // clickListener
            with(mTagLayout) {
                setOnClickListener {
                    clickListener?.invoke(position, item)
                }
                backgroundColor = CommonUtil.convertComposeColor(item.tagColor.bgColor)
            }

            // hashtag
            with(mTv) {
                text = item.hashtagName
                textColor = CommonUtil.convertComposeColor(item.tagColor.textColor)
            }

        }
    }
}