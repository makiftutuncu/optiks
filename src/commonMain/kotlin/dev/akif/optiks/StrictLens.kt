package dev.akif.optiks

interface StrictLens<Source, Value: Any>: StrictGetter<Source, Value>, StrictSetter<Source, Value> {
    operator fun <Value2: Any> plus(that: StrictLens<Value, Value2>): StrictLens<Source, Value2> =
        Optiks.Strict.lens(
            { source: Source -> that[this[source]] },
            { modifier: (Value2) -> Value2 -> (that::modify andThen this::modify).invoke(modifier) }
        )
}
