package via.easyflow.interactor.controller.task.model

data class PushCommentRequestBody(
    val comment: String,
    val userId: String
) {
}