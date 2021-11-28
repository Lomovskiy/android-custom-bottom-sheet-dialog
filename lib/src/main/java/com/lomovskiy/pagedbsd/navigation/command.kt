package com.lomovskiy.pagedbsd.navigation

interface NavigationCommand

class Forward(val page: Page) : NavigationCommand
class Replace(val page: Page) : NavigationCommand
object Back : NavigationCommand
object BackToRoot : NavigationCommand
