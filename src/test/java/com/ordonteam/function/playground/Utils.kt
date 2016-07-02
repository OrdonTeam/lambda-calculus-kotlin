package com.ordonteam.function.playground

import org.junit.Assert.*
import org.junit.Test

class UtilsTest {
    @Test
    fun shouldReturnFunctionalRepresentationOfANumber() {
        assertFunctionRepresent(6, 6.asFunction())
        assertFunctionRepresent(100, 100.asFunction())
    }

    @Test
    fun shouldReturnFunctionalRepresentationOfBoolean() {
        assertBehaveLikeTrue(true.asFunction())
        assertBehaveLikeFalse(false.asFunction())
    }

    @Test
    fun shouldChcekIfFunctionRepresentNumber() {
        assertTrue(6.asFunction().isNumber(6))
        assertFalse(6.asFunction().isNumber(7))
    }

    @Test
    fun shouldConvertToNumber() {
        assertEquals(6, 6.asFunction().asNumber())
        assertEquals(9, 9.asFunction().asNumber())
    }
}

fun Int.asFunction() = (1..this).fold(ZERO, { value, i -> NEXT.call(value) })

fun Boolean.asFunction() = if (this) TRUE else FALSE

fun F.isNumber(value: Int) = asNumber() == value

fun F.asNumber() = (this.call(Counter()).call(Count(0)) as Count).count