package via.easyflow.shared.modules.task.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.subtask.AddSubtaskIn
import via.easyflow.shared.modules.task.api.inputs.subtask.ChangeSubtaskIn
import via.easyflow.shared.modules.task.api.inputs.subtask.GetSubtasksByTaskIdInput
import via.easyflow.shared.modules.task.model.SubtaskModel

interface ISubtaskService {
    fun addSubtask(input: AddSubtaskIn): Mono<SubtaskModel>
    fun changeSubtask(input: ChangeSubtaskIn): Mono<SubtaskModel>
    fun getSubtasksByTaskId(input: GetSubtasksByTaskIdInput): Flux<SubtaskModel>
}