package com.lomovskiy.pagedbsd.sample.pages.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsd
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdVM
import com.lomovskiy.pagedbsd.PagedBsdPage
import java.util.*

class PageSecond : PagedBsdPage<UuidsPagedBsdVM.Action, UuidsPagedBsd.State, UuidsPagedBsdVM>(
    R.layout.page_second,
    UuidsPagedBsdVM::class
) {

    private lateinit var list: RecyclerView

    private var listAdapter: PageSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.list)
        listAdapter = PageSecondLA {
            vm.handleAction(UuidsPagedBsdVM.Action.SelectedListItem(it))
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdVM.Action.PressedButtonBackToFirst)
    }

    override fun renderState(state: UuidsPagedBsd.State) {
        listAdapter?.submitList(List(state.selectedPosition ?: 0) {
            UUID.randomUUID().toString()
        })
    }

}
