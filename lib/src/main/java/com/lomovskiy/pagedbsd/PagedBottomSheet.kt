package com.lomovskiy.pagedbsd

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.reflect.KClass

abstract class PagedBottomSheet<C : PagedBottomSheet.Config, A, S, VM : PagedBottomSheetVM<A, S>>(
    viewModelClass: KClass<VM>,
    private val initialAction: A
) : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    abstract val pageFactory: FragmentFactory

    protected val vm: VM by createViewModelLazy(
        viewModelClass,
        { this.viewModelStore },
        { this }
    )

    protected val config: C
        get() = requireArguments().getParcelable(EXTRA_CONFIG) ?:
        throw IllegalStateException("Config must be provided!")

    protected lateinit var navigator: Navigator

    abstract fun <T : ViewModel?> onCreateViewModel(modelClass: Class<T>): VM

    abstract fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = pageFactory
        super.onCreate(savedInstanceState)
        setStyle(
            config.getConfigStyle() ?: DialogFragment.STYLE_NORMAL,
            config.getConfigTheme() ?: 0
        )
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
        navigator = object : PagedBsdNavigator(R.id.container, this) {

            override fun setupFragmentTransaction(
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                this@PagedBottomSheet.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
            }

        }
        vm.handleAction(initialAction)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return onCreateViewModel(modelClass) as T
    }

    interface Config : Parcelable {

        fun getConfigStyle(): Int?

        fun getConfigTheme(): Int?

    }

    companion object {

        const val EXTRA_CONFIG = "EXTRA_CONFIG"

    }

}
