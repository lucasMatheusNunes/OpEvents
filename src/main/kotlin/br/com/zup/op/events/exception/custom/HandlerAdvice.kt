package br.com.zup.op.events.exception.custom

import br.com.zup.op.events.exception.model.ApiFieldError
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class HandlerAdvice {

    @ExceptionHandler(InvalidField::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleInvalidField(request: HttpServletRequest, ex: InvalidField) : ResponseEntity<ApiFieldError>{
        return ResponseEntity(ex.apiFieldError, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

}