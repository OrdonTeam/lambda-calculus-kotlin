package com.ordonteam.function.playground

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PlaygroundTest {

    val identity = F { x -> x }
    val other = F { x -> x }
    val TRUE = F { x -> F { y -> x } }
    val FALSE = F { x -> F { y -> y } }
    val NOT = F { x -> x.call(FALSE).call(TRUE) }
    val IF = F { x -> F { y -> F { z -> x.call(y).call(z) } } }
    val OR = F { x -> F { y -> x.call(TRUE).call(y) } }
    val AND = F { x -> F { y -> x.call(y).call(FALSE) } }

    val ZERO = F { x -> x }
    val ONE = F { x -> x.call(x) }
    val NEXT = F { x -> F { y -> x.call(y.call(y)) } }
    val PREVIOUS = F { number -> F { counter -> number.call(F { ignoreInvocation -> counter }) } }
    val TWO = NEXT.call(ONE)
    val ZERO_TO_ONE_PAIR = F { x -> x.call(ZERO).call(ONE) }
    val PAIR_CREATOR = F { a -> F { b -> F { x -> x.call(a).call(b) } } }
    val EMPTY_LIST = object : F {
        override fun call(x: F) = this
    }
    val APPEND_LIST = F { list -> F { element -> PAIR_CREATOR.call(element).call(list) } }

    @Test
    fun shouldReturnPassedParam() {
        assertEquals(identity, identity.call(identity))
    }

    @Test
    fun shouldDifferentFunctionExists() {
        assertNotEquals(identity, other)
    }

    @Test
    fun shouldReturnFirstParam() {
        assertEquals(identity, TRUE.call(identity).call(other))
    }

    @Test
    fun shouldReturnSecondParam() {
        assertEquals(identity, FALSE.call(other).call(identity))
    }

    @Test
    fun shouldBehaveLikeNot() {
        assertEquals(FALSE, NOT.call(TRUE))
        assertEquals(TRUE, NOT.call(FALSE))
    }

    @Test
    fun shouldBehaveLikeIf() {
        assertEquals(identity, IF.call(TRUE).call(identity).call(other))
        assertEquals(identity, IF.call(FALSE).call(other).call(identity))
    }

    @Test
    fun shouldBehaveLikeOr() {
        assertEquals(TRUE, OR.call(TRUE).call(TRUE))
        assertEquals(TRUE, OR.call(TRUE).call(FALSE))
        assertEquals(TRUE, OR.call(FALSE).call(TRUE))
        assertEquals(FALSE, OR.call(FALSE).call(FALSE))
    }

    @Test
    fun shouldBehaveLikeAnd() {
        assertEquals(TRUE, AND.call(TRUE).call(TRUE))
        assertEquals(FALSE, AND.call(TRUE).call(FALSE))
        assertEquals(FALSE, AND.call(FALSE).call(TRUE))
        assertEquals(FALSE, AND.call(FALSE).call(FALSE))
    }

    @Test
    fun shouldRepresent0() {
        assertFunctionRepresent(0, ZERO)
    }

    @Test
    fun shouldRepresent1() {
        assertFunctionRepresent(1, ONE)
    }

    @Test
    fun shouldRepresentNextValue() {
        assertFunctionRepresent(1, NEXT.call(ZERO))
        assertFunctionRepresent(2, NEXT.call(NEXT.call(ZERO)))
    }

    @Test
    fun shouldRepresentZeroToOneTuple() {
        assertFunctionRepresent(0, ZERO_TO_ONE_PAIR.call(TRUE))
        assertFunctionRepresent(1, ZERO_TO_ONE_PAIR.call(FALSE))
    }

    @Test
    fun shouldRepresentRequestedPair() {
        assertFunctionRepresent(1, PAIR_CREATOR.call(ONE).call(ZERO).call(TRUE))
        assertFunctionRepresent(1, PAIR_CREATOR.call(ZERO).call(ONE).call(FALSE))
    }

    @Test
    fun shouldRepresentList_0_1_2() {
        val LIST = PAIR_CREATOR.call(ONE).call(PAIR_CREATOR.call(ZERO).call(TRUE.call(TWO)))

        assertFunctionRepresent(1, LIST.call(TRUE))
        assertFunctionRepresent(0, LIST.call(FALSE).call(TRUE))
        assertFunctionRepresent(2, LIST.call(FALSE).call(FALSE).call(TRUE))
    }

    @Test
    fun shouldRepresentEmptyList() {
        assertEquals(EMPTY_LIST, EMPTY_LIST.call(TRUE))
        assertEquals(EMPTY_LIST, EMPTY_LIST.call(FALSE))
    }

    @Test
    fun shouldAppendToList() {
        assertFunctionRepresent(2, APPEND_LIST.call(EMPTY_LIST).call(TWO).call(TRUE))
        assertEquals(EMPTY_LIST, APPEND_LIST.call(EMPTY_LIST).call(TWO).call(FALSE))
    }

    @Test
    fun shouldRepresentPreviousNumber() {
        assertSameNumber(ZERO, PREVIOUS.call(ONE))
        assertSameNumber(ONE, PREVIOUS.call(TWO))
        assertSameNumber(ZERO, PREVIOUS.call(PREVIOUS.call(TWO)))
    }

}
