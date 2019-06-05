package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.domain.TopicRepository
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KafkaEventManager(private val topicRepository: TopicRepository) : EventManager {
    private val objectMapper = ObjectMapper()

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        val topicEntityDataBase = topicRepository.findAll()

        val eventEntity = EventEntity(
            topic = request.topic,
            payload = objectMapper.writeValueAsString(request.payload),
            user = request.user,
            key = request.key
        )
        eventEntity.validateFields()
        eventEntity.validateTopic(topicEntityDataBase)

        return RepublishEventResponse(UUID.randomUUID().toString(), "PUBLISHED")
    }

}