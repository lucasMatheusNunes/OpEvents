package br.com.zup.op.events.interfaces.model

import br.com.zup.op.events.domain.ReasonEntity
import org.springframework.http.HttpStatus
import java.util.*

data class RepublishEventRequest(
    val topic: String,
    val payload : Map<String, *>,
    val reason: String,
    val user: String,
    val note: String
)

data class RepublishEventsListRequest(
    val topic: String?,
    val reason: String?,
    val user: String?,
    val initDate: Date?,
    val endDate: Date?
)

data class RepublishEventResponse(
    val id: String,
    val status: String
)

data class RepublishEventsListResponse(
    val events: ArrayList<Any>,
    val status: HttpStatus
)

data class ReasonsListResponse(
    val reasons: ArrayList<ReasonEntity>,
    val status: HttpStatus
)

data class TopicsListResponse(
    val topics: ArrayList<ReasonEntity>,
    val status: HttpStatus
)