package com.depromeet.linkzupzup.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract val ctx: Context

    companion object {
        const val TYPE_HEADER = Integer.MIN_VALUE
        const val TYPE_FOOTER = Integer.MIN_VALUE + 1
    }

    var rv: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    /**
     * Total Count
     */
    override fun getItemCount(): Int {
        var itemCount = basicItemCount
        if (useHeader()) itemCount += 1
        if (useFooter()) itemCount += 1
        return itemCount
    }

    /**
     * ViewHolder Type
     */
    override fun getItemViewType(position: Int): Int {
        return when {
            (position == 0 && useHeader()) -> TYPE_HEADER
            (position == itemCount - 1 && useFooter()) -> TYPE_FOOTER
            else -> if (useHeader()) position - 1 else position
        }
    }

    /**
     * Create ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        = when (viewType) {
            TYPE_HEADER -> onCreateHeaderViewHolder(parent, viewType)!!
            TYPE_FOOTER -> onCreateFooterViewHolder(parent, viewType)!!
            else -> onCreateBasicViewHolder(parent, viewType)
        }


    /**
     * Bind ViewHolder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            (position == 0 && useHeader()) -> onBindHeaderItemView(holder, position)
            (position == itemCount - 1 && useFooter()) -> onBindFooterItemView(holder, position)
            else -> {
                val basicPosition = if (useHeader()) position - 1 else position
                onBindBasicItemView(holder, basicPosition)
            }
        }
    }

    /**
     * Header
     */
    open fun useHeader() = false
    open fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? = null
    open fun onBindHeaderItemView(holder: RecyclerView.ViewHolder, position: Int) {}

    /**
     * Footer
     */
    open fun useFooter() = false
    open fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? = null
    open fun onBindFooterItemView(holder: RecyclerView.ViewHolder, position: Int) {}

    /**
     * Default View Holder
     */
    abstract fun onCreateBasicViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindBasicItemView(holder: RecyclerView.ViewHolder?, position: Int)

    private var basicItemCount: Int = 0
    fun getBasicItemCount() = basicItemCount
    fun setBasicItemCount(count: Int) {
        basicItemCount = count
    }

    /**
     * Utils
     */
    fun postRv(block: RecyclerView.() -> Unit) {
        rv?.run { post { block() } }
    }

    fun postDelayedRv(millis: Long, block: RecyclerView.() -> Unit) {
        rv?.run { postDelayed({ block() }, millis) }
    }
}