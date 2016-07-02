package com.ordonteam.function.playground

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
}

fun Int.asFunction() = (1..this).fold(ZERO, { value, i -> NEXT.call(value) })

fun Boolean.asFunction() = if (this) TRUE else FALSE

fun F.isNumber(value: Int) = (this.call(Counter()).call(Count(0)) as Count).count == value