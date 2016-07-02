package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

data class Count(val count: Int) : F {
    override fun call(f: F) = throw RuntimeException()
}

class Counter : F {
    override fun call(count: F): F {
        return if (count is Count) {
            Count(count.count + 1)
        } else {
            throw RuntimeException()
        }
    }
}

fun assertFunctionRepresent(value: Int, number: F) {
    assertTrue(number.isNumber(value))
}

fun assertBehaveLikeTrue(hopefullyTrue: F) {
    val expected = F { x -> x }
    assertEquals("It is not true", expected, hopefullyTrue.call(expected).call({ x -> expected }))
}

fun assertBehaveLikeFalse(hopefullyTrue: F, message: String = "It is not false") {
    val expected = F { x -> x }
    assertEquals(message, expected, hopefullyTrue.call({ x -> expected }).call(expected))
}