package com.ordonteam.function.playground

val NEIGHBOURS_COUNT = F { world ->
    F { x ->
        F { y ->
            val topLeft = IF.call(world.call(PREVIOUS.call(x)).call(PREVIOUS.call(y))).call(ONE).call(ZERO)
            val topMiddle = IF.call(world.call(PREVIOUS.call(x)).call(y)).call(ONE).call(ZERO)
            val topRight = IF.call(world.call(PREVIOUS.call(x)).call(NEXT.call(y))).call(ONE).call(ZERO)
            val middleLeft = IF.call(world.call(x).call(PREVIOUS.call(y))).call(ONE).call(ZERO)
            val middleRight = IF.call(world.call(x).call(NEXT.call(y))).call(ONE).call(ZERO)
            val bottomLeft = IF.call(world.call(NEXT.call(x)).call(PREVIOUS.call(y))).call(ONE).call(ZERO)
            val bottomMiddle = IF.call(world.call(NEXT.call(x)).call(y)).call(ONE).call(ZERO)
            val bottomRight = IF.call(world.call(NEXT.call(x)).call(NEXT.call(y))).call(ONE).call(ZERO)
            ADD.call(ADD.call(ADD.call(ADD.call(ADD.call(ADD.call(ADD.call(topLeft).call(topMiddle)).call(topRight)).call(middleLeft)).call(middleRight)).call(bottomLeft)).call(bottomMiddle)).call(bottomRight)
        }
    }
}
val GAME_OF_LIFT_TICK = F { world ->
    F { x ->
        F { y ->
            val livingNeighbours = NEIGHBOURS_COUNT.call(world).call(x).call(y)
            val hasThreeNeighbours = EQ.call(livingNeighbours).call(THREE)
            val hasTwoNeighbours = EQ.call(livingNeighbours).call(TWO)
            val isAlive = world.call(x).call(y)
            OR.call(hasThreeNeighbours).call(AND.call(hasTwoNeighbours).call(isAlive))
        }
    }
}