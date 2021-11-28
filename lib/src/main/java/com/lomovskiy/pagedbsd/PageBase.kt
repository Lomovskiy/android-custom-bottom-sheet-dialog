package com.lomovskiy.pagedbsd

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class PageBase<A, S, VM : BaseViewModel<A, S>>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    abstract val vm: VM

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                onBackPressed()
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        vm.getStateStream().observe(viewLifecycleOwner, ::renderState)
    }

    abstract fun onBackPressed()

    abstract fun renderState(state: S)

}
