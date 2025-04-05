package via.easyflow.modules.task.internal.repository.repository.subtask

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.repository.model.SubtaskEntity

interface ISubtaskRepository {
    fun add(subtask: SubtaskEntity): Mono<SubtaskEntity>;
    fun update(subtask: SubtaskEntity): Mono<SubtaskEntity>;
    fun deleteById(subtaskId: String): Mono<String>;
    fun existsById(subtaskId: String): Mono<Boolean>;
}