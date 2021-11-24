package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

class ScreenThird : Fragment(R.layout.screen_third), View.OnClickListener {

    private val vm: PagedBottomSheetDialogFragmentVM by viewModels(
        factoryProducer = { requireParentFragment() as ViewModelProvider.Factory },
        ownerProducer = { requireParentFragment() }
    )

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.OnBackToSecondStepPressed)
        }

    }

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        vm.getState().observe(viewLifecycleOwner, {
            buttonStub.text = it.selectedString
        })
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.OnListItemButtonPressed)
    }

}
