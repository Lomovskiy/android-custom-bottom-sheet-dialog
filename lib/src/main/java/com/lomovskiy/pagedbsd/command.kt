package com.lomovskiy.pagedbsd

interface NavigationCommand

class Forward(val route: Route) : NavigationCommand
class Replace(val route: Route) : NavigationCommand
class BackTo(val route: Route?) : NavigationCommand
object Back : NavigationCommand
