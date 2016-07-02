package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Test

class ListTest {
    @Test
    fun shouldRepresentNotANumberAndNotATuple() {
        assertBehaveLikeTrue(IS_NAN.call(NAN))
        assertBehaveLikeFalse(IS_NAN.call(ONE))
        assertBehaveLikeFalse(IS_NAN.call(PAIR_CREATOR.call(ONE).call(ONE)))
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
}