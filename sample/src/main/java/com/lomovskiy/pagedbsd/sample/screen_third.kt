package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.View
import android.widget.Button

class PageThird : PageBase(R.layout.page_third), View.OnClickListener {

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getState().observe(viewLifecycleOwner, {
            buttonStub.text = it.selectedString
        })
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.OnListItemButtonPressed)
    }

    override fun onBackPressed() {
        vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.OnBackToSecondStepPressed)
    }

}
