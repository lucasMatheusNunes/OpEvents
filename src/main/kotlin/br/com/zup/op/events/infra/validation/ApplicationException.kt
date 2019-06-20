package br.com.zup.op.events.infra.validation

import java.lang.RuntimeException

class ApplicationException(
    val apiFieldError: ApplicationField
) : RuntimeException()