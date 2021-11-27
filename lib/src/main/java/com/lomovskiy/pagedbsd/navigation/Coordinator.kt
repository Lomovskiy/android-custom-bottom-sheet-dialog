package com.lomovskiy.pagedbsd.navigation

interface Coordinator {

    fun navigateTo(page: Page)
    fun replaceOn(page: Page)
    fun backTo(page: Page)
    fun finish()

}

open class PagedBsdCoordinator(
    private val navigator: Navigator
) : Coordinator {

    override fun navigateTo(page: Page) {
        navigator.executeCommands(arrayOf(PagedBsdNavigationCommand.ForwardTo(page)))
    }

    override fun replaceOn(page: Page) {
        navigator.executeCommands(arrayOf(PagedBsdNavigationCommand.ReplaceOn(page)))
    }

    override fun backTo(page: Page) {
        navigator.executeCommands(arrayOf(PagedBsdNavigationCommand.BackTo(page)))
    }

    override fun finish() {
        navigator.executeCommands(arrayOf(
            PagedBsdNavigationCommand.BackTo(null),
            PagedBsdNavigationCommand.Back
        ))
    }

}