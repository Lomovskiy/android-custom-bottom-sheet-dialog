package com.lomovskiy.pagedbsd.sample.pages.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lomovskiy.pagedbsd.PagedBsdPage
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdViewModel
import java.util.*

class PageSecond : PagedBsdPage<UuidsPagedBsdViewModel, UuidsPagedBsdViewModel.State, UuidsPagedBsdViewModel.Action>(R.layout.page_second) {

    override val key: String = PageSecond::class.java.simpleName
    override val clazz: Class<out Fragment> = PageSecond::class.java

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
            vm.handleAction(UuidsPagedBsdViewModel.Action.OnListItemClicked(it))
        }
        val lm = LinearLayoutManager(requireContext())
        list.layoutManager = lm
        list.addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        list.adapter = listAdapter
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdViewModel.Action.OnBackToFirstStepPressed)
    }


}
