package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KafkaEventManager: EventManager{

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        //do validations
        //call third-part APIs (http, database, kafka)

        return RepublishEventResponse(UUID.randomUUID().toString(), "PUBLISHED")
    }

}