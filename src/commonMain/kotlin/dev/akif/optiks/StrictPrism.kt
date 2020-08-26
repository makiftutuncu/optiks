package dev.akif.optiks

interface StrictPrism<Source, Value: Any> {
    operator fun get(source: Source): Value

    fun reverseGet(value: Value): Source

    fun modify(modifier: (Value) -> Value): (Source) -> Value = { source: Source -> modifier(get(source)) }

    operator fun <Value2: Any> plus(that: StrictPrism<Value, Value2>): StrictPrism<Source, Value2> =
        Optiks.Strict.prism(
            { source: Source -> that[this[source]] },
            { value2: Value2 -> (that::reverseGet andThen this::reverseGet).invoke(value2) }
        )
}
