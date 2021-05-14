package com.depromeet.linkzupzup.component


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.utils.DLog

class SliderAdapter(callback: (View)->Unit): RecyclerView.Adapter<SliderItemViewHolder>() {

    private val data: ArrayList<String> = ArrayList();
    private val clickListener = View.OnClickListener { v -> v?.let { callback.invoke(it) } }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.slider_holder_layout, parent, false)
        itemView.setOnClickListener(clickListener)
        DLog.e("ADAPTER", "onCreateViewHolder")
        return SliderItemViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: SliderItemViewHolder, position: Int) {
        holder.textView.text = data[position]
        DLog.e("ADAPTER", "onBindViewHolder")
    }

    fun setData(data: ArrayList<String>) {
        this.data.clear()
        this.data.addAll(data)
        DLog.e("ADAPTER", "setData, ${data.size}")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        DLog.e("ADAPTER", "getItemCount, ${data.size}")
        return data.size
    }

}

class SliderItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.slider_tv)
}