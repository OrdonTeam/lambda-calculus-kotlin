package com.ordonteam.function.playground

import org.junit.Assert.assertEquals

data class CountingFunction(val count: Int) : F {

    override fun call(f: F?) = CountingFunction(count + 1)
}

fun assertSameNumber(expectedNumber: F, actualNumber: F) {
    assertEquals(expectedNumber.call(CountingFunction(0)), actualNumber.call(CountingFunction(0)))
}

fun assertFunctionRepresent(value: Int, number: F) {
    assertEquals(CountingFunction(value), number.call(CountingFunction(0)))
}

fun assertBehaveLikeTrue(hopefullyTrue: F) {
    val expected = F { x -> x }
    assertEquals("It is not true", expected, hopefullyTrue.call(expected).call({ x -> expected }))
}

fun assertBehaveLikeFalse(hopefullyTrue: F) {
    val expected = F { x -> x }
    assertEquals("It is not false", expected, hopefullyTrue.call({ x -> expected }).call(expected))
}