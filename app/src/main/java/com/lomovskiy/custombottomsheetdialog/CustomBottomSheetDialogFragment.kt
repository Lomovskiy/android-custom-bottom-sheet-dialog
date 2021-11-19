package com.lomovskiy.custombottomsheetdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment(),
    NavigationRouter<CustomBottomSheetNavigationMessage> {

    private lateinit var dialog: BottomSheetDialog

    override fun handleNavigationMessage(message: CustomBottomSheetNavigationMessage) {
        when (message) {
            CustomBottomSheetNavigationMessage.Back -> {
                if (childFragmentManager.backStackEntryCount == 0) {
                    dismiss()
                    return
                }
                childFragmentManager.popBackStack()
            }
            CustomBottomSheetNavigationMessage.OpenScreenFirst -> {
                openScreen(
                    containerViewId = R.id.container,
                    screen = ScreenFirst::class.java,
                    tag = ScreenFirst::class.java.simpleName,
                    addToBackStack = true
                )
            }
            CustomBottomSheetNavigationMessage.OpenScreenSecond -> {
                openScreen(
                    containerViewId = R.id.container,
                    screen = ScreenSecond::class.java,
                    tag = ScreenSecond::class.java.simpleName,
                    addToBackStack = true
                )
            }
            CustomBottomSheetNavigationMessage.OpenScreenThird -> {
                openScreen(
                    containerViewId = R.id.container,
                    screen = ScreenThird::class.java,
                    tag = ScreenThird::class.java.simpleName,
                    addToBackStack = true
                )
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = object : BottomSheetDialog(requireContext(), theme) {

            override fun onBackPressed() {
                handleNavigationMessage(CustomBottomSheetNavigationMessage.Back)
            }

        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openScreen(
            containerViewId = R.id.container,
            screen = ScreenFirst::class.java,
            tag = ScreenFirst::class.java.simpleName,
            addToBackStack = false
        )
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(): CustomBottomSheetDialogFragment {
            return CustomBottomSheetDialogFragment()
        }

    }

}