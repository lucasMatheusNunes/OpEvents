package br.com.zup.op.events.infra.validation

import java.lang.RuntimeException

class FieldException(
    val apiFieldError: ApiFieldError
) : RuntimeException()