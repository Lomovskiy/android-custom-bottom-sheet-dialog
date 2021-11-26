package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

class PageThird : PageBase(R.layout.page_third), View.OnClickListener {

    private val vm: PagedBottomSheetDialogFragmentVM by viewModels(
        factoryProducer = { requireParentFragment() as ViewModelProvider.Factory },
        ownerProducer = { requireParentFragment() }
    )

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
