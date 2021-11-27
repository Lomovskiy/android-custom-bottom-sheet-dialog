package com.lomovskiy.pagedbsd.sample.pages

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lomovskiy.pagedbsd.sample.UuidPagedBsd
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdViewModel

abstract class PageBase(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    protected val vm: UuidsPagedBsdViewModel by viewModels(
        ownerProducer = { parentFragment as ViewModelStoreOwner },
        factoryProducer = { parentFragment as ViewModelProvider.Factory }
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
        vm.getStateStream().observe(viewLifecycleOwner, ::renderState)
    }

    abstract fun onBackPressed()

    abstract fun renderState(state: UuidPagedBsd.State)

}
