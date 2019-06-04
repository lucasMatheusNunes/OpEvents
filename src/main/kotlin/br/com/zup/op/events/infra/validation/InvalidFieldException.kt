package br.com.zup.op.events.infra.validation

import java.lang.RuntimeException

class InvalidFieldException(
    val apiFieldError: ApiFieldError
) : RuntimeException()