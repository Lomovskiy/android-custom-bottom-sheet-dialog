package com.lomovskiy.pagedbsd.sample.pages.second

import androidx.recyclerview.widget.DiffUtil

class PageSecondItemCallback : DiffUtil.ItemCallback<CharSequence>() {

    override fun areItemsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

}
