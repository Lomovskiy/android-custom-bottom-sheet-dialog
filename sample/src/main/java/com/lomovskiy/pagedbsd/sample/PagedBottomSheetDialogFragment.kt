package com.lomovskiy.pagedbsd.sample

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.IllegalStateException

class ScreenFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            PageFirst::class.java.name -> {
                return PageFirst()
            }
            PageSecond::class.java.name -> {
                return PageSecond()
            }
            PageThird::class.java.name -> {
                return PageThird()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

}

class PagedBottomSheetDialogFragment : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    private val navigator: Navigator<SelectUUIDNavCommand> =
        NavigatorImpl(this)

    private val depsProvider: DepsProvider by lazy(LazyThreadSafetyMode.NONE) {
        return@lazy getDepsProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = ScreenFactory()
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
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.handleCommand(SelectUUIDNavCommand.OpenFirstStep)
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(): PagedBottomSheetDialogFragment {
            return PagedBottomSheetDialogFragment()
        }

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            PagedBottomSheetDialogFragmentVM::class.java -> {
                return PagedBottomSheetDialogFragmentVM(
                    navigator,
                    depsProvider.provideDep1(),
                    depsProvider.provideDep2()
                ) as T
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    data class State(
        val selectedString: CharSequence?,
        val selectedNumber: Int?
    ) {

        companion object {

            fun empty(): State {
                return State(
                    selectedString = null,
                    selectedNumber = null
                )
            }

        }

    }



}

class PagedBottomSheetDialogFragmentVM(
    private val navigator: Navigator<SelectUUIDNavCommand>,
    private val deps1: String,
    private val deps2: String
) : ViewModel() {

    private val state = MutableLiveData(PagedBottomSheetDialogFragment.State.empty())

    fun getState(): LiveData<PagedBottomSheetDialogFragment.State> {
        return state
    }

    fun setState(state: PagedBottomSheetDialogFragment.State) {
        this.state.value = state
    }

    fun handleAction(action: Action) {
        when (action) {
            Action.Close -> {
                navigator.handleCommand(SelectUUIDNavCommand.Finish)
            }
            Action.OnListItemButtonPressed -> {
                navigator.handleCommand(SelectUUIDNavCommand.Finish)
            }
            is Action.OnListItemClicked -> {
                state.value = state.value!!.copy(selectedString = action.string)
                navigator.handleCommand(SelectUUIDNavCommand.OpenThirdStep)
            }
            is Action.OnNumberButtonPressed -> {
                state.value = state.value!!.copy(selectedNumber = action.number)
                navigator.handleCommand(SelectUUIDNavCommand.OpenSecondStep)
            }
            Action.OnBackToFirstStepPressed -> {
                state.value = state.value!!.copy(selectedNumber = null)
                navigator.handleCommand(SelectUUIDNavCommand.OpenFirstStep)
            }
            Action.OnBackToSecondStepPressed -> {
                state.value = state.value!!.copy(selectedString = null)
                navigator.handleCommand(SelectUUIDNavCommand.OpenSecondStep)
            }
        }
    }

    sealed class Action {
        class OnNumberButtonPressed(val number: Int) : Action()
        class OnListItemClicked(val string: CharSequence) : Action()
        object OnListItemButtonPressed : Action()
        object OnBackToFirstStepPressed : Action()
        object OnBackToSecondStepPressed : Action()
        object Close : Action()
    }

}
