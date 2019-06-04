package br.com.zup.op.events.interfaces.model

data class RepublishEventRequest(
    val topic: String,
    val payload : Map <String, *>?
)

data class RepublishEventResponse(
    val id: String,
    val status: String
)