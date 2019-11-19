package com.yuriy.primenumbers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rec_view_item.view.*

class NumbersAdapter(private val numbers : List<Int>) : RecyclerView.Adapter<NumbersAdapter.NumbersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rec_view_item, parent, false)
        return NumbersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.bind(numbers[position])
    }



    inner class NumbersViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(number: Int) = with(itemView){
            id_item_number.text = number.toString()
        }
    }
}