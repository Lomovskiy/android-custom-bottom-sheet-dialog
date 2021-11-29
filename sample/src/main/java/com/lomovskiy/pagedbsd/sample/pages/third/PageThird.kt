package com.lomovskiy.pagedbsd.sample.pages.third

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheet
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheetVM
import com.lomovskiy.pagedbsd.PagedBottomSheetPage

class PageThird : PagedBottomSheetPage<UuidsBottomSheetVM.Action, UuidsBottomSheet.State, UuidsBottomSheetVM>(
    R.layout.page_third,
    UuidsBottomSheetVM::class
), View.OnClickListener {

    private lateinit var buttonStub: Button
    private lateinit var buttonBackToFirst: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStub = view.findViewById(R.id.button_stub)
        buttonBackToFirst = view.findViewById(R.id.button_back_to_first)
        buttonStub.setOnClickListener(this)
        buttonBackToFirst.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_stub -> {
                vm.handleAction(UuidsBottomSheetVM.Action.Close)
            }
            R.id.button_back_to_first -> {
                vm.handleAction(UuidsBottomSheetVM.Action.PressedButtonBackToFirst)
            }
        }
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsBottomSheetVM.Action.PressedButtonBackToSecond)
    }

    override fun renderState(state: UuidsBottomSheet.State) {
        buttonStub.text = state.selectedUuid
    }

}
