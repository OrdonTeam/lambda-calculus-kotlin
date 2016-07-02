package com.ordonteam.function.playground

val EMPTY_LIST = F { x -> TRUE }
val IS_EMPTY_LIST = F { p -> p.call(FALSE) }
val PAIR = F { a -> F { b -> F { x -> x.call(a).call(b) } } }
val APPEND_LIST = F { list -> F { element -> PAIR.call(element).call(list) } }

