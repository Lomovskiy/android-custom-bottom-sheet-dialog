package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import java.util.*

class ScreenSecond : Fragment(R.layout.screen_second) {

    private val vm: ScreenSecondVM by viewModels(
        factoryProducer = {
            requireParentFragment() as ViewModelProvider.Factory
        }
    )

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            vm.handleAction(ScreenSecondVM.Action.OnBackPressed)
        }

    }

    private lateinit var list: RecyclerView

    private var listAdapter: ScreenSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        list = view.findViewById(R.id.list)
        listAdapter = ScreenSecondLA {
            vm.handleAction(ScreenSecondVM.Action.OnListItemClicked(it))
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
    private val onClickListener: (item: CharSequence) -> Unit
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

class ScreenSecondItemCallback : DiffUtil.ItemCallback<CharSequence>() {

    override fun areItemsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean {
        return oldItem == newItem
    }

}

class ScreenSecondVM(
    private val coordinator: Coordinator
) : ViewModel() {

    fun handleAction(action: Action) {
        when (action) {
            Action.OnBackPressed -> {
                coordinator.handleCommand(Coordinator.Command.OpenFirstStep)
            }
            is Action.OnListItemClicked -> {
                coordinator.handleCommand(Coordinator.Command.OpenThirdStep(action.item))
            }
        }
    }

    sealed class Action {
        object OnBackPressed : Action()
        class OnListItemClicked(val item: CharSequence) : Action()
    }

}
