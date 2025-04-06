package via.easyflow.modules.task.api.interaction.service

import via.easyflow.modules.task.api.contract.`in`.WriteTaskHistoryIn

interface ITaskHistoryService {
    fun writeTaskHistory(taskIn: WriteTaskHistoryIn)
}