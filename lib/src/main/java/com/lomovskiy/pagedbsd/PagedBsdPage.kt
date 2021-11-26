package com.lomovskiy.pagedbsd

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

abstract class PagedBsdPage<A>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    protected val vm: PagedBsdViewModel<A> by viewModels(
        factoryProducer = { findViewModelProvider() },
        ownerProducer = { findViewModelStore() }
    )

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                onBackPressed()
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    abstract fun onBackPressed()

}
