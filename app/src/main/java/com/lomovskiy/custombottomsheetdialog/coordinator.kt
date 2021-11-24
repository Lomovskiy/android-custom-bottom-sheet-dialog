package com.lomovskiy.custombottomsheetdialog

interface Coordinator {

    fun handleCommand(command: Command)

    sealed class Command {
        object OpenFirstStep : Command()
        object OpenSecondStep : Command()
        object OpenThirdStep : Command()
        object Finish : Command()
    }

}

class CoordinatorImpl(
    private val navigator: Navigator
) : Coordinator {

    override fun handleCommand(command: Coordinator.Command) {
        when (command) {
            Coordinator.Command.Finish -> {
                navigator.handleState(State.Finished)
            }
            Coordinator.Command.OpenFirstStep -> {
                navigator.handleState(State.StepOne)
            }
            Coordinator.Command.OpenSecondStep -> {
                navigator.handleState(State.StepTwo)
            }
            is Coordinator.Command.OpenThirdStep -> {
                navigator.handleState(State.StepThree)
            }
        }
    }


}