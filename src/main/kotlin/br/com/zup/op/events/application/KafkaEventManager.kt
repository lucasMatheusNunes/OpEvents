package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.ArrayList

@Service
class KafkaEventManager: EventManager{

    override fun topicList(): TopicsListResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

       // return TopicsListResponse(ArrayList<Topic>())
        return TopicsListResponse(ArrayList<Reason>(), HttpStatus.OK)

    }

    override fun reasonList(): ReasonsListResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //return ReasonsListResponse(ArrayList<Reason>())
        return ReasonsListResponse(ArrayList<Reason>(), HttpStatus.OK)

    }

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        //do validations
        //call third-part APIs (http, database, kafka)

        return RepublishEventResponse(UUID.randomUUID().toString(), "PUBLISHED")
    }


}