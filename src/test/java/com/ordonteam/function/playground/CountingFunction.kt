package com.ordonteam.function.playground

import org.junit.Assert

data class CountingFunction(val count: Int) : F {

    override fun call(f: F?) = CountingFunction(count + 1)
}

fun assertSameNumber(expectedNumber: F, actualNumber: F) {
    Assert.assertEquals(expectedNumber.call(CountingFunction(0)), actualNumber.call(CountingFunction(0)))
}

fun assertFunctionRepresent(value: Int, number: F) {
    Assert.assertEquals(CountingFunction(value), number.call(CountingFunction(0)))
}