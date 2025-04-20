package via.easyflow.modules.task.repository.repository.subtask

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.query.builder.IQueryBuilder
import via.easyflow.core.tools.database.query.param.filler.IQueryParamMapper
import via.easyflow.modules.task.repository.model.SubtaskEntity

@Repository
class SubtaskRepository(
    private val client: DatabaseClient,
    private val queryBuilder: IQueryBuilder,
    private val paramMapper: IQueryParamMapper<DatabaseClient.GenericExecuteSpec, Mono<DatabaseClient.GenericExecuteSpec>>
) : ISubtaskRepository {
    override fun add(subtask: SubtaskEntity): Mono<SubtaskEntity> {
        val sql = """
            INSERT INTO subtask (document) VALUES (:document::jsonb)
        """.trimIndent()

        return paramMapper.fillByPairs(
            "document" to subtask,
            clientBinder = client.sql(sql)
        )
            .flatMap { it.then() }
            .then(Mono.fromCallable { subtask })
    }

    override fun update(subtask: SubtaskEntity): Mono<SubtaskEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(subtaskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(subtaskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}