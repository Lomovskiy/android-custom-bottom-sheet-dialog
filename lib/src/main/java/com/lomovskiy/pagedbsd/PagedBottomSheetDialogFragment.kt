package com.lomovskiy.pagedbsd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PagedBsd : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    private val navigator: Navigator<SelectUUIDNavCommand> =
        NavigatorImpl(this)

    private val depsProvider: DepsProvider by lazy(LazyThreadSafetyMode.NONE) {
        return@lazy getDepsProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = ScreenFactory()
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {

            override fun onBackPressed() {
                requireActivity().onBackPressed()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.handleCommand(SelectUUIDNavCommand.OpenFirstStep)
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(): PagedBottomSheetDialogFragment {
            return PagedBottomSheetDialogFragment()
        }

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            PagedBottomSheetDialogFragmentVM::class.java -> {
                return PagedBottomSheetDialogFragmentVM(
                    navigator,
                    depsProvider.provideDep1(),
                    depsProvider.provideDep2()
                ) as T
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    data class State(
        val selectedString: CharSequence?,
        val selectedNumber: Int?
    ) {

        companion object {

            fun empty(): State {
                return State(
                    selectedString = null,
                    selectedNumber = null
                )
            }

        }

    }



}