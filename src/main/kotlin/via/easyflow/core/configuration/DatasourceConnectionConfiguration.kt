package via.easyflow.core.configuration

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient
import via.easyflow.core.configuration.properties.DataSourceProperties
import via.easyflow.core.datasource.ClientFactory
import via.easyflow.core.datasource.IClientFactory

@Configuration
class DatasourceConnectionConfiguration(
    private val props: DataSourceProperties
) {
    @Bean
    fun connectionFactory(): ConnectionFactory = ConnectionFactories.get(
        builder()
            .option(DRIVER, props.driver)
            .option(HOST, props.host)
            .option(USER, props.user)
            .option(PORT, props.port)
            .option(PASSWORD, props.password)
            .option(DATABASE, props.name)
            .build()
    )

    @Bean
    fun clientFactory(connectionFactory: ConnectionFactory): IClientFactory = ClientFactory(connectionFactory)

    @Bean
    fun databaseClient(clientFactory: IClientFactory): DatabaseClient = clientFactory.create()
}