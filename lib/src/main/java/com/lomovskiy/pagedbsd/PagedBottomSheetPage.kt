package com.lomovskiy.pagedbsd

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

abstract class PagedBottomSheetPage<A, S, VM : PagedBottomSheetVM<A, S>>(
    @LayoutRes contentLayoutId: Int,
    viewModelClass: KClass<VM>
) : Fragment(contentLayoutId) {

    private var collectStateStreamJob: Job? = null

    protected val vm: VM by createViewModelLazy(
        viewModelClass,
        { (parentFragment as ViewModelStoreOwner).viewModelStore },
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
    }

    override fun onStart() {
        super.onStart()
        collectStateStreamJob = viewLifecycleOwner.lifecycleScope.launch {
            vm.getStateStream().collect(::renderState)
        }
    }

    override fun onStop() {
        collectStateStreamJob?.cancel()
        super.onStop()
    }

    abstract fun onBackPressed()

    abstract fun renderState(state: S)

}
