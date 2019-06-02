package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.ReasonsListResponse
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import br.com.zup.op.events.interfaces.model.TopicsListResponse

interface EventManager {
    fun republish(request: RepublishEventRequest): RepublishEventResponse

    fun reasonList(): ReasonsListResponse

    fun topicList(): TopicsListResponse

}