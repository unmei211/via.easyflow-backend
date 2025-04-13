package via.easyflow.modules.task.repository.repository.subtask

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.modules.task.repository.model.SubtaskEntity

@Repository
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