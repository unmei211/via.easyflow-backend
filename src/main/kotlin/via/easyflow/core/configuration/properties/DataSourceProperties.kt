package via.easyflow.core.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "datasource")
data class DataSourceProperties(
    val host: String,
    val port: Int,
    val name: String,
    val user: String,
    val password: String,
    val driver: String,
)