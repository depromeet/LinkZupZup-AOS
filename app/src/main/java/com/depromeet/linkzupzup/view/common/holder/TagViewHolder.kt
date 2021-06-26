package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.common.FontValues
import com.depromeet.linkzupzup.view.common.PaddingValues
import org.jetbrains.anko.*

class TagViewHolder(
    val parent: ViewGroup,
    val ui: TagUI,
    val paddingValues: PaddingValues = PaddingValues(padding= parent.context.dip(8)),
    val radiusValue: Float = parent.context.dip(2).toFloat(),
    val fontSet: FontValues = FontValues(textSizeRes = R.dimen.large_tag_text_size, textLineHeightRes = R.dimen.large_tag_line_height, fontRes = R.font.spoqa_hansansneo_regular),
    var clickListener: ((Int, LinkHashData) -> Unit)? = null): RecyclerView.ViewHolder(ui.createView(AnkoContext.create(parent.context, parent))) {

    fun onBind(list: ArrayList<LinkHashData>, position: Int) = with(ui) {
        list[position].let { item ->

            with(mCardView) {
                background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = radiusValue
                    setColor(Color.TRANSPARENT)
                }
            }

            // clickListener
            with(mTagLayout) {
                leftPadding = paddingValues.leftPadding
                topPadding = paddingValues.topPadding
                rightPadding = paddingValues.rightPadding
                bottomPadding = paddingValues.bottomPading
                setOnClickListener { clickListener?.invoke(position, item) }
                backgroundColor = item.tagColor.bgColor
            }

            // hashtag
            with(mTv) {
                text = "#${item.hashtagName}"
                textColor = item.tagColor.textColor
                setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(fontSet.textSizeRes))
                setLineSpacing(resources.getDimension(fontSet.textLineHeightRes), 1.0f)
                typeface = ResourcesCompat.getFont(context, fontSet.fontRes)
            }
            DLog.e("TAG", item.hashtagName)

        }
    }
}