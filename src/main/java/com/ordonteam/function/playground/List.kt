package com.ordonteam.function.playground

val PAIR_CREATOR = F { a -> F { b -> F { x -> x.call(a).call(b) } } }
val APPEND_LIST = F { list -> F { element -> PAIR_CREATOR.call(element).call(list) } }

val NAN = F { x -> TRUE }
val IS_NAN = F { p -> p.call(FALSE) }

val EMPTY_LIST = object : F {
    override fun call(x: F) = this
}
