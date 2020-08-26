package dev.akif.optiks

import kotlin.jvm.JvmStatic

object Optiks {
    @JvmStatic fun <Source, Value: Any?> getter(f: (Source) -> Value?): Getter<Source, Value> =
        object : Getter<Source, Value> {
            override operator fun get(source: Source): Value? = f(source)
        }

    @JvmStatic fun <Source, Value: Any?> setter(modifierBuilder: ((Value) -> Value?) -> ((Source) -> Source)): Setter<Source, Value> =
        object : Setter<Source, Value> {
            override fun modify(modifier: (Value) -> Value?): (Source) -> Source = modifierBuilder(modifier)
        }

    @JvmStatic fun <Source, Value: Any?> lens(accessor: (Source) -> Value?,
                                              modifierBuilder: ((Value) -> Value?) -> ((Source) -> Source)): Lens<Source, Value> =
        object : Lens<Source, Value> {
            override operator fun get(source: Source): Value? = accessor(source)

            override fun modify(modifier: (Value) -> Value?): (Source) -> Source = modifierBuilder(modifier)
        }

    @JvmStatic fun <Source, Value: Any?> prism(accessor: (Source) -> Value?,
                                               reverseGetter: (Value) -> Source): Prism<Source, Value> =
        object : Prism<Source, Value> {
            override operator fun get(source: Source): Value? = accessor(source)

            override fun reverseGet(value: Value): Source = reverseGetter(value)
        }

    object Strict {
        @JvmStatic fun <Source, Value: Any> getter(f: (Source) -> Value): StrictGetter<Source, Value> =
            object : StrictGetter<Source, Value> {
                override operator fun get(source: Source): Value = f(source)
            }

        @JvmStatic fun <Source, Value: Any> setter(modifierBuilder: ((Value) -> Value) -> ((Source) -> Source)): StrictSetter<Source, Value> =
            object : StrictSetter<Source, Value> {
                override fun modify(modifier: (Value) -> Value): (Source) -> Source = modifierBuilder(modifier)
            }

        @JvmStatic fun <Source, Value: Any> lens(accessor: (Source) -> Value,
                                                 modifierBuilder: ((Value) -> Value) -> ((Source) -> Source)): StrictLens<Source, Value> =
            object : StrictLens<Source, Value> {
                override operator fun get(source: Source): Value = accessor(source)

                override fun modify(modifier: (Value) -> Value): (Source) -> Source = modifierBuilder(modifier)
            }

        @JvmStatic fun <Source, Value: Any> prism(accessor: (Source) -> Value,
                                                  reverseGetter: (Value) -> Source): StrictPrism<Source, Value> =
            object : StrictPrism<Source, Value> {
                override operator fun get(source: Source): Value = accessor(source)

                override fun reverseGet(value: Value): Source = reverseGetter(value)
            }
    }
}
