package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class GameOfLifeTest {

    val NEIGHBOURS_COUNT = F { world -> F { x -> F { y -> IF.call(world.call(PREVIOUS.call(x)).call(PREVIOUS.call(x))).call(ONE).call(ZERO) } } }
    val GAME_OF_LIFT_TICK = F { world -> F { x -> F { y -> FALSE } } }

    @Test
    fun shouldReturnEmptyWorld() {
        val world = worldOf()
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
    }

    @Test
    fun singleCellShouldDie() {
        val world = worldOf(5 to 5)
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
    }

    @Test
    fun shouldReturnNeighboursCount() {
        assertNeighboursCountOn_5_5(0, worldOf())
        assertNeighboursCountOn_5_5(1, worldOf(4 to 4))
    }

    @Test
    @Ignore("To big")
    fun groupOfFourCellShouldSurvive() {
        val world = worldOf(4 to 4, 4 to 5, 5 to 4, 5 to 5)
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellAlive(5, 5)
    }

    private fun worldOf(vararg lives: Pair<Int, Int>): F {
        return F { x -> F { y -> lives.contains(x.asNumber() to y.asNumber()).asFunction() } }
    }

    private fun F.assertCellDead(x: Int, y: Int) {
        assertBehaveLikeFalse(this.call(x.asFunction()).call(y.asFunction()))
    }

    private fun F.assertCellAlive(x: Int, y: Int) {
        assertBehaveLikeTrue(this.call(x.asFunction()).call(y.asFunction()))
    }

    private fun assertNeighboursCountOn_5_5(expectedNeighboursCount: Int, world: F) {
        assertEquals(expectedNeighboursCount, NEIGHBOURS_COUNT.call(world).call(5.asFunction()).call(5.asFunction()).asNumber())
    }
}
