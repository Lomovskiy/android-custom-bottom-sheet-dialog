package com.lomovskiy.custombottomsheetdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScreenFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            ScreenFirst::class.java.name -> {
                return ScreenFirst()
            }
            ScreenSecond::class.java.name -> {
                return ScreenSecond()
            }
            ScreenThird::class.java.name -> {
                return ScreenThird()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

}

class PagedBottomSheetDialogFragment : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    private lateinit var dialog: BottomSheetDialog

    private val navigator: Navigator = NavigatorImpl(this)
    private val coordinator: Coordinator = CoordinatorImpl(navigator)

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = ScreenFactory()
        super.onCreate(savedInstanceState)
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
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinator.handleCommand(Coordinator.Command.OpenFirstStep)
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(): PagedBottomSheetDialogFragment {
            return PagedBottomSheetDialogFragment()
        }

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            ScreenFirstVM::class.java -> {
                return ScreenFirstVM(coordinator) as T
            }
            ScreenSecondVM::class.java -> {
                return ScreenSecondVM(coordinator) as T
            }
            ScreenThirdVM::class.java -> {
                return ScreenThirdVM(coordinator) as T
            }
            else -> {
                return modelClass.newInstance()
            }
        }
    }

}
