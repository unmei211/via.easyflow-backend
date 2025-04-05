package via.easyflow.modules.task.internal.repository.repository.task

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.core.exception.ConflictException
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.modules.task.internal.repository.contract.UpdateTaskMutation
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
                .one()
        }
            .flatMap { row ->
                val document = row["document"]
                if (document == null) {
                    Mono.error(IllegalArgumentException("document is null"))
                } else {
                    jsonRIO.fromJson(document as Json, TaskEntity::class)
                }
            }
    }

    override fun update(mutation: UpdateTaskMutation): Mono<TaskEntity> {
        val sql = """
            UPDATE task SET document = :document::jsonb
            WHERE 
            (document ->> 'taskId' = :taskId) and 
            (document ->> 'version' = :version) and
            (document ->> 'projectId' = :projectId)
        """.trimIndent()

        val jsonMono = Mono.from(jsonRIO.toJson(mutation.taskEntity))

        val argsMono = Mono
            .just(mutableMapOf<String, Any>())
            .map { arg ->
                arg["version"] = mutation.currentVersion
                arg["taskId"] = mutation.taskEntity.taskId
                arg["projectId"] = mutation.taskEntity.projectId
                arg
            }

        val args: Mono<Map<String, Any>> = jsonMono.flatMap { json ->
            argsMono.map { args ->
                args["document"] = json
                args
            }
        }

        return args.flatMap { arg ->
            client.sql(sql)
                .bindValues(arg)
                .fetch()
                .rowsUpdated()
        }.flatMap { rowsUpdated ->
            if (rowsUpdated == 0L) {
                Mono.error(ConflictException("Conflict"))
            } else {
                Mono.just(mutation.taskEntity)
            }
        }
    }

    override fun deleteById(taskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(taskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}