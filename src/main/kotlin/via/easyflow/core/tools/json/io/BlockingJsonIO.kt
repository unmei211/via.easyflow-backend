package via.easyflow.core.tools.json.io

import kotlin.reflect.KClass

interface BlockingJsonIO {
    fun <T : Any> toJson(obj: T): String
    fun <T : Any> fromJson(json: String, clazz: KClass<T>): T
}