package com.lomovskiy.pagedbsd.sample.pages.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.lomovskiy.pagedbsd.PagedBsdPage
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdVM
import java.util.*

class PageSecond : PagedBsdPage<UuidsPagedBsdVM, UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>(R.layout.page_second) {

    private lateinit var list: RecyclerView

    private var listAdapter: PageSecondLA? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getStateStream().observe(viewLifecycleOwner, {
            listAdapter?.submitList(List(it.selectedPosition ?: 0) {
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
