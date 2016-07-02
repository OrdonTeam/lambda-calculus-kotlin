package com.ordonteam.function.playground

val ZERO = F { f -> F { x -> x } }

val ONE = F { f -> F { x -> f.call(x) } }

val NEXT = F { n -> F { f -> F { x -> n.call(f).call(f.call(x)) } } }

val TWO = F { f -> F { x -> f.call(f.call(x)) } }

val PREVIOUS = F { n -> F { f -> F { x -> n.call(F { g -> F { h -> h.call(g.call(f)) } }).call(F { u -> x }).call(F { u -> u }) } } }

val ADD = F { a -> F { b -> F { f -> F { x -> b.call(f).call(a.call(f).call(x)) } } } }

val MULTIPLY = F { a -> F { b -> F { f -> F { x -> b.call(F { xx -> a.call(f).call(xx) }).call(x) } } } }

val IS_ZERO = F { n -> n.call(F { x -> FALSE }).call(TRUE) }

val SUB = F { a -> F { b -> F { f -> F { x -> b.call(PREVIOUS).call(a).call(f).call(x) } } } }
