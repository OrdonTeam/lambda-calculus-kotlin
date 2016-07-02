package com.ordonteam.function.playground

val TRUE = F { x -> F { y -> x } }

val FALSE = F { x -> F { y -> y } }

val NOT = F { x -> x.call(FALSE).call(TRUE) }

val IF = F { x -> F { y -> F { z -> x.call(y).call(z) } } }

val OR = F { x -> F { y -> x.call(TRUE).call(y) } }

val AND = F { x -> F { y -> x.call(y).call(FALSE) } }