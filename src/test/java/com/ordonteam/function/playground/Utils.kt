package com.ordonteam.function.playground

import org.junit.Test

class UtilsTest {
    @Test
    fun shouldReturnFunctionalRepresentationOfANumber() {
        assertFunctionRepresent(6, 6.asFunction())
        assertFunctionRepresent(100, 100.asFunction())
    }
}

fun Int.asFunction() = (1..this).fold(ZERO, { value, i -> NEXT.call(value) })