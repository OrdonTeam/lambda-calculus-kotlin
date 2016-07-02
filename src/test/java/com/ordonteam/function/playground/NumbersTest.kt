package com.ordonteam.function.playground

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class NumbersTest {

    @Test
    fun shouldSubtract() {
        assertEquals(6, SUB.call(9.asFunction()).call(3.asFunction()).asNumber())
    }

    @Test
    fun shouldChackIfLEQ() {
        assertTrue(LEQ.call(2.asFunction()).call(3.asFunction()).asBoolean())
        assertTrue(LEQ.call(3.asFunction()).call(3.asFunction()).asBoolean())
        assertFalse(LEQ.call(4.asFunction()).call(3.asFunction()).asBoolean())
    }

    @Test
    fun shouldCheckIfEqual() {
        assertFalse(EQ.call(2.asFunction()).call(3.asFunction()).asBoolean())
        assertTrue(EQ.call(3.asFunction()).call(3.asFunction()).asBoolean())
        assertFalse(EQ.call(4.asFunction()).call(3.asFunction()).asBoolean())
    }
}