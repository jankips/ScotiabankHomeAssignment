package com.example.scotiabankhomeassignment.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LiveData<T>.getOrAwaitValue(): T? {
    var data: T? = null
    var initialized = false

    val observer = Observer<T> { value ->
        data = value
        initialized = true
    }

    observeForever(observer)

    val currentState = this.value

    if (initialized && currentState != null) {
        data = currentState
    }

    removeObserver(observer)

    return data
}