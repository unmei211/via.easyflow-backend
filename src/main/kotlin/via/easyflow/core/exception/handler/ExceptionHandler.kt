package via.easyflow.core.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import via.easyflow.core.api.out.exception.BadRequestExceptionOut
import via.easyflow.core.api.out.exception.ConflictExceptionOut
import via.easyflow.core.api.out.exception.NotFoundExceptionOut
import via.easyflow.core.exception.BadRequestException
import via.easyflow.core.exception.ConflictException
import via.easyflow.core.exception.NotFoundException
import via.easyflow.core.tools.logger.logger


@ControllerAdvice
class ExceptionHandler {
    private val log = logger()


    @ExceptionHandler(NotFoundException::class)
    fun handleDataAccessException(ex: NotFoundException): ResponseEntity<NotFoundExceptionOut> {
        log.error(ex.message, ex)
        return ResponseEntity(
            NotFoundExceptionOut(ex.errors, ex.message ?: ""),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(ConflictException::class)
    fun handleDataAccessException(ex: ConflictException): ResponseEntity<ConflictExceptionOut> {
        log.error(ex.message, ex)
        return ResponseEntity(
            ConflictExceptionOut(ex.errors, ex.message ?: ""),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleDataAccessException(ex: BadRequestException): ResponseEntity<BadRequestExceptionOut> {
        log.error(ex.message, ex)
        return ResponseEntity(
            BadRequestExceptionOut(ex.errors, ex.message ?: ""),
            HttpStatus.BAD_REQUEST
        )
    }
//    }
//
//    /**
//     * Handles all other generic exceptions and returns a response indicating an internal server error.
//     * <p>
//     * This method catches exceptions not explicitly handled by other methods.
//     *
//     * @param ex the Exception thrown during request processing.
//     * @return a {@link ResponseEntity} containing an {@link InternalErrorDto} with error details and HTTP 500 status.
//     */
//    @ExceptionHandler(Exception::class)
//    fun handleGenericException(ex: Exception): ResponseEntity<InternalErrorDto> {
//        logger.error(ex.message, ex)
//        return ResponseEntity
//            .internalServerError()
//            .body(
//                InternalErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.INTERNAL_SERVER_ERROR.name,
//                    errorMessage = "Something went wrong while processing the request",
//                    errors = listOf(),
//                ),
//            )
//    }

//    /**
//     * Handles {@link MethodArgumentTypeMismatchException} and returns a response indicating a bad request error.
//     * <p>
//     * This method provides detailed feedback when a request parameter has an invalid type.
//     *
//     * @param ex the MethodArgumentTypeMismatchException thrown when a parameter type does not match.
//     * @return a {@link ResponseEntity} containing a {@link BadRequestErrorDto} with error details and HTTP 400 status.
//     */
//    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
//    fun handleTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<BadRequestErrorDto> =
//        ResponseEntity
//            .badRequest()
//            .body(
//                BadRequestErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.BAD_REQUEST.name,
//                    errorMessage = "Parameter '${ex.name}' should be of type ${ex.requiredType?.simpleName}",
//                    errors = mutableListOf(),
//                ),
//            )
//
//    /**
//     * Handles {@link MethodArgumentTypeMismatchException} and returns a response indicating a bad request error.
//     * <p>
//     * This method provides detailed feedback when a request parameter has an invalid type.
//     *
//     * @param ex the MethodArgumentTypeMismatchException thrown when a parameter type does not match.
//     * @return a {@link ResponseEntity} containing a {@link BadRequestErrorDto} with error details and HTTP 400 status.
//     */
//    @ExceptionHandler(MissingServletRequestParameterException::class)
//    fun handleTypeMismatch(ex: MissingServletRequestParameterException): ResponseEntity<BadRequestErrorDto> =
//        ResponseEntity
//            .badRequest()
//            .body(
//                BadRequestErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.BAD_REQUEST.name,
//                    errorMessage = "Parameter '${ex.parameterName}' should not be NULL.",
//                    errors = listOf(),
//                ),
//            )
//
//    @ExceptionHandler(HttpMessageNotReadableException::class)
//    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<BadRequestErrorDto> =
//        ResponseEntity
//            .badRequest()
//            .body(
//                BadRequestErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.BAD_REQUEST.name,
//                    errorMessage = ex.message,
//                    errors = listOf(),
//                ),
//            )
//
//    @ExceptionHandler(IllegalArgumentException::class)
//    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<UnprocessableEntityErrorDto> =
//        ResponseEntity
//            .unprocessableEntity()
//            .body(
//                UnprocessableEntityErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.UNPROCESSABLE_ENTITY.name,
//                    errorMessage = ex.message,
//                    errors = listOf(),
//                ),
//            )
//
//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun handleHttpMessageNotReadable(ex: MethodArgumentNotValidException): ResponseEntity<UnprocessableEntityErrorDto> {
//        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }
//
//        return ResponseEntity
//            .unprocessableEntity()
//            .body(
//                UnprocessableEntityErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.UNPROCESSABLE_ENTITY.name,
//                    errorMessage = ex.message,
//                    errors = errors.map { (key, value) -> "$key=$value" },
//                ),
//            )
//    }
//
//    @ExceptionHandler(ValidationViolationException::class)
//    fun handleValidationViolation(ex: ValidationViolationException): ResponseEntity<BadRequestErrorDto> =
//        ResponseEntity
//            .badRequest()
//            .body(
//                BadRequestErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.BAD_REQUEST.name,
//                    errorMessage = ex.message,
//                    errors = ex.violations.map { it.messageTemplate },
//                ),
//            )
//
//    /**
//     * Handles {@link NotFoundException} and returns a response indicating that the requested resource was not found.
//     *
//     * @param ex the NotFoundException thrown when a resource is not found.
//     * @return a {@link ResponseEntity} containing a {@link NotFoundErrorDto} with error details and HTTP 404 status.
//     */
//    @ExceptionHandler(NotFoundException::class)
//    fun handleNotFound(ex: NotFoundException): ResponseEntity<NotFoundErrorDto> {
//        logger.error(ex.message, ex)
//        return ResponseEntity(
//            NotFoundErrorDto(
//                status = false,
//                errorCode = HttpStatus.NOT_FOUND.name,
//                errorMessage = "The requested resource was not found.",
//                errors = ex.errors,
//            ),
//            HttpStatus.NOT_FOUND,
//        )
//    }
//
//    @ExceptionHandler(ConstraintViolationException::class)
//    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<BadRequestErrorDto> =
//        ResponseEntity(
//            BadRequestErrorDto(
//                status = false,
//                errorCode = HttpStatus.BAD_REQUEST.name,
//                errorMessage = ex.localizedMessage,
//                errors = emptyList(),
//            ),
//            HttpStatus.BAD_REQUEST,
//        )
//
//    /**
//     * Handles {@link NoResourceFoundException} and returns a response indicating a not found error.
//     * <p>
//     * This method provides detailed feedback when there's invalid URL.
//     *
//     * @param ex the NoResourceFoundException thrown when a URL is invalid.
//     * @return a {@link ResponseEntity} containing a {@link NotFoundErrorDto} with error details and HTTP 404 status.
//     */
//    @ExceptionHandler(NoResourceFoundException::class)
//    fun handleURLNotFound(ex: NoResourceFoundException): ResponseEntity<NotFoundErrorDto> {
//        logger.error(ex.message, ex)
//        return ResponseEntity
//            .status(HttpStatus.NOT_FOUND)
//            .body(
//                NotFoundErrorDto(
//                    status = false,
//                    errorCode = HttpStatus.NOT_FOUND.name,
//                    errorMessage = "Provided URL not found",
//                    errors = listOf(),
//                ),
//            )
//    }
//
////    @ExceptionHandler(ForbiddenException::class)
////    fun handleForbidden(ex: ForbiddenException): ResponseEntity<NotFoundErrorDto> {
////        logger.error(ex.message, ex)
////        return ResponseEntity(
////            NotFoundErrorDto(
////                status = false,
////                errorCode = "NOT_FOUND",
////                errorMessage = "The requested resource was not found.",
////                errors = ex.errors
////            ),
////            HttpStatus.NOT_FOUND,
////        )
////    }
}
