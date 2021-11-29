package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.lomovskiy.pagedbsd.PagedBottomSheet
import kotlinx.parcelize.Parcelize

class UuidsBottomSheet : PagedBottomSheet<UuidsBottomSheet.Config, UuidsBottomSheetVM.Action, UuidsBottomSheet.State, UuidsBottomSheetVM>(
    UuidsBottomSheetVM::class,
    UuidsBottomSheetVM.Action.Start
) {

    override val pageFactory: FragmentFactory = PageFactory()

    override fun <T : ViewModel?> onCreateViewModel(modelClass: Class<T>): UuidsBottomSheetVM {
        val coordinator: Coordinator = UuidPagedBottomSheetCoordinator(navigator)
        return UuidsBottomSheetVM(coordinator)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), config.anotherConfig, Toast.LENGTH_SHORT).show()
    }

    override fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        // animation ?
    }

    data class State(
        val selectedUuid: CharSequence?,
        val uuids: List<CharSequence>?
    ) {

        companion object {

            fun empty(): State {
                return State(
                    selectedUuid = null,
                    uuids = null
                )
            }

        }

    }

    @Parcelize
    class Config(
        val style: Int?,
        val theme: Int?,
        val anotherConfig: String
    ) : PagedBottomSheet.Config {

        override fun getConfigStyle(): Int? {
            return style
        }

        override fun getConfigTheme(): Int? {
            return theme
        }

        companion object {

            fun default(): Config {
                return Config(null, null, "")
            }

        }

    }

    companion object {

        fun newInstance(config: Config): UuidsBottomSheet {
            return UuidsBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_CONFIG, config)
                }
            }
        }

    }

}
