package com.lomovskiy.custombottomsheetdialog

interface Base {
    fun print()
}

class BaseImpl(private val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

class Derived(val b: Base) : Base by b {
    fun print2() {

    }
}