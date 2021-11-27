package com.lomovskiy.pagedbsd.sample.pages.second

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lomovskiy.pagedbsd.sample.R

class PageSecondLAVH(
    itemView: View,
    private val onClickListener: (item: CharSequence) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: CharSequence) {
        label.text = item
        label.setOnClickListener {
            onClickListener(item)
        }
    }

}