package com.lomovskiy.pagedbsd

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

abstract class PagedBsdPage<A, S, VM : PagedBsdVM<A, S>>(
    @LayoutRes contentLayoutId: Int,
    private val viewModelClass: KClass<VM>
) : Fragment(contentLayoutId) {

    protected val vm: VM by viewModel(
        viewModelClass,
        { parentFragment as ViewModelStoreOwner },
        { parentFragment as ViewModelProvider.Factory }
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

    abstract fun renderState(state: S)

    private fun Fragment.viewModel(
        clazz: KClass<VM>,
        ownerProducer: () -> ViewModelStoreOwner,
        factoryProducer: (() -> ViewModelProvider.Factory),
    ) = createViewModelLazy(clazz, { ownerProducer().viewModelStore }, factoryProducer)

}
