package dev.akif.optiks

interface Prism<Source, Value: Any?> {
    operator fun get(source: Source): Value?

    fun reverseGet(value: Value): Source

    fun modify(modifier: (Value) -> Value?): (Source) -> Value? { source: Source -> get(source)?.let { modifier(it) } }

    operator fun <Value2: Any?> plus(that: Prism<Value, Value2>): Prism<Source, Value2> =
        Optiks.prism(
            { source: Source -> this[source]?.let { that[it] } },
            { value2: Value2 -> (that::reverseGet andThen this::reverseGet).invoke(value2) }
        )
}
