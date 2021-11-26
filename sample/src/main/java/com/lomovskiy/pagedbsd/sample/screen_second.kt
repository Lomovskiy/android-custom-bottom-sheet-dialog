package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.lomovskiy.pagedbsd.PagedBsdPage
import java.util.*

class PageSecond : PagedBsdPage<UuidsPagedBsdVM.Action>(R.layout.page_second) {

    private lateinit var list: RecyclerView

    private var listAdapter: PageSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getState().observe(viewLifecycleOwner, {
            listAdapter?.submitList(List(it.selectedNumber ?: 0) {
                UUID.randomUUID().toString()
            })
        })
        list = view.findViewById(R.id.list)
        listAdapter = PageSecondLA {
            vm.handleAction(UuidsPagedBsdVM.Action.OnListItemClicked(it))
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdVM.Action.OnBackToFirstStepPressed)
    }


}

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

class PageSecondItemCallback : DiffUtil.ItemCallback<CharSequence>() {

    override fun areItemsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

}
