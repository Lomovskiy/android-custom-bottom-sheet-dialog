package com.lomovskiy.pagedbsd.sample.pages.third

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidPagedBsd
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdViewModel
import com.lomovskiy.pagedbsd.sample.pages.PageBase

class PageThird : PageBase(R.layout.page_third),
    View.OnClickListener {

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(UuidsPagedBsdViewModel.Action.Close)
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdViewModel.Action.PressedButtonBackToSecond)
    }

    override fun renderState(state: UuidPagedBsd.State) {
        buttonStub.text = state.selectedUuid
    }

}
