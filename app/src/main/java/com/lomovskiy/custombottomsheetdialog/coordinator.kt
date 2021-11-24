package com.lomovskiy.custombottomsheetdialog

interface Coordinator {
    fun start()
    fun forward()
    fun back()
    fun finish()
}

class CoordinatorImpl(
    private val navigator: Navigator
) : Coordinator {

    private var state: State = State.PAGE_ONE

    override fun start() {
        state = State.PAGE_ONE
        navigator.handleState(State.PAGE_ONE)
    }

    override fun forward() {
        when (state) {
            State.PAGE_ONE -> {
                state = State.PAGE_TWO
                navigator.handleState(state)
            }
            State.PAGE_TWO -> {
                state = State.PAGE_THREE
                navigator.handleState(state)
            }
            State.PAGE_THREE -> {}
            State.CLOSED -> {}
        }
    }

    override fun back() {
        when (state) {
            State.PAGE_ONE -> {
                state = State.CLOSED
                navigator.handleState(state)
            }
            State.PAGE_TWO -> {
                state = State.PAGE_ONE
                navigator.handleState(state)
            }
            State.PAGE_THREE -> {
                state = State.PAGE_TWO
                navigator.handleState(state)
            }
            State.CLOSED -> {}
        }
    }

    override fun finish() {
        state = State.CLOSED
        navigator.handleState(state)
    }

}