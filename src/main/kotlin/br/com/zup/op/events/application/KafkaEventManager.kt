package br.com.zup.op.events.application

import br.com.zup.op.events.domain.EventEntity
import br.com.zup.op.events.domain.TopicEntiy
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KafkaEventManager : EventManager {
    private val objectMapper = ObjectMapper()

    override fun republish(request: RepublishEventRequest): RepublishEventResponse {

        val topicEntity = ArrayList<TopicEntiy>()

        topicEntity.add(TopicEntiy("TOPICNAME-1"))
        topicEntity.add(TopicEntiy("TOPICNAME-2"))
        topicEntity.add(TopicEntiy("TOPICNAME-3"))
        topicEntity.add(TopicEntiy("TOPICNAME-4"))
        topicEntity.add(TopicEntiy("TOPICNAME-5"))
        topicEntity.add(TopicEntiy("TOPICNAME-6"))

        val eventEntity = EventEntity(
            topic = request.topic,
            payload = objectMapper.writeValueAsString(request.payload),
            user = "lucas.nunes@zup.com.br",
            key = "abcdfghij3493"
        )
        eventEntity.validateFields()
        eventEntity.validateTopic(topicEntity)

        return RepublishEventResponse(UUID.randomUUID().toString(), "PUBLISHED")
    }

}