package com.lomovskiy.pagedbsd.sample.pages.second

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.lomovskiy.pagedbsd.sample.R

class PageSecondLA(
    private val onClickListener: (item: CharSequence) -> Unit
) : ListAdapter<CharSequence, PageSecondLAVH>(PageSecondItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageSecondLAVH {
        return PageSecondLAVH(
            LayoutInflater.from(parent.context).inflate(R.layout.page_second_list_item, parent, false),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: PageSecondLAVH, position: Int) {
        holder.bind(currentList[position])
    }


}
