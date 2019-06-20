package br.com.zup.op.events.infra.exception

import br.com.zup.op.events.infra.validation.ApplicationField
import br.com.zup.op.events.infra.validation.FieldValidationCallback
import br.com.zup.op.events.infra.validation.ApplicationException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(ApplicationException::class)
    @ResponseBody
    fun handleInvalidField(request: HttpServletRequest, ex: ApplicationException): ResponseEntity<ApplicationField> =
        ResponseEntity.badRequest().body(ex.apiFieldError)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApplicationField> =
        ResponseEntity.badRequest().body(ApplicationField(ex.message, emptyList()))

    @ExceptionHandler(MismatchedInputException::class)
    @ResponseBody
    fun handleMismatchedInput(ex: MismatchedInputException) : ResponseEntity<ApplicationField> =
        ResponseEntity.badRequest().body(ApplicationField("Invalid payload", emptyList()))

    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseBody
    fun habdleJsonMapping(ex: MissingKotlinParameterException): ResponseEntity<ApplicationField> {

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
            ApplicationField(
                "Exist fields required with invalid values or that were don't send",
                fieldsError
            )
        )
    }
}