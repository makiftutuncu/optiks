package sample

import dev.akif.optiks.*
import dev.akif.optiks.Optiks.getter
import dev.akif.optiks.Optiks.prism
import dev.akif.optiks.Optiks.Strict.getter as strictGetter
import dev.akif.optiks.Json.Syntax.toJson

actual class Sample {
    actual fun checkMe() = 42
}

actual object Platform {
    actual val name: String = "JVM"
}

fun test() {
    val json = Json.obj(
        "id"      to 1L.toJson(),
        "name"    to "Mehmet Akif Tutuncu".toJson(),
        "tags"    to listOf(2.toJson(), "test".toJson()).toJson(),
        "details" to mapOf("foo" to "bar".toJson()).toJson()
    )

    val tagsJsonGetter = getter<Json, JArray> { it.`as`<JObject>()?.get("tags") }

    val jArrayTestJStringGetter = getter<JArray, JString> { arr ->
        val j = JString("test")
        arr.values.find { it == j }?.`as`()
    }

    val jStringStringGetter = getter<JString, String> { it.value }

    val testTagGetter = tagsJsonGetter + jArrayTestJStringGetter + jStringStringGetter

    testTagGetter[json]?.also { println(it) }

    val tagsJsonStrictGetter = strictGetter<Json, JArray> { it.`as`<JObject>()?.get("tags") ?: JArray.empty }

    val jArrayTestJsonStrictGetter = strictGetter<JArray, Json> { arr ->
        val j = JString("test")
        arr.values.find { it == j }?.`as`<JString>() ?: JNull
    }

    val jsonStringStrictGetter = strictGetter<Json, String> { it.`as`<JString>()?.value ?: "N/A" }

    val testTagStrictGetter = tagsJsonStrictGetter + jArrayTestJsonStrictGetter + jsonStringStrictGetter

    testTagStrictGetter[json].also { println(it) }

    val jStringPrism = prism<Json, JString>({ it.`as`() }, { it })


}
