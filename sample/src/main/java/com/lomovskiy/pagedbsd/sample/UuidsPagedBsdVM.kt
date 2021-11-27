package com.lomovskiy.pagedbsd.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomovskiy.pagedbsd.PagedBsdState
import com.lomovskiy.pagedbsd.PagedBsdViewModel
import com.lomovskiy.pagedbsd.PagedBsdViewModelAction

class UuidsPagedBsdVM(
    private val navigator: Navigator<SelectUUIDNavCommand>
) : PagedBsdViewModel<UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>() {

    private val state = MutableLiveData(State.empty())

    override fun handleAction(action: Action) {
        when (action) {
            Action.Close -> {
                navigator.handleCommand(SelectUUIDNavCommand.Finish)
            }
            Action.OnListItemButtonPressed -> {
                navigator.handleCommand(SelectUUIDNavCommand.Finish)
            }
            is Action.OnListItemClicked -> {
                state.value = state.value!!.copy(selectedUuid = action.string)
                navigator.handleCommand(SelectUUIDNavCommand.OpenThirdStep)
            }
            is Action.OnNumberButtonPressed -> {
                state.value = state.value!!.copy(selectedPosition = action.number)
                navigator.handleCommand(SelectUUIDNavCommand.OpenSecondStep)
            }
            Action.OnBackToFirstStepPressed -> {
                state.value = state.value!!.copy(selectedPosition = null)
                navigator.handleCommand(SelectUUIDNavCommand.OpenFirstStep)
            }
            Action.OnBackToSecondStepPressed -> {
                state.value = state.value!!.copy(selectedPosition = null)
                navigator.handleCommand(SelectUUIDNavCommand.OpenSecondStep)
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
