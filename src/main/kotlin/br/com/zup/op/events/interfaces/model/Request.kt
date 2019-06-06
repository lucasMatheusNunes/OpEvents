package br.com.zup.op.events.interfaces.model

import br.com.zup.op.events.domain.ReasonEntity
import org.springframework.http.HttpStatus
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import java.util.*

data class RepublishEventRequest(
    val topic: String,
    val payload : Map<String, *>,
    val reason: String,
    val user_name: String,
    val note: String?
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
    val status: ListenableFuture<SendResult<String, String>>
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

data class Kafka(val bootstrapServers: String)
