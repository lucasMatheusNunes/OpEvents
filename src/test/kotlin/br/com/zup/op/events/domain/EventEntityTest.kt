package br.com.zup.op.events.domain

import br.com.zup.op.events.exception.custom.InvalidField
import com.fasterxml.jackson.core.JsonParseException
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
class EventEntityTest {
    private val logger: Logger = LoggerFactory.getLogger("Log EventEntityTest")
    private val topicEntityEmptyList: ArrayList<TopicEntiy> = arrayListOf()
    private val topicEntityFullList: ArrayList<TopicEntiy> =
        arrayListOf(
            TopicEntiy("TOPICNAME-A"),
            TopicEntiy("TOPICNAME-B"),
            TopicEntiy("TOPICNAME-C")
        )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should accept the validation when the topic found on list`() {
        logger.info("Testing: should accept the validation when the topic found on list")

        val payload : String = "{\"name\" : \"realwave\", " +
                                    "\"system\" : { " +
                                        "\"name\": \"system name\" " +
                                    "} " +
                                "}"
        val eventEntity = EventEntity(topic = "TOPICNAME-A", payload = payload, user = "lucas.nunes@zup.com.br")
        Assert.assertTrue(eventEntity.validateFields() is Unit)
        Assert.assertTrue(eventEntity.validateTopic(topicEntityFullList) is Unit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should accept the validation when the topic not found on list`() {
        logger.info("Testing: should accept the validation when the topic not found on list")

        val eventEntity = EventEntity(null, "TOPIC1", "", "")
        eventEntity.validateTopic(topicEntityEmptyList)
    }

    @Test(expected = InvalidField::class)
    fun `should accept the validation when payload and user is blank`() {
        logger.info("Testing: should accept the validation when payload and user is blank")

        val eventEntity = EventEntity(topic = "TOPICNAME-A", payload = "", user = "")
        Assert.assertTrue(eventEntity.validateFields() is Unit)
    }

    @Test(expected = JsonParseException::class)
    fun `should accept the validation when payload is invalid format`() {
        logger.info("Testing: should accept the validation when payload is invalid format")

        val payload : String = "{\"name\" : \"realwave\", " +
                                    "\"system\" " +
                                        "\"name\" \"system name\" " +
                                "}"
        val eventEntity = EventEntity(topic = "TOPICNAME-A", payload = payload, user = "lucas.nunes@zup.com.br")
        eventEntity.validateFields() is Unit
    }
}