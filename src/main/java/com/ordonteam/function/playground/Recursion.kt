package com.ordonteam.function.playground

val RECURSION_BUILDER = F { f -> F { a -> f.call(f).call(a) } }
