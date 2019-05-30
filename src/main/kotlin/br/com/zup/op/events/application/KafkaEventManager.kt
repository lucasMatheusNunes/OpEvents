package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.Reason
import br.com.zup.op.events.interfaces.model.ReasonsListResponse
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import br.com.zup.op.events.interfaces.model.TopicsListResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KafkaEventManager: EventManager{

    override fun topicList(request: RepublishEventRequest): TopicsListResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

       // return TopicsListResponse(ArrayList<Topic>())
    }

    override fun reasonList(request: RepublishEventRequest): ReasonsListResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //return ReasonsListResponse(ArrayList<Reason>())
    }

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        //do validations
        //call third-part APIs (http, database, kafka)

        return RepublishEventResponse(UUID.randomUUID().toString(), "PUBLISHED")
    }


}