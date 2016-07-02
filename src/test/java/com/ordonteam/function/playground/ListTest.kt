package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Test

class ListTest {
    @Test
    fun shouldRepresentNotANumberAndNotATuple() {
        assertBehaveLikeTrue(IS_EMPTY_LIST.call(EMPTY_LIST))
        assertBehaveLikeFalse(IS_EMPTY_LIST.call(ONE))
        assertBehaveLikeFalse(IS_EMPTY_LIST.call(PAIR.call(ONE).call(ONE)))
    }

    @Test
    fun shouldRepresentRequestedPair() {
        assertFunctionRepresent(1, PAIR.call(ONE).call(ZERO).call(TRUE))
        assertFunctionRepresent(1, PAIR.call(ZERO).call(ONE).call(FALSE))
    }

    @Test
    fun shouldRepresentList_0_1_2() {
        val LIST = PAIR.call(ONE).call(PAIR.call(ZERO).call(TRUE.call(TWO)))

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