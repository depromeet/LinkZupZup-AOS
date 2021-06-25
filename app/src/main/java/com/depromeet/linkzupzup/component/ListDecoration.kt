package com.depromeet.linkzupzup.component

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListDecoration(var direction: Int, var dividerSize: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let { adapter ->
            if (parent.getChildAdapterPosition(view) != adapter.itemCount - 1) {
                RecyclerView.HORIZONTAL
                when (direction) {
                    RecyclerView.HORIZONTAL -> { outRect.right = dividerSize }
                    else -> { outRect.bottom = dividerSize }
                }
            }
        }
    }
}