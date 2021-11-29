package com.lomovskiy.pagedbsd.sample.pages.second

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheet
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheetVM
import com.lomovskiy.pagedbsd.PagedBottomSheetPage

class PageSecond : PagedBottomSheetPage<UuidsBottomSheetVM.Action, UuidsBottomSheet.State, UuidsBottomSheetVM>(
    R.layout.page_second,
    UuidsBottomSheetVM::class
) {

    private lateinit var list: RecyclerView

    private var listAdapter: PageSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.list)
        listAdapter = PageSecondLA {
            vm.handleAction(UuidsBottomSheetVM.Action.SelectedListItem(it))
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsBottomSheetVM.Action.PressedButtonBackToFirst)
    }

    override fun renderState(state: UuidsBottomSheet.State) {
        listAdapter?.submitList(state.uuids)
    }

}
