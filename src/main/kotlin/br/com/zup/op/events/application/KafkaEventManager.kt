package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.EventRepository
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.ReasonRepository
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import br.com.zup.op.events.producer.KafkaProducerConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.Consumer
import org.springframework.stereotype.Service


@Service
class KafkaEventManager(
        private val eventRepository: EventRepository,
        private val reasonRepository: ReasonRepository,
        private val kafkaProducerConfig: KafkaProducerConfig,
        private val topicConsumer: Consumer<String, Any>) : EventManager {

    private val objectMapper = ObjectMapper()

    private val kafkaTemplate = kafkaProducerConfig.kafkaTemplate()

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        val reasons = reasonRepository.findAll()

        val eventEntity = EventEntity(
                topic = request.topic,
                payload = objectMapper.writeValueAsString(request.payload),
                reason = request.reason,
                user_id = request.user,
                _key = request.key,
                note = request.note
        )
        eventEntity.validateFields()
        eventEntity.validateTopic(this.listTopics())
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

    override fun listTopics(): ArrayList<TopicEntiy> {
        var listTopics  = topicConsumer.listTopics()
        val response = ArrayList<TopicEntiy>()

        for((key) in listTopics){
            val topic = TopicEntiy(key)

            response.add(topic)
        }

        return response
    }

}