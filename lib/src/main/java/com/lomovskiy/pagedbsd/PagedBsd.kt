package com.lomovskiy.pagedbsd

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.reflect.KClass

abstract class PagedBsd<A, S, VM : PagedBsdVM<A, S>>(
    viewModelClass: KClass<VM>,
    private val initialAction: A
) : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    abstract val pageFactory: FragmentFactory

    protected val vm: VM by createViewModelLazy(
        viewModelClass,
        { this.viewModelStore },
        { this }
    )

    protected lateinit var navigator: Navigator

    abstract fun <T : ViewModel?> onCreateViewModel(modelClass: Class<T>): VM

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = PagedBsdNavigator(R.id.container, this)
        vm.handleAction(initialAction)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return onCreateViewModel(modelClass) as T
    }

}
