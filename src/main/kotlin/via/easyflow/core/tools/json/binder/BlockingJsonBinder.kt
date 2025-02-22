package via.easyflow.core.tools.json.binder

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import via.easyflow.core.tools.json.io.BlockingJsonIO
import kotlin.reflect.KClass

@Component
class BlockingJsonBinder(
    private val objectMapper: ObjectMapper,
) : BlockingJsonIO {
    override fun <T : Any> toJson(obj: T): String = objectMapper.writeValueAsString(obj)

    override fun <T : Any> fromJson(json: String, clazz: KClass<T>): T = objectMapper.readValue(json, clazz.java)
}