package com.lomovskiy.pagedbsd.sample

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lomovskiy.pagedbsd.navigation.Coordinator
import com.lomovskiy.pagedbsd.navigation.PagedBsdCoordinator
import com.lomovskiy.pagedbsd.navigation.PagedBsdNavigator

class UuidPagedBsd : BottomSheetDialogFragment() {

    private val navigator: PagedBsdNavigator = PagedBsdNavigator(
        R.id.container,
        childFragmentManager,
        parentFragmentManager
    )

    private val coordinator: Coordinator = PagedBsdCoordinator(navigator)

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = PageFactory()
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

}
