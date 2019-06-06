package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.EventRepository
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.ReasonRepository
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.domain.TopicRepository
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import br.com.zup.op.events.producer.KafkaProducerConfig
import com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.stereotype.Service
import kotlin.collections.ArrayList

@Service
class KafkaEventManager(
        private val eventRepository: EventRepository,
        private val reasonRepository: ReasonRepository,
        private val topicRepository: TopicRepository,
        private val kafkaProducerConfig: KafkaProducerConfig) : EventManager {

    private val objectMapper = ObjectMapper()

    private val kafkaTemplate = kafkaProducerConfig.kafkaTemplate()

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        val topics = topicRepository.findAll()
        val reasons = reasonRepository.findAll()
        println(reasons[0].name)
        val eventEntity = EventEntity(
                topic = request.topic,
                payload = objectMapper.writeValueAsString(request.payload),
                reason = request.reason,
                user_id = request.user,
                _key = request.key,
                note = request.note
        )
        eventEntity.validateFields()
        eventEntity.validateTopic(topics)
        eventEntity.validateReason(reasons)

        val statusRepublish = kafkaTemplate.send(request.topic, request.payload.toString())
        //persist
        val savedEntity = this.eventRepository.save(eventEntity)
        println("\n" + statusRepublish + "\n")
        return RepublishEventResponse(savedEntity.id.toString(), "PUBLISHED")
    }

    override fun reasonList(): ArrayList<ReasonEntity> {
        return reasonRepository.findAll() as ArrayList<ReasonEntity>
    }

    override fun topicList(): ArrayList<TopicEntiy> {
        return topicRepository.findAll() as ArrayList<TopicEntiy>
    }

}
