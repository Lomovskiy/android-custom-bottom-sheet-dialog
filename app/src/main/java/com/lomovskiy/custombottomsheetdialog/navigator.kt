package com.lomovskiy.custombottomsheetdialog

enum class State {
    PAGE_ONE,
    PAGE_TWO,
    PAGE_THREE,
    CLOSED
}

interface Navigator {
    fun handleState(state: State)
}

interface
