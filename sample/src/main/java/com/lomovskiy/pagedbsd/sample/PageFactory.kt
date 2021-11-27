package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class PageFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            PageFirst::class.java.name -> {
                return PageFirst()
            }
            PageSecond::class.java.name -> {
                return PageSecond()
            }
            PageThird::class.java.name -> {
                return PageThird()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

}