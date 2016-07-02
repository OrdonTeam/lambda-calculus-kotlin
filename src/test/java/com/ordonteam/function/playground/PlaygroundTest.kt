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

    val ZERO = F { f -> F { x -> x } }
    val ONE = F { f -> F { x -> f.call(x) } }
    val TWO = F { f -> F { x -> f.call(f.call(x)) } }
    val NEXT = F { n -> F { f -> F { x -> n.call(f).call(f.call(x)) } } }
    val PREVIOUS = F { n -> F { f -> F { x -> n.call(F { g -> F { h -> h.call(g.call(f)) } }).call(F { u -> x }).call(F { u -> u }) } } }
    val ZERO_TO_ONE_PAIR = F { x -> x.call(ZERO).call(ONE) }
    val PAIR_CREATOR = F { a -> F { b -> F { x -> x.call(a).call(b) } } }
    val EMPTY_LIST = object : F {
        override fun call(x: F) = this
    }
    val APPEND_LIST = F { list -> F { element -> PAIR_CREATOR.call(element).call(list) } }
    val IS_ZERO = F { n -> n.call(FALSE).call(TRUE) }
    val ADD = F { a -> F { b -> F { f -> F { x -> b.call(f).call(a.call(f).call(x)) } } } }
    val MULTIPLY = F { a -> F { b -> F { f -> F { x -> b.call(F { xx -> a.call(f).call(xx) }).call(x) } } } }
    val RECURSION_BUILDER = F { f -> F { a -> f.call(f).call(a) } }
    val FACTORIAL = F { a ->
        val FACTORIAL_TO_BUILD = F { self -> F { a -> F { acc -> IF.call(IS_ZERO.call(a)).call(identity).call(F { acc2 -> self.call(self).call(PREVIOUS.call(a)).call(MULTIPLY.call(a).call(acc2)) }).call(acc) } } }
        val FACTORIAL_INTERNAL = RECURSION_BUILDER.call(FACTORIAL_TO_BUILD)
        FACTORIAL_INTERNAL.call(a).call(ONE)
    }

    @Test
    fun shouldCalculateFactorial() {
        assertFunctionRepresent(1, FACTORIAL.call(ZERO))
        assertFunctionRepresent(1, FACTORIAL.call(ONE))
        assertFunctionRepresent(2, FACTORIAL.call(TWO))
        assertFunctionRepresent(6, FACTORIAL.call(NEXT.call(TWO)))
    }

    @Test
    fun shouldMultiply() {
        assertFunctionRepresent(0, MULTIPLY.call(ZERO).call(ZERO))
        assertFunctionRepresent(0, MULTIPLY.call(ZERO).call(ONE))
        assertFunctionRepresent(0, MULTIPLY.call(ONE).call(ZERO))
        assertFunctionRepresent(1, MULTIPLY.call(ONE).call(ONE))
        assertFunctionRepresent(4, MULTIPLY.call(TWO).call(TWO))
    }

    @Test
    fun shouldAddTwoNumbers() {
        assertFunctionRepresent(0, ADD.call(ZERO).call(ZERO))
        assertFunctionRepresent(1, ADD.call(ZERO).call(ONE))
        assertFunctionRepresent(1, ADD.call(ONE).call(ZERO))
        assertFunctionRepresent(5, ADD.call(TWO).call(NEXT.call(TWO)))
    }

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
    fun shouldRepresent2() {
        assertFunctionRepresent(2, TWO)
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
        assertFunctionRepresent(0, PREVIOUS.call(ONE))
        assertFunctionRepresent(1, PREVIOUS.call(TWO))
        assertFunctionRepresent(0, PREVIOUS.call(PREVIOUS.call(TWO)))
    }

    @Test
    fun shouldRepresentIsZero() {
        assertBehaveLikeTrue(IS_ZERO.call(ZERO))
        assertBehaveLikeFalse(IS_ZERO.call(ONE))
        assertBehaveLikeFalse(IS_ZERO.call(TWO))
    }
}
