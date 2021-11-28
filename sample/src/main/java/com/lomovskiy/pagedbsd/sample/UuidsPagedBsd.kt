package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lomovskiy.pagedbsd.PagedBsd
import com.lomovskiy.pagedbsd.PagedBsdNavigator

class UuidsPagedBsd : PagedBsd<UuidsPagedBsdVM.Action, UuidsPagedBsd.State, UuidsPagedBsdVM>(
    UuidsPagedBsdVM.Action.Start
), ViewModelProvider.Factory {

    override val vm: UuidsPagedBsdVM by viewModels(
        factoryProducer = { this },
        ownerProducer = { this }
    )

    override val pageFactory: FragmentFactory = PageFactory()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UuidsPagedBsdVM(
            PagedBsdNavigator(
                R.id.container,
                this
            )
        ) as T
    }

    data class State(
        val selectedPosition: Int?,
        val selectedUuid: CharSequence?
    ) {

        companion object {

            fun empty(): State {
                return State(
                    selectedPosition = null,
                    selectedUuid = null
                )
            }

        }

    }

}
