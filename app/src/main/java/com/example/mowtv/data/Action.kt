package com.example.mowtv.data

class Action(data: Int) {
    private var mAction = 0
    fun getValue(): Int {
        return mAction
    }
    companion object {
        const val DATA_LOADED = 0
        const val DATA_IS_LOADING = 1
    }
}