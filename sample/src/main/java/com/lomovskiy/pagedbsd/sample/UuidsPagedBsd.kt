package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import com.lomovskiy.pagedbsd.PagedBsd

class UuidsPagedBsd : PagedBsd<UuidsPagedBsdVM, UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>() {

    override val viewModel: UuidsPagedBsdVM by viewModels()
    override val pageFactory: FragmentFactory = PageFactory()

}
