package com.ordonteam.function.playground

val TRUE = F { x -> F { y -> x } }

val FALSE = F { x -> F { y -> y } }

val NOT = F { x -> x.call(FALSE).call(TRUE) }

val IF = F { x -> F { y -> F { z -> x.call(y).call(z) } } }

val OR = F { x -> F { y -> x.call(TRUE).call(y) } }

val AND = F { x -> F { y -> x.call(y).call(FALSE) } }

val ZERO = F { f -> F { x -> x } }

val ONE = F { f -> F { x -> f.call(x) } }

val NEXT = F { n -> F { f -> F { x -> n.call(f).call(f.call(x)) } } }

val TWO = NEXT.call(ONE)

val THREE = NEXT.call(TWO)

val PREVIOUS = F { n -> F { f -> F { x -> n.call(F { g -> F { h -> h.call(g.call(f)) } }).call(F { u -> x }).call(F { u -> u }) } } }

val ADD = F { a -> F { b -> F { f -> F { x -> b.call(f).call(a.call(f).call(x)) } } } }

val IS_ZERO = F { n -> n.call(F { x -> FALSE }).call(TRUE) }

val SUB = F { a -> F { b -> F { f -> F { x -> b.call(PREVIOUS).call(a).call(f).call(x) } } } }

val LEQ = F { a -> F { b -> IS_ZERO.call(SUB.call(a).call(b)) } }

val EQ = F { a -> F { b -> AND.call(LEQ.call(a).call(b)).call(LEQ.call(b).call(a)) } }

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