package com.lomovskiy.pagedbsd

import com.lomovskiy.pagedbsd.navigation.Page

interface NavigationCommand

class Forward(val page: Page) : NavigationCommand
class Replace(val page: Page) : NavigationCommand
object Back : NavigationCommand
object BackToRoot : NavigationCommand
