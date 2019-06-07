package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.EventRepository
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.ReasonRepository
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.Consumer
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.client.HttpStatusCodeException

@Service
class KafkaEventManager(
    private val eventRepository: EventRepository,
    private val reasonRepository: ReasonRepository,
    private val topicConsumer: Consumer<String, Any>,
    private val kafkaTemplate: KafkaTemplate<String, String>
) : EventManager {

    private val objectMapper = ObjectMapper()

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

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

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
        eventEntity.validateTopic(this.listTopics())
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
        return RepublishEventResponse(savedEntity.id.toString(), "Event Republish Success")


    }
}