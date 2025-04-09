package via.easyflow.modules.task.internal.repository.repository.task

import io.r2dbc.postgresql.codec.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.exception.ConflictException
import via.easyflow.core.tools.database.arguments.factory.IArgumentsHandlerFactory
import via.easyflow.core.tools.database.query.builder.IQueryBuilder
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.param.filler.IQueryParamMapper
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.modules.task.internal.repository.contract.UpdateTaskMutation
import via.easyflow.modules.task.internal.repository.model.TaskEntity

@Repository
class TaskRepository(
    private val client: DatabaseClient,
    private val jsonRIO: ReactiveJsonIO,
    private val argsFactory: IArgumentsHandlerFactory,
    private val queryBuilder: IQueryBuilder,
    private val paramMapper: IQueryParamMapper<DatabaseClient.GenericExecuteSpec, Mono<DatabaseClient.GenericExecuteSpec>>
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

        val argsMono = argsFactory.getR2dbc()

        return argsMono.bind(
            "version" to mutation.currentVersion,
            "taskId" to mutation.taskEntity.taskId,
            "projectId" to mutation.taskEntity.projectId,
            "document" to mutation.taskEntity
        ).flatMap { args ->
            client.sql(sql)
                .bindValues(args.getMap())
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

    override fun searchByFilter(filters: List<IQueryFilter>): Flux<TaskEntity> {
        val sql = """
            SELECT document FROM task WHERE ${queryBuilder.build(filters)}
        """.trimIndent()

        val specs: DatabaseClient.GenericExecuteSpec = client.sql(sql)

        val specsMono: Mono<DatabaseClient.GenericExecuteSpec> = paramMapper.fill(
            filters,
            specs
        )

        val rows: Flux<MutableMap<String, Any>> = specsMono.flatMapMany {
            it.fetch().all()
        }

        return rows.concatMap { row ->
            jsonRIO.fromJson(row["document"] as Json, TaskEntity::class)
        }
    }


    override fun deleteById(taskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(taskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}