package br.com.zup.op.events.infra.validation

import java.lang.RuntimeException

class SendPayloadException(
    val errorMessage: String = "Error in send of event for apache kafka"
) : RuntimeException()