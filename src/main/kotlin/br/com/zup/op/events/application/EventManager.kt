package br.com.zup.op.events.application

import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.interfaces.model.*
import java.util.ArrayList

interface EventManager {
    fun republish(request: RepublishEventRequest): RepublishEventResponse

    fun republishList(request: RepublishEventsListRequest): RepublishEventsListResponse

    fun reasonList(): ArrayList<ReasonEntity>

    fun topicList(): TopicsListResponse

}