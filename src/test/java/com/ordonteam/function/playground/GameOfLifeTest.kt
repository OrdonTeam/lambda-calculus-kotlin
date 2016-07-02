package com.ordonteam.function.playground

import org.junit.Test

class GameOfLifeTest {

    val GAME_OF_LIFT_TICK = F { x -> F { y -> FALSE } }

    @Test
    fun shouldReturnEmptyWorld() {
        val world = F { x -> F { y -> FALSE } }
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
    }

    @Test
    fun singleCellShouldDie() {
        val world = F { x -> F { y -> (x.isNumber(5) && y.isNumber(5)).asFunction() } }
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
    }

    private fun F.assertCellDead(x: Int, y: Int) {
        assertBehaveLikeFalse(this.call(x.asFunction()).call(y.asFunction()))
    }
}
