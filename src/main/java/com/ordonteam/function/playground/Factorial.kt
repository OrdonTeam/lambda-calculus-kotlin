package com.ordonteam.function.playground

val FACTORIAL = F { a ->
    val FACTORIAL_TO_BUILD = F { self -> F { a -> F { acc -> IF.call(IS_ZERO.call(a)).call(IDENTITY).call(F { acc2 -> self.call(self).call(PREVIOUS.call(a)).call(MULTIPLY.call(a).call(acc2)) }).call(acc) } } }
    val FACTORIAL_INTERNAL = RECURSION_BUILDER.call(FACTORIAL_TO_BUILD)
    FACTORIAL_INTERNAL.call(a).call(ONE)
}