package com.lomovskiy.pagedbsd.sample.pages.second

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidPagedBsd
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdViewModel
import com.lomovskiy.pagedbsd.sample.pages.PageBase
import java.util.*

class PageSecond : PageBase(R.layout.page_second) {

    private lateinit var list: RecyclerView

    private var listAdapter: PageSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.list)
        listAdapter = PageSecondLA {
            vm.handleAction(UuidsPagedBsdViewModel.Action.SelectedListItem(it))
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdViewModel.Action.PressedButtonBackToFirst)
    }

    override fun renderState(state: UuidPagedBsd.State) {
        listAdapter?.submitList(List(state.selectedPosition ?: 0) {
            UUID.randomUUID().toString()
        })
    }


}
