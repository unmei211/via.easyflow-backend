package via.easyflow.core.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.core.tools.database.mapper.R2dbcJsonbMapper
import via.easyflow.core.tools.database.mapper.R2dbcMapper
import via.easyflow.core.tools.json.io.ReactiveJsonIO

@Configuration
class ToolsConfiguration(
    private val jsonRIO: ReactiveJsonIO
) {
    @Bean
    fun jsonbR2dbcMapper(): R2dbcMapper {
        return R2dbcJsonbMapper("document", jsonRIO)
    }
}