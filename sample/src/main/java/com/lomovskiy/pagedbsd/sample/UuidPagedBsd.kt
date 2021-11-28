package com.lomovskiy.pagedbsd.sample

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lomovskiy.pagedbsd.PagedBsdNavigator

class UuidPagedBsd : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    private val vm: UuidsPagedBsdViewModel by viewModels(
        factoryProducer = { this },
        ownerProducer = { this }
    )

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.handleAction(UuidsPagedBsdViewModel.Action.Start)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UuidsPagedBsdViewModel(
            PagedBsdNavigator(
                R.id.container,
                this
            )
        ) as T
    }

    data class State(
        val selectedPosition: Int?,
        val selectedUuid: CharSequence?
    )

}
