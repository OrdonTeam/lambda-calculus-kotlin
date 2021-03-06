package com.ordonteam.function.playground

import org.junit.Assert.assertEquals
import org.junit.Test

class GameOfLifeTest {

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
    fun cellShouldNotEmergeWithOnlyTwoFriends() {
        val world = worldOf(4 to 4, 4 to 5)
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
    }

    @Test
    fun shouldReturnNeighboursCount() {
        assertNeighboursCountOn_5_6(0, worldOf())
        assertNeighboursCountOn_5_6(1, worldOf(4 to 5))
        assertNeighboursCountOn_5_6(1, worldOf(4 to 6))
        assertNeighboursCountOn_5_6(1, worldOf(4 to 7))
        assertNeighboursCountOn_5_6(1, worldOf(5 to 5))
        assertNeighboursCountOn_5_6(1, worldOf(5 to 7))
        assertNeighboursCountOn_5_6(1, worldOf(6 to 5))
        assertNeighboursCountOn_5_6(1, worldOf(6 to 6))
        assertNeighboursCountOn_5_6(1, worldOf(6 to 7))
    }

    @Test
    fun groupOfFourCellShouldSurvive() {
        val world = worldOf(4 to 4, 4 to 5, 5 to 4, 5 to 5)
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellAlive(5, 5)
    }

    @Test
    fun onceShouldDieWhenNotEnoughFriends() {
        val world = worldOf(4 to 4, 5 to 5)
        val newWorld = GAME_OF_LIFT_TICK.call(world)
        newWorld.assertCellDead(5, 5)
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

    private fun assertNeighboursCountOn_5_6(expectedNeighboursCount: Int, world: F) {
        assertEquals(expectedNeighboursCount, NEIGHBOURS_COUNT.call(world).call(5.asFunction()).call(6.asFunction()).asNumber())
    }
}
