package dev.akif.optiks

interface StrictSetter<Source, Value: Any> {
    fun modify(modifier: (Value) -> Value): (Source) -> Source

    fun set(newValue: Value): (Source) -> Source = modify { newValue }

    operator fun <Value2: Any> plus(that: StrictSetter<Value, Value2>): StrictSetter<Source, Value2> =
        Optiks.Strict.setter { modifier: (Value2) -> Value2 ->
            (that::modify andThen this::modify).invoke(modifier)
        }
}
