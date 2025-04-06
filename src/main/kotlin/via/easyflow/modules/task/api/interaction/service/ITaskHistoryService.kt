package via.easyflow.modules.task.api.interaction.service

import reactor.core.publisher.Mono
import via.easyflow.modules.task.api.contract.`in`.WriteTaskHistoryIn
import via.easyflow.modules.task.internal.repository.model.TaskHistoryEntity

interface ITaskHistoryService {
    fun writeTaskHistory(taskIn: WriteTaskHistoryIn): Mono<TaskHistoryEntity>
}