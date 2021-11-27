package com.lomovskiy.pagedbsd

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lomovskiy.pagedbsd.navigation.PagedBsdNavigator

abstract class PagedBsd<VM, S, A> : BottomSheetDialogFragment(),
    PagedBsdViewModelProvider<VM>
        where S : PagedBsdState,
              A : PagedBsdViewModelAction,
              VM : PagedBsdViewModel<S, A> {

    protected abstract val viewModel: VM
    protected abstract val pageFactory: FragmentFactory

    protected val navigator: PagedBsdNavigator = PagedBsdNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = pageFactory
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {

            override fun onBackPressed() {
                requireActivity().onBackPressed()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paged_bottom_sheet, container, false)
    }

    override fun providePagedBsdViewModel(): VM {
        return viewModel
    }

}
