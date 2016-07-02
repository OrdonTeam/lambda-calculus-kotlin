package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PlaygroundTest {

    val other = F { x -> x }

    @Test
    fun shouldRepresentNotANumberAndNotATuple() {
        assertBehaveLikeTrue(IS_NAN.call(NAN))
        assertBehaveLikeFalse(IS_NAN.call(ONE))
        assertBehaveLikeFalse(IS_NAN.call(PAIR_CREATOR.call(ONE).call(ONE)))
    }

    @Test
    fun shouldCalculateFactorial() {
        assertFunctionRepresent(1, FACTORIAL.call(ZERO))
        assertFunctionRepresent(1, FACTORIAL.call(ONE))
        assertFunctionRepresent(2, FACTORIAL.call(TWO))
        assertFunctionRepresent(6, FACTORIAL.call(NEXT.call(TWO)))
        assertFunctionRepresent(720, FACTORIAL.call(NEXT.call(NEXT.call(NEXT.call(NEXT.call(TWO))))))
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
        assertEquals(IDENTITY, IDENTITY.call(IDENTITY))
    }

    @Test
    fun shouldDifferentFunctionExists() {
        assertNotEquals(IDENTITY, other)
    }

    @Test
    fun shouldReturnFirstParam() {
        assertEquals(IDENTITY, TRUE.call(IDENTITY).call(other))
    }

    @Test
    fun shouldReturnSecondParam() {
        assertEquals(IDENTITY, FALSE.call(other).call(IDENTITY))
    }

    @Test
    fun shouldBehaveLikeNot() {
        assertEquals(FALSE, NOT.call(TRUE))
        assertEquals(TRUE, NOT.call(FALSE))
    }

    @Test
    fun shouldBehaveLikeIf() {
        assertEquals(IDENTITY, IF.call(TRUE).call(IDENTITY).call(other))
        assertEquals(IDENTITY, IF.call(FALSE).call(other).call(IDENTITY))
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
