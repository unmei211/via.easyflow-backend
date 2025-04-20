package via.easyflow.modules.task.repository.repository.task_comment

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.arguments.factory.IArgumentsHandlerFactory
import via.easyflow.core.tools.database.query.builder.IQueryBuilder
import via.easyflow.core.tools.database.query.param.filler.IQueryParamMapper
import via.easyflow.modules.task.repository.model.TaskCommentEntity

@Component
class TaskCommentRepository(
    private val client: DatabaseClient,
    private val argsFactory: IArgumentsHandlerFactory,
    private val queryBuilder: IQueryBuilder,
    private val paramMapper: IQueryParamMapper<DatabaseClient.GenericExecuteSpec, Mono<DatabaseClient.GenericExecuteSpec>>
) : ITaskCommentRepository {
    override fun add(taskComment: TaskCommentEntity): Mono<TaskCommentEntity> {
        val sql = """
            INSERT INTO task_comment(document) VALUES (:comment::jsonb)
        """.trimIndent()

        return paramMapper.fillByPairs(
            "comment" to taskComment,
            clientBinder = client.sql(sql)
        ).then(Mono.just(taskComment))
    }

    override fun update(taskComment: TaskCommentEntity): Mono<TaskCommentEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(subtaskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(subtaskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}