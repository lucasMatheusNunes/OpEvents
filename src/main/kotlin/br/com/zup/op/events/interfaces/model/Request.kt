package br.com.zup.op.events.interfaces.model

import javax.persistence.Entity
import javax.persistence.Id

data class RepublishEventRequest(
    val topic: String,
    val event : Map <String, *>,
    val reason: String
)

data class RepublishEventResponse(
    val id: String,
    val status: String
)

data class ReasonsListResponse(
    val reasons: ArrayList<Reason>
)

data class TopicsListResponse(
    val reasons: ArrayList<Reason>
)