package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import java.util.*

class PageSecond : PageBase(R.layout.page_second) {

    private val vm: PagedBottomSheetDialogFragmentVM by viewModels(
        factoryProducer = { requireParentFragment() as ViewModelProvider.Factory },
        ownerProducer = { requireParentFragment() }
    )

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
            vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.OnListItemClicked(it))
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
    }

    override fun onBackPressed() {
        vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.OnBackToFirstStepPressed)
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
