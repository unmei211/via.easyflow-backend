package via.easyflow.modules.task.repository.repository.task_history

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.arguments.factory.IArgumentsHandlerFactory
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.modules.task.repository.model.TaskHistoryEntity

@Repository
class TaskHistoryRepository(
    private val mapper: ReactiveJsonIO,
    private val client: DatabaseClient,
    private val argsFactory: IArgumentsHandlerFactory
) : ITaskHistoryRepository {
    override fun add(taskHistory: via.easyflow.modules.task.repository.model.TaskHistoryEntity): Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity> {
        val sql = """
            INSERT INTO task_history(document) VALUES(:document::jsonb)
        """.trimIndent()

        return argsFactory
            .getR2dbcAsync()
            .flatMap { args ->
                args.bind(
                    "document" to taskHistory
                )
            }
            .flatMap { args ->
                client.sql(sql)
                    .bindValues(args.getMap())
                    .then()
            }.then(Mono.just(taskHistory))
    }

    override fun update(taskHistory: via.easyflow.modules.task.repository.model.TaskHistoryEntity): Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(subtaskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(subtaskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}