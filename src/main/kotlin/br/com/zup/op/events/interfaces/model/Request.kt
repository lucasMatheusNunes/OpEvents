package br.com.zup.op.events.interfaces.model

data class RepublishEventRequest(
    val topic: String,
    val payload : Map<String, *>,
    val reason: String,
    val user_id: String,
    val _key: String,
    val note: String?
)

data class RepublishEventResponse(
    val id: String,
    val status: String
)

