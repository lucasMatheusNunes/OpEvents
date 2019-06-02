package br.com.zup.op.events.interfaces.model

import org.springframework.http.HttpStatus

data class RepublishEventRequest(
    val topic: String,
    val payload : Map<String, *>,
    val reason: String,
    val user: String,
    val note: String
)

data class RepublishEventResponse(
    val id: String,
    val status: String
)

data class ReasonsListResponse(
    val reasons: ArrayList<Reason>,
    val status: HttpStatus
)

data class TopicsListResponse(
    val topics: ArrayList<Reason>,
    val status: HttpStatus
)