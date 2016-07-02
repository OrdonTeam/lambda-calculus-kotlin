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
        assertFunctionRepresent(1, PAIR.call(ONE).call(ZERO).call(HEAD))
        assertFunctionRepresent(1, PAIR.call(ZERO).call(ONE).call(TAIL))
    }

    @Test
    fun shouldRepresentList_0_1_2() {
        val LIST = PAIR.call(ONE).call(PAIR.call(ZERO).call(TRUE.call(TWO)))

        assertFunctionRepresent(1, LIST.call(HEAD))
        assertFunctionRepresent(0, LIST.call(TAIL).call(HEAD))
        assertFunctionRepresent(2, LIST.call(TAIL).call(TAIL).call(HEAD))
    }

    @Test
    fun shouldAppendToList() {
        assertFunctionRepresent(2, APPEND_LIST.call(EMPTY_LIST).call(TWO).call(HEAD))
        assertEquals(EMPTY_LIST, APPEND_LIST.call(EMPTY_LIST).call(TWO).call(TAIL))
    }

//    @Test
//    fun shouldCalculateListSize() {
//        val SIZE = F { list ->
//            val SIZE_TO_BUILD = F { self -> F { list -> F { size -> IF.call(IS_EMPTY_LIST.call(list)).call(IDENTITY).call(F { size -> self.call(self).call(list.call(TAIL)).call(NEXT.call(size)) }).call(size) } } }
//            val SIZE_INTERNAL = RECURSION_BUILDER.call(SIZE_TO_BUILD)
//            SIZE_INTERNAL.call(list).call(ZERO)
//        }
//        assertFunctionRepresent(0, SIZE.call(EMPTY_LIST))
//        assertFunctionRepresent(1, SIZE.call(APPEND_LIST.call(EMPTY_LIST).call(ONE)))
//        assertFunctionRepresent(2, SIZE.call(APPEND_LIST.call(APPEND_LIST.call(EMPTY_LIST).call(ONE)).call(ONE)))
//    }
}