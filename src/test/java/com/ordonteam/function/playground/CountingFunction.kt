package com.ordonteam.function.playground

import org.junit.Assert

data class CountingFunction(val count: Int) : F {

    override fun call(f: F?) = CountingFunction(count + 1)
}

fun assertFunctionRepresent(value: Int, function: F) {
    Assert.assertEquals(CountingFunction(value), function.call(CountingFunction(0)))
}