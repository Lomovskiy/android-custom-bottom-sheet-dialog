package com.lomovskiy.custombottomsheetdialog

sealed class CustomBottomSheetNavigationMessage : NavigationMessage {

    object Back : CustomBottomSheetNavigationMessage()
    object OpenScreenFirst : CustomBottomSheetNavigationMessage()
    object OpenScreenSecond : CustomBottomSheetNavigationMessage()
    object OpenScreenThird : CustomBottomSheetNavigationMessage()

}
