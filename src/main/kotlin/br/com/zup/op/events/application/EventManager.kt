package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.*

interface EventManager {
    fun republish(request: RepublishEventRequest): RepublishEventResponse

    fun republishList(request: RepublishEventsListRequest): RepublishEventsListResponse

    fun reasonList(): ReasonsListResponse

    fun topicList(): TopicsListResponse

}