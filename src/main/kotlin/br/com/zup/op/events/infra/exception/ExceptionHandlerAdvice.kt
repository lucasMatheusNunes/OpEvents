package br.com.zup.op.events.infra.exception

import br.com.zup.op.events.infra.validation.ApiFieldError
import br.com.zup.op.events.infra.validation.InvalidFieldException
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
    fun handleInvalidField(request: HttpServletRequest, ex: InvalidFieldException) : ResponseEntity<ApiFieldError>{
        return ResponseEntity(ex.apiFieldError, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

}