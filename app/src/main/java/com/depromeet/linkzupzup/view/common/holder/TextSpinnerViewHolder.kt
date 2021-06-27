package com.depromeet.linkzupzup.view.common.holder

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.view.common.PaddingValues
import org.jetbrains.anko.*
import java.util.*

class TextSpinnerViewHolder(
    parent: ViewGroup,
    val ui: TextSpinnerUI,
    val paddings: PaddingValues = PaddingValues(padding = parent.context.dip(16)),
    var touchListener: (()->Unit)? = null,
    var clickListener: ((Int, String) -> Unit)? = null): RecyclerView.ViewHolder(ui.createView(AnkoContext.create(parent.context, parent))) {

    fun onBind(list: ArrayList<String>, position: Int, isPick: Boolean, suffix: String = "") = with(ui) {
        list[position].let { item ->

            mCardView.setOnClickListener { clickListener?.invoke(position, item) }
            mCardView.setOnTouchListener { view, motionEvent ->
                touchListener?.invoke()
                true
            }

            with(mBodyLayout) {
                // leftPadding = paddings.leftPadding
                // topPadding = paddings.topPadding
                // rightPadding = paddings.rightPadding
                // bottomPadding = paddings.bottomPading
            }

            with(mTv) {
                text = "$item${suffix}"

                if (isPick) {

                    typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.spinner_text_size))
                    setLineSpacing(resources.getDimension(R.dimen.spinner_line_height), 1.0f)
                    textColor = Color.parseColor("#292A2B")
                    gravity = Gravity.CENTER

                } else {

                    typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_regular)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.spinner_text_size))
                    setLineSpacing(resources.getDimension(R.dimen.spinner_line_height), 1.0f)
                    textColor = Color.parseColor("#CED3D6")
                    gravity = Gravity.CENTER

                }
            }

            mUnderLIne.visibility = if (list.size -1 >= position) View.GONE else View.VISIBLE

        }
    }
}