package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class NumbersTest {

    @Test
    fun shouldSubtract() {
        assertEquals(6, SUB.call(9.asFunction()).call(3.asFunction()).asNumber())
    }

    @Test
    @Ignore("To big")
    fun shouldCheckIfEqual() {

    }
}