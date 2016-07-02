package com.ordonteam.function.playground

import org.junit.Test

class GameOfLifeTest {

    val GAME_OF_LIFT_TICK = IDENTITY

    @Test
    fun shouldReturnEmptyWorld() {
        val world = F { x -> F { y -> FALSE } }
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
    }

    private fun F.assertCellDead(x: Int, y: Int) {
        assertBehaveLikeFalse(this.call(x.asFunction()).call(y.asFunction()))
    }
}
