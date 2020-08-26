package dev.akif.optiks

interface Setter<Source, Value: Any?> {
    fun modify(modifier: (Value) -> Value?): (Source) -> Source

    fun set(newValue: Value?): (Source) -> Source = modify { newValue }

    operator fun <Value2: Any?> plus(that: Setter<Value, Value2>): Setter<Source, Value2> =
        Optiks.setter { modifier: (Value2) -> Value2? ->
            (that::modify andThen this::modify).invoke(modifier)
        }
}
