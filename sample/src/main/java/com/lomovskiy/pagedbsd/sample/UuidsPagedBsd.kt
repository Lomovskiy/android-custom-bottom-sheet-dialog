package com.lomovskiy.pagedbsd.sample

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.lomovskiy.pagedbsd.Navigator
import com.lomovskiy.pagedbsd.PagedBsd
import com.lomovskiy.pagedbsd.PagedBsdNavigator

class UuidsPagedBsd : PagedBsd<UuidsPagedBsdVM.Action, UuidsPagedBsd.State, UuidsPagedBsdVM>(
    UuidsPagedBsdVM::class,
    UuidsPagedBsdVM.Action.Start
) {

    override val pageFactory: FragmentFactory = PageFactory()

    override fun <T : ViewModel?> onCreateViewModel(modelClass: Class<T>): UuidsPagedBsdVM {
        val coordinator: Coordinator = UuidPagedBsdCoordinator(navigator)
        return UuidsPagedBsdVM(coordinator)
    }

    override fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        Toast.makeText(
            requireContext(),
            "ft: $fragmentTransaction\ncf: $currentFragment\nnf: $nextFragment",
            Toast.LENGTH_SHORT
        ).show()
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

}
