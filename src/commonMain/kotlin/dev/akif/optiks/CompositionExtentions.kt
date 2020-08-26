package dev.akif.optiks

infix fun <A, B, C> ((A) -> B).compose(f: (C) -> A): (C) -> B = { c: C -> this(f(c)) }

infix fun <A, B, C> ((A) -> B).andThen(f: (B) -> C): (A) -> C = { a: A -> f(this(a)) }
