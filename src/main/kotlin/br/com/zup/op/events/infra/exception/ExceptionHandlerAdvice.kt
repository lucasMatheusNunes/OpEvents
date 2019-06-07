package br.com.zup.op.events.infra.exception

import br.com.zup.op.events.infra.validation.ApiFieldError
import br.com.zup.op.events.infra.validation.FieldValidationCallback
import br.com.zup.op.events.infra.validation.FieldException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(FieldException::class)
    @ResponseBody
    fun handleInvalidField(request: HttpServletRequest, ex: FieldException): ResponseEntity<ApiFieldError> =
        ResponseEntity.badRequest().body(ex.apiFieldError)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApiFieldError> =
        ResponseEntity.badRequest().body(ApiFieldError(ex.message, emptyList()))

    @ExceptionHandler(MismatchedInputException::class)
    @ResponseBody
    fun handleMismatchedInput(ex: MismatchedInputException) : ResponseEntity<ApiFieldError> =
        ResponseEntity.badRequest().body(ApiFieldError("Invalid payload", emptyList()))

    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseBody
    fun habdleJsonMapping(ex: MissingKotlinParameterException): ResponseEntity<ApiFieldError> {

        var fieldsError = ArrayList<FieldValidationCallback>()
        ex.path.forEach {
            fieldsError.add(
                FieldValidationCallback(
                    it.fieldName,
                    "Field is required or value passed is invalid"
                )
            )
        }
        return ResponseEntity.badRequest().body(
            ApiFieldError(
                "Exist fields required with invalid values or that were don't send",
                fieldsError
            )
        )
    }
}