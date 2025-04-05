package via.easyflow.core.datasource

import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient

class ClientFactory(private val connectionFactory: ConnectionFactory) : IClientFactory {
    override fun create(): DatabaseClient {
        return DatabaseClient
            .builder()
            .connectionFactory(connectionFactory)
            .build()
    }
}