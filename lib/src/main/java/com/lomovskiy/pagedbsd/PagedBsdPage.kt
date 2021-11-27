package com.lomovskiy.pagedbsd

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.lomovskiy.pagedbsd.navigation.Page

abstract class PagedBsdPage<VM, S, A>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), Page
        where
              VM : PagedBsdViewModel<S, A>,
              A : PagedBsdViewModelAction {

    protected val vm: PagedBsdViewModel<S, A> = findPagedBsdViewModelProvider<VM>()
        .providePagedBsdViewModel()

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
