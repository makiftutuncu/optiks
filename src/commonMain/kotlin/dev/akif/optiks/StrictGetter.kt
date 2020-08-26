package dev.akif.optiks

interface StrictGetter<Source, Value: Any> {
    operator fun get(source: Source): Value

    operator fun <Value2: Any> plus(that: StrictGetter<Value, Value2>): StrictGetter<Source, Value2> =
        Optiks.Strict.getter { source: Source ->
            that[this[source]]
        }
}
