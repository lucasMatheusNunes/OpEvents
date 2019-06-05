package br.com.zup.op.events.infra.exception

import br.com.zup.op.events.infra.validation.ApiFieldError
import br.com.zup.op.events.infra.validation.FieldValidationCallback
import br.com.zup.op.events.infra.validation.InvalidFieldException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(InvalidFieldException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleInvalidField(request: HttpServletRequest, ex: InvalidFieldException): ResponseEntity<ApiFieldError> {
        return ResponseEntity(ex.apiFieldError, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApiFieldError> {
        return ResponseEntity(
            ApiFieldError(ex.message, emptyList()),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
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
        return ResponseEntity(
            ApiFieldError(
                "Exist fields required with invalid values or that were don't send",
                fieldsError
            ), HttpHeaders(), HttpStatus.BAD_REQUEST
        )
    }
}