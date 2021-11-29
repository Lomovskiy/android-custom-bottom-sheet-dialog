package com.lomovskiy.pagedbsd.sample

import com.lomovskiy.pagedbsd.*

interface Coordinator {

    fun start()
    fun onNumberSelected()
    fun onUuidSelected()
    fun onBackToSelectUuid()
    fun onBackToSelectNumber()
    fun finish()

}

class UuidPagedBottomSheetCoordinator(
    private val navigator: Navigator
) : Coordinator {

    override fun start() {
        navigator.executeCommand(Replace(Routes.First))
    }

    override fun onNumberSelected() {
        navigator.executeCommand(Forward(Routes.Second))
    }

    override fun onUuidSelected() {
        navigator.executeCommand(Forward(Routes.Third))
    }

    override fun onBackToSelectUuid() {
        navigator.executeCommand(Back)
    }


    override fun onBackToSelectNumber() {
        navigator.executeCommand(BackTo(Routes.First))
    }

    override fun finish() {
        navigator.executeCommands(arrayOf(BackTo(null), Back))
    }

}
