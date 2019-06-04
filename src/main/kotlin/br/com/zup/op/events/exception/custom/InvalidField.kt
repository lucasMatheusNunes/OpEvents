package br.com.zup.op.events.exception.custom

import br.com.zup.op.events.exception.model.ApiFieldError
import java.lang.RuntimeException

class InvalidField(
    val apiFieldError: ApiFieldError
) : RuntimeException()