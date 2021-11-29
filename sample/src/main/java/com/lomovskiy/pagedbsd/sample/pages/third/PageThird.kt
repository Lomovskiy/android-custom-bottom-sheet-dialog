package com.lomovskiy.pagedbsd.sample.pages.third

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheet
import com.lomovskiy.pagedbsd.sample.UuidsPagedBottomSheetVM
import com.lomovskiy.pagedbsd.PagedBottomSheetPage

class PageThird : PagedBottomSheetPage<UuidsPagedBottomSheetVM.Action, UuidsBottomSheet.State, UuidsPagedBottomSheetVM>(
    R.layout.page_third,
    UuidsPagedBottomSheetVM::class
), View.OnClickListener {

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(UuidsPagedBottomSheetVM.Action.Close)
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBottomSheetVM.Action.PressedButtonBackToSecond)
    }

    override fun renderState(state: UuidsBottomSheet.State) {
        buttonStub.text = state.selectedUuid
    }

}
