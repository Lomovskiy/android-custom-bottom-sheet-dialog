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

class UuidPagedBsdCoordinator(
    private val navigator: Navigator
) : Coordinator {

    override fun start() {
        navigator.executeCommand(Replace(Pages.First))
    }

    override fun onNumberSelected() {
        navigator.executeCommand(Forward(Pages.Second))
    }

    override fun onUuidSelected() {
        navigator.executeCommand(Forward(Pages.Third))
    }

    override fun onBackToSelectUuid() {
        navigator.executeCommand(Back)
    }


    override fun onBackToSelectNumber() {
        navigator.executeCommand(Back)
    }

    override fun finish() {
        navigator.executeCommands(arrayOf(BackToRoot, Back))
    }

}
