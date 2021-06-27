package com.depromeet.linkzupzup.view.common.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.base.BaseAdapter
import com.depromeet.linkzupzup.view.common.holder.TextSpinnerUI
import com.depromeet.linkzupzup.view.common.holder.TextSpinnerViewHolder
import java.util.*

class TextSpinnerAdapter(override val ctx: Context, dataList: ArrayList<String> = arrayListOf(), var suffix: String = "") : BaseAdapter<TextSpinnerViewHolder>() {

    private var curPickPosition = 0
    private var list: ArrayList<String> = dataList
    private var clickListener: ((Int, String) -> Unit)? = null
    private var touchListener: (()->Unit)? = null

    init { setBasicItemCount(list.size) }

    override fun onCreateBasicViewHolder(parent: ViewGroup, viewType: Int): TextSpinnerViewHolder
        = TextSpinnerViewHolder(parent= parent, ui= TextSpinnerUI(), touchListener= touchListener) { position, data ->
        curPickPosition = position
        clickListener?.invoke(position, data)
    }

    override fun onBindBasicItemView(holder: TextSpinnerViewHolder?, position: Int) {
        holder?.onBind(list, position, position == curPickPosition, suffix)
    }

    fun initList(dataList: ArrayList<String> = arrayListOf()) {
        list.clear()
        list.addAll(dataList)
        setBasicItemCount(list.size)
    }

    fun setOnClickListener(onClickListener: (Int, String) -> Unit) {
        clickListener = onClickListener
    }

    fun setTouchListener(touchListener: ()->Unit) {
        this.touchListener = touchListener
    }

//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        recyclerView.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
//            override fun onChildViewAttachedToWindow(view: View) {
//                view.layoutParams = view.layoutParams.apply {
//
//                }
//            }
//
//            override fun onChildViewDetachedFromWindow(view: View) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}