package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lomovskiy.pagedbsd.PagedBsd
import com.lomovskiy.pagedbsd.PagedBsdNavigator

class UuidPagedBsd : PagedBsd<UuidsPagedBsdViewModel.Action, UuidPagedBsd.State, UuidsPagedBsdViewModel>(
    UuidsPagedBsdViewModel.Action.Start
), ViewModelProvider.Factory {

    override val vm: UuidsPagedBsdViewModel by viewModels(
        factoryProducer = { this },
        ownerProducer = { this }
    )

    override val pageFactory: FragmentFactory = PageFactory()

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
