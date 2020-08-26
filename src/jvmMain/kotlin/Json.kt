package dev.akif.optiks

import java.math.BigDecimal

sealed class Json {
    inline fun <reified J: Json> `as`(): J? =
        when (this) {
            is J -> this
            else -> null
        }

    companion object {
        fun `null`(): JNull = JNull

        fun arr(vararg values: Json): JArray = JArray(*values)

        fun obj(vararg pairs: Pair<String, Json>): JObject = JObject(*pairs)

        fun bool(value: Boolean): JBoolean = JBoolean(value)

        fun str(value: String): JString = JString(value)

        fun byte(value: Byte): JNumber = JNumber(BigDecimal(value.toInt()))

        fun short(value: Short): JNumber = JNumber(BigDecimal(value.toInt()))

        fun int(value: Int): JNumber = JNumber(BigDecimal(value))

        fun long(value: Long): JNumber = JNumber(BigDecimal(value))

        fun float(value: Float): JNumber = JNumber(BigDecimal(value.toDouble()))

        fun double(value: Double): JNumber = JNumber(BigDecimal(value))
    }

    object Syntax {
        fun List<Json>.toJson(): JArray = JArray(this)

        fun Map<String, Json>.toJson(): JObject = JObject(this)

        fun Boolean.toJson(): JBoolean = bool(this)

        fun String.toJson(): JString = str(this)

        fun Byte.toJson(): JNumber = byte(this)

        fun Short.toJson(): JNumber = short(this)

        fun Int.toJson(): JNumber = int(this)

        fun Long.toJson(): JNumber = long(this)

        fun Float.toJson(): JNumber = float(this)

        fun Double.toJson(): JNumber = double(this)
    }
}

object JNull: Json() {
    override fun toString(): String = "null"
}

sealed class JPrimitive<out Value: Any>(open val value: Value): Json()

data class JBoolean(override val value: Boolean): JPrimitive<Boolean>(value) {
    override fun toString(): String = value.toString()
}

data class JString(override val value: String): JPrimitive<String>(value) {
    override fun toString(): String = """"${value.replace("\"", "\\\"")}""""
}

data class JNumber(override val value: BigDecimal): JPrimitive<BigDecimal>(value) {
    override fun toString(): String = value.toPlainString()
}

data class JArray(val values: List<Json>): Json() {
    constructor(vararg values: Json): this(values.toList())

    inline operator fun <reified J: Json> get(index: Int): J? = if (index !in values.indices) null else values[index].to()

    override fun toString(): String = values.joinToString(",", "[", "]")

    companion object {
        val empty: JArray = JArray(emptyList())
    }
}

data class JObject(val values: Map<String, Json>): Json() {
    constructor(vararg pairs: Pair<String, Json>): this(pairs.toMap())

    inline operator fun <reified J: Json> get(key: String): J? = values[key]?.to()

    override fun toString(): String =
        values.entries.joinToString(",", "{", "}") { (key, value) ->
            """"${key.replace("\"", "\\\"")}":$value"""
        }

    companion object {
        val empty: JObject = JObject(emptyMap())
    }
}
