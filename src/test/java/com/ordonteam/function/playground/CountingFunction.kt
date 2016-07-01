package com.ordonteam.function.playground

import org.junit.Assert.assertEquals

data class CountingFunction(val count: Int) : F {

    override fun call(f: F?) = CountingFunction(count + 1)
}

val callParameterWithAnyFunction = F { x -> x.call(anyFunction) }

val anyFunction = F { x -> x }

fun assertFunctionRepresent(value: Int, number: F) {
    assertEquals(CountingFunction(value), number.call(callParameterWithAnyFunction).call(CountingFunction(0)))
}

fun assertBehaveLikeTrue(hopefullyTrue: F) {
    val expected = F { x -> x }
    assertEquals("It is not true", expected, hopefullyTrue.call(expected).call({ x -> expected }))
}

fun assertBehaveLikeFalse(hopefullyTrue: F, message: String = "It is not false") {
    val expected = F { x -> x }
    assertEquals(message, expected, hopefullyTrue.call({ x -> expected }).call(expected))
}