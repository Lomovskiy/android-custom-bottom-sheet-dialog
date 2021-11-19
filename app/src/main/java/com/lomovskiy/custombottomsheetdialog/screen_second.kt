package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import java.util.*

class ScreenSecond : Fragment(R.layout.screen_second) {

    private lateinit var list: RecyclerView

    private var listAdapter: ScreenSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.list)
        listAdapter = ScreenSecondLA {
            sendNavigationMessage(CustomBottomSheetNavigationMessage.OpenScreenThird)
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
        listAdapter?.submitList(List(100) {
            UUID.randomUUID().toString()
        })
    }


}

class ScreenSecondLA(
    private val onClickListener: () -> Unit
) : ListAdapter<CharSequence, ScreenSecondLAVH>(ScreenSecondItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenSecondLAVH {
        return ScreenSecondLAVH(
            LayoutInflater.from(parent.context).inflate(R.layout.screen_second_list_item, parent, false),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: ScreenSecondLAVH, position: Int) {
        holder.bind(currentList[position])
    }


}


class ScreenSecondLAVH(
    itemView: View,
    private val onClickListener: () -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: CharSequence) {
        label.text = item
        label.setOnClickListener {
            onClickListener()
        }
    }

}

class ScreenSecondItemCallback : DiffUtil.ItemCallback<CharSequence>() {

    override fun areItemsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

}
