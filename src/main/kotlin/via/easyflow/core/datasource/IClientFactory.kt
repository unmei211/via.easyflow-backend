package via.easyflow.core.datasource

import org.springframework.r2dbc.core.DatabaseClient

interface IClientFactory {
    fun create(): DatabaseClient
}