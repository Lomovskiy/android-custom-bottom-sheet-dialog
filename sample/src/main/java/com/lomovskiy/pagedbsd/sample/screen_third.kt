package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lomovskiy.pagedbsd.PagedBsdPage

class PageThird : PagedBsdPage<UuidsPagedBsdVM, UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>(R.layout.page_third),
    View.OnClickListener {

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getStateStream().observe(viewLifecycleOwner, {
            buttonStub.text = it.selectedUuid
        })
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(UuidsPagedBsdVM.Action.OnListItemButtonPressed)
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdVM.Action.OnBackToSecondStepPressed)
    }

}
