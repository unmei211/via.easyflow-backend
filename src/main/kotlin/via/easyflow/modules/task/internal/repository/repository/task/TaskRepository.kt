package via.easyflow.modules.task.internal.repository.repository.task

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.core.exception.NotFoundException
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.modules.task.internal.repository.model.TaskEntity

@Repository
class TaskRepository(
    private val client: DatabaseClient,
    private val objectMapper: ObjectMapper,
    private val jsonRIO: ReactiveJsonIO
) : ITaskRepository {
    override fun add(task: TaskEntity): Mono<TaskEntity> {
        val sql = """
            INSERT INTO task VALUES (:task::jsonb)
        """.trimMargin()

        val taskMono: Mono<String> = jsonRIO.toJson(task)

        return taskMono.flatMap { taskToInsert ->
            client
                .sql(sql)
                .bind("task", taskToInsert)
                .then()
                .then(Mono.fromCallable { task })
        }
    }

    override fun getById(monoTaskId: Mono<String>): Mono<TaskEntity> {
        val sql = """
            SELECT document FROM task WHERE (document ->> 'taskId' = :taskId)
        """.trimIndent()

        return monoTaskId.flatMap {
            client
                .sql(sql)
                .bind("taskId", it)
                .fetch()
                .first()
        }.flatMap { row ->
            val document = row["document"]
            if (document == null) {
                Mono.error(NotFoundException("f"))
            } else {
                jsonRIO.fromJson(document as Json, TaskEntity::class)
            }
        }
    }

    override fun update(task: TaskEntity): Mono<TaskEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(taskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(taskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}