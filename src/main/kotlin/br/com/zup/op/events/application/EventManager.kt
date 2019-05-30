package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse

interface EventManager {
    fun republish(request: RepublishEventRequest): RepublishEventResponse
}