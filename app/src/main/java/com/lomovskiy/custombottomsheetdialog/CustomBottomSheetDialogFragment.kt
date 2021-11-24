package com.lomovskiy.custombottomsheetdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScreenFactory(
    private val coordinator: Coordinator
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            ScreenFirst::class.java.name -> {
                return ScreenFirst(coordinator)
            }
            ScreenSecond::class.java.name -> {
                return ScreenSecond(coordinator)
            }
            ScreenThird::class.java.name -> {
                return ScreenThird(coordinator)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

}

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment(), Navigator {

    private lateinit var dialog: BottomSheetDialog

    private val coordinator: Coordinator = CoordinatorImpl(this)

    override fun handleState(state: State) {
        when (state) {
            State.PAGE_ONE -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.container, ScreenFirst::class.java, null)
                    .commit()
            }
            State.PAGE_TWO -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.container, ScreenSecond::class.java, null)
                    .commit()
            }
            State.PAGE_THREE -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.container, ScreenThird::class.java, null)
                    .commit()
            }
            State.CLOSED -> {
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = ScreenFactory(coordinator)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = object : BottomSheetDialog(requireContext(), theme) {

            override fun onBackPressed() {
                coordinator.back()
            }

        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinator.start()
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(): CustomBottomSheetDialogFragment {
            return CustomBottomSheetDialogFragment()
        }

    }

}