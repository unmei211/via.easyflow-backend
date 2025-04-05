package via.easyflow.modules.task.internal.repository.repository.subtask

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.repository.model.SubtaskEntity

class SubtaskRepository : ISubtaskRepository {
    override fun add(subtask: SubtaskEntity): Mono<SubtaskEntity> {
        TODO("Not yet implemented")
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