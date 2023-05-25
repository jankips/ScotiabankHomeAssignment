package com.example.scotiabankhomeassignment

import androidx.lifecycle.Observer
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import java.util.concurrent.CountDownLatch

class TestObserver<T> : Observer<T> {
    private val observedValues = mutableListOf<T?>()
    private val latch = CountDownLatch(1)

    override fun onChanged(value: T?) {
        observedValues.add(value)
        latch.countDown()
    }

    fun awaitValue(): T? {
        latch.await()
        return observedValues.lastOrNull()
    }

    fun assertValue(expectedValue: T?) {
        val actualValue = awaitValue()
        assertEquals(expectedValue, actualValue)
    }

    fun assertNoValue() {
        assertTrue(observedValues.isEmpty())
    }
}