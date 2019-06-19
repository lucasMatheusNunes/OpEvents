package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.EventRepository
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.ReasonRepository
import br.com.zup.op.events.domain.TopicEntity
import br.com.zup.op.events.infra.validation.ApplicationException
import br.com.zup.op.events.infra.validation.ApplicationField
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.Consumer
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaEventManager(
    private val eventRepository: EventRepository,
    private val reasonRepository: ReasonRepository,
    private val topicConsumer: Consumer<String, Any>,
    private val kafkaTemplate: KafkaTemplate<String, String>
) : EventManager {

    private val objectMapper = ObjectMapper()

    override fun listReasons(): List<ReasonEntity> {
        return reasonRepository.findAll()
    }

    override fun listTopics(): List<TopicEntity> {
        val topics = topicConsumer.listTopics()
        return topics.map { TopicEntity(it.key) }
    }

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {
        val reasons = this.listReasons()
        val topics = this.listTopics()
        val eventEntity = EventEntity(
            topic = request.topic,
            payload = objectMapper.writeValueAsString(request.payload),
            reason = request.reason,
            user_id = request.user_id,
            _key = request._key,
            note = request.note
        )
        eventEntity.validateFields()
        eventEntity.validateTopic(topics)
        eventEntity.validateReason(reasons)
        try {
            kafkaTemplate.send(eventEntity.topic, eventEntity._key, eventEntity.payload).completable().join()
            kafkaTemplate.flush()
            Thread.sleep(1000)
            eventRepository.save(eventEntity).id.toString()
        } catch (e: Exception) {
            throw ApplicationException(ApplicationField("Error in send of event for apache kafka"))
        }
        return RepublishEventResponse(eventEntity.id.toString(), "Event Republish Success")
    }
}