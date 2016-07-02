package com.ordonteam.function.playground

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
}

fun Int.asFunction() = (1..this).fold(ZERO, { value, i -> NEXT.call(value) })

fun Boolean.asFunction() = if (this) TRUE else FALSE