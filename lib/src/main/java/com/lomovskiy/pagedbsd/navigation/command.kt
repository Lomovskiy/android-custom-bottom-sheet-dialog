package com.lomovskiy.pagedbsd.navigation

interface NavigationCommand

class ForwardTo(val page: Page) : NavigationCommand
class ReplaceOn(val page: Page) : NavigationCommand
object Back : NavigationCommand
class BackTo(val page: Page?) : NavigationCommand
