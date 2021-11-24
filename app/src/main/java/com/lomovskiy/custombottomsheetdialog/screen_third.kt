package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScreenThird : Fragment(R.layout.screen_third), View.OnClickListener {

    private val vm: ScreenThirdVM by viewModels(
        factoryProducer = {
            requireParentFragment() as ViewModelProvider.Factory
        }
    )

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            vm.handleAction(ScreenThirdVM.Action.OnBackPressed)
        }

    }

    private val item: CharSequence
        get() = requireArguments().getCharSequence("string") as CharSequence

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.text = item
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(ScreenThirdVM.Action.OnButtonPressed)
    }

}

class ScreenThirdVM(
    private val coordinator: Coordinator
) : ViewModel() {

    fun handleAction(action: Action) {
        when (action) {
            Action.OnBackPressed -> {
                coordinator.handleCommand(Coordinator.Command.OpenSecondStep)
            }
            Action.OnButtonPressed -> {
                coordinator.handleCommand(Coordinator.Command.Finish)
            }
        }
    }

    sealed class Action {
        object OnBackPressed : Action()
        object OnButtonPressed : Action()
    }

}
