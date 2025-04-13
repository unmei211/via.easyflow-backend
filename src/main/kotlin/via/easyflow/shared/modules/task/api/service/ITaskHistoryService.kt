package via.easyflow.shared.modules.task.api.service

import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.WriteTaskHistoryIn
import via.easyflow.modules.task.repository.model.TaskHistoryEntity

interface ITaskHistoryService {
    fun writeTaskHistory(taskIn: WriteTaskHistoryIn): Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity>
}