package br.com.zup.op.events.application

import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback

interface EventManager {



    fun republish(request: RepublishEventRequest): RepublishEventResponse

    fun reasonList(): ArrayList<ReasonEntity>

    fun topicList(): ArrayList<TopicEntiy>

}
