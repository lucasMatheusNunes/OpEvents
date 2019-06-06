package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.*

interface EventManager {
    fun republish(request: RepublishEventRequest): RepublishEventResponse

    fun republishList(request: RepublishEventsListRequest): RepublishEventsListResponse


   // fun sendMessage(topic:String, message: String): ListenableFuture<SendResult<String, String>>



    fun reasonList(): ReasonsListResponse

    fun topicList(): TopicsListResponse

}