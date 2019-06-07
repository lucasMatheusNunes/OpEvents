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
import net.bytebuddy.asm.Advice
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult

import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.client.HttpStatusCodeException
import kotlin.collections.ArrayList

@Service
class KafkaEventManager(
        private val eventRepository: EventRepository,
        private val reasonRepository: ReasonRepository,
        private val topicRepository: TopicRepository,
        private val kafkaTemplate: KafkaTemplate<String, String>
) : EventManager {

    private val objectMapper = ObjectMapper()

    override fun reasonList(): ArrayList<ReasonEntity> {
        return reasonRepository.findAll() as ArrayList<ReasonEntity>
    }

    override fun topicList(): ArrayList<TopicEntiy> {
        return topicRepository.findAll() as ArrayList<TopicEntiy>
    }

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        val topics = topicRepository.findAll()
        val reasons = reasonRepository.findAll()
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
        val future = kafkaTemplate.send(eventEntity.topic, eventEntity.payload)

        future.addCallback(object : ListenableFutureCallback<SendResult<String, String>> {

            override fun onSuccess(result: SendResult<String, String>?) {
                //val savedEntity = eventRepository.save(eventEntity)
            }

            override fun onFailure(ex: Throwable) {
                ex.printStackTrace()
                if (ex is HttpStatusCodeException) {
                    println(ex.responseBodyAsString)
                }
            }

        })
        val savedEntity = this.eventRepository.save(eventEntity)
        return RepublishEventResponse(savedEntity.id.toString(), "PUBLISHED")


    }
}

