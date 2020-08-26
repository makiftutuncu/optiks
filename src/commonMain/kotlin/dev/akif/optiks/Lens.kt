package dev.akif.optiks

interface Lens<Source, Value: Any?>: Getter<Source, Value>, Setter<Source, Value> {
    operator fun <Value2: Any?> plus(that: Lens<Value, Value2>): Lens<Source, Value2> =
        Optiks.lens(
            { source: Source -> this[source]?.let { that[it] } },
            { modifier: (Value2) -> Value2? -> (that::modify andThen this::modify).invoke(modifier) }
        )
}
