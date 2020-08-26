package dev.akif.optiks

interface Getter<Source, Value: Any?> {
    operator fun get(source: Source): Value?

    operator fun <Value2: Any?> plus(that: Getter<Value, Value2>): Getter<Source, Value2> =
        Optiks.getter { source: Source ->
            this[source]?.let { that[it] }
        }
}
