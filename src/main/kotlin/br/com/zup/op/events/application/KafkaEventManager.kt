package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.EventRepository
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.ReasonRepository
import br.com.zup.op.events.interfaces.model.*
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.ArrayList



@Service
class KafkaEventManager(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val eventRepository: EventRepository,
    private val reasonRepository: ReasonRepository
) : EventManager {


  override fun reasonList(): java.util.ArrayList<ReasonEntity> {
    val reasonList = reasonRepository.findAll()
    //return ReasonsListResponse(reasonList as ArrayList<ReasonEntity>, HttpStatus.OK)
      return reasonList as ArrayList<ReasonEntity>
  }

  override fun republish(request: RepublishEventRequest): RepublishEventResponse {

      //object will altereted
    val event = EventEntity(
        UUID.randomUUID(),
        request.topic,
        request.payload.toString(),
        request.reason,
        request.user_name,
        request.note
    )
    //do validations

    //call third-part APIs (http, database, kafka)

    val statusRepublish = kafkaTemplate.send(request.topic, "1", request.payload.toString())

    //persist
    val savedEntity = this.eventRepository.save(event)

    return RepublishEventResponse(savedEntity.id.toString(), statusRepublish)
  }




  override fun republishList(request: RepublishEventsListRequest): RepublishEventsListResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  //override fun reasonList(): ArrayList<ReasonEntity> = reasonRepository.findAll() as ArrayList<ReasonEntity>

  override fun topicList(): TopicsListResponse = TopicsListResponse(ArrayList<ReasonEntity>(), HttpStatus.OK)

}
//kafkaTemplate