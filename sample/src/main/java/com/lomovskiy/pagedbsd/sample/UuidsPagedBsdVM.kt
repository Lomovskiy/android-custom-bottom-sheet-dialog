package com.lomovskiy.pagedbsd.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomovskiy.pagedbsd.PagedBsdState
import com.lomovskiy.pagedbsd.PagedBsdViewModel
import com.lomovskiy.pagedbsd.PagedBsdViewModelAction
import com.lomovskiy.pagedbsd.navigation.PagedBsdCoordinator

class UuidsPagedBsdVM(
    private val coordinator: PagedBsdCoordinator
) : PagedBsdViewModel<UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>(coordinator) {

    private val state = MutableLiveData(State.empty())

    override fun handleAction(action: Action) {
        when (action) {
            Action.Close -> {
                coordinator.finish()
            }
            Action.OnListItemButtonPressed -> {
                coordinator.finish()
            }
            is Action.OnListItemClicked -> {
                state.value = state.value!!.copy(selectedUuid = action.string)
                coordinator.replaceOn(Pages.PageThird)
            }
            is Action.OnNumberButtonPressed -> {
                state.value = state.value!!.copy(selectedPosition = action.number)
                coordinator.replaceOn(Pages.PageSecond)
            }
            Action.OnBackToFirstStepPressed -> {
                state.value = state.value!!.copy(selectedPosition = null)
                coordinator.backTo(Pages.PageFirst)
            }
            Action.OnBackToSecondStepPressed -> {
                state.value = state.value!!.copy(selectedPosition = null)
                coordinator.backTo(Pages.PageSecond)
            }
        }
    }

    override fun getStateStream(): LiveData<State> {
        return state
    }

    data class State(
        val selectedPosition: Int?,
        val selectedUuid: CharSequence?
    ) : PagedBsdState {

        companion object {

            fun empty(): State {
                return State(
                    selectedPosition = null,
                    selectedUuid = null
                )
            }

        }

    }

    sealed class Action : PagedBsdViewModelAction {
        class OnNumberButtonPressed(val number: Int) : Action()
        class OnListItemClicked(val string: CharSequence) : Action()
        object OnListItemButtonPressed : Action()
        object OnBackToFirstStepPressed : Action()
        object OnBackToSecondStepPressed : Action()
        object Close : Action()
    }

}
