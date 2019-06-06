package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.domain.TopicRepository
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.ArrayList

@Service
class KafkaEventManager(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val eventRepository: EventRepository,
    private val reasonRepository: ReasonRepository,
    private val topicRepository: TopicRepository
) : EventManager {

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

    val topics = topicRepository.findAll()
    override fun reasonList(): java.util.ArrayList<ReasonEntity> {
      val reasonList = reasonRepository.findAll()
      //return ReasonsListResponse(reasonList as ArrayList<ReasonEntity>, HttpStatus.OK)
      return reasonList as ArrayList<ReasonEntity>
    }

        val eventEntity = EventEntity(
            topic = request.topic,
            payload = objectMapper.writeValueAsString(request.payload),
            request.reason,
            user = request.user,
            key = request.key
	    request.note
        )
        eventEntity.validateFields()
        eventEntity.validateTopic(topics)

        val statusRepublish = kafkaTemplate.send(request.topic, "1", request.payload.toString())

        //persist
        val savedEntity = this.eventRepository.save(event)

        return RepublishEventResponse(savedEntity.id.toString(), statusRepublish)
    }

}
