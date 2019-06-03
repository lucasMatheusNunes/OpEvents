package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.EventRepository
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.ReasonRepository
import br.com.zup.op.events.interfaces.model.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.ArrayList

@Service
class KafkaEventManager(
    private val eventRepository: EventRepository,
    private val reasonRepository: ReasonRepository
) : EventManager {

  override fun republishList(request: RepublishEventsListRequest): RepublishEventsListResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun topicList(): TopicsListResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    return TopicsListResponse(ArrayList<ReasonEntity>(), HttpStatus.OK)
  }

  override fun reasonList(): ReasonsListResponse {
    val reasonList = reasonRepository.findAll()
    return ReasonsListResponse(reasonList as ArrayList<ReasonEntity>, HttpStatus.OK)
  }

  override fun republish(request: RepublishEventRequest): RepublishEventResponse {

    //object will altereted
    val event = EventEntity(
        UUID.randomUUID(),
        request.topic,
        request.payload.toString(),
        request.reason,
        request.user,
        request.note
    )
    //do validations

    //call third-part APIs (http, database, kafka)

    //persist
    val savedEntity = this.eventRepository.save(event)

    return RepublishEventResponse(savedEntity.id.toString(), "PUBLISHED")
  }

}