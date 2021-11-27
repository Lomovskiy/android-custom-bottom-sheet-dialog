package com.lomovskiy.pagedbsd.navigation

interface NavigationCommand

sealed class PagedBsdNavigationCommand : NavigationCommand {

    class ForwardTo(val page: Page) : PagedBsdNavigationCommand()
    class ReplaceOn(val page: Page) : PagedBsdNavigationCommand()
    object Back : PagedBsdNavigationCommand()
    class BackTo(val page: Page?) : PagedBsdNavigationCommand()

}
