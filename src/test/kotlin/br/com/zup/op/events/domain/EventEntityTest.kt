package br.com.zup.op.events.domain

import br.com.zup.op.events.infra.validation.ApplicationException
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

@RunWith(SpringRunner::class)
class EventEntityTest() {
    private val logger: Logger = LoggerFactory.getLogger("\n\nLog EventEntityTest")

    private val topicEntityEmptyList: List<TopicEntity> = arrayListOf()
    private val topicEntityFullList: List<TopicEntity> =
            arrayListOf(
                    TopicEntity("rw_A"),
                    TopicEntity("rw_B"),
                    TopicEntity("rw_C")
            )
    private val reasons: List<ReasonEntity> = arrayListOf(
            ReasonEntity("REASON_A"),
            ReasonEntity("REASON_B"),
            ReasonEntity("REASON_C")
    )

    @Test
    fun `should accept the validation when the topic found on list`() {
        logger.info("Testing: should accept the validation when the topic found on list\n")

        val eventEntity = EventEntity(
                topic = "rw_A",
                payload = File("./src/test/resources/payload.json").readText(),
                reason = "Reason_1",
                user_id = "lucas.nunes@zup.com.br",
                _key = "abcdfghij3493",
                note = ""
        )
        Assert.assertTrue(eventEntity.validateFields() is Unit)
        Assert.assertTrue(eventEntity.validateTopic(topicEntityFullList) is Unit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should accept the validation when the topic not found on list`() {
        logger.info("Testing: should accept the validation when the topic not found on list\n")

        val eventEntity = EventEntity(null, "TOPIC1", "", "", "", "", "")
        eventEntity.validateTopic(topicEntityEmptyList)
    }

    @Test(expected = ApplicationException::class)
    fun `should accept the validation when payload and user is blank`() {
        logger.info("Testing: should accept the validation when payload and user is blank\n")

        val eventEntity = EventEntity(topic = "rw_A", payload = "", reason = "", user_id = "", _key = "", note = "")
        eventEntity.validateFields()
    }

    @Test
    fun `should accept the validation when reason is exists`() {
        logger.info("Testing: should accept the validation when reason is exists\n")

        val entityTest = EventEntity(
                topic = "rw_A",
                payload = File("./src/test/resources/invalidPayload.json").readText(),
                reason = "REASON_A",
                user_id = "lucas.nunes@zup.com.br",
                _key = "abcdfghij3493",
                note = ""
        )
        Assert.assertTrue(entityTest.validateReason(reasons) is Unit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should reprove the validation when reason is not exists`() {
        logger.info("Testing: should reprove the validation when reason is not exists\n")

        val entityTest = EventEntity(
                topic = "rw_A",
                payload = File("./src/test/resources/invalidPayload.json").readText(),
                reason = "REASON_W",
                user_id = "lucas.nunes@zup.com.br",
                _key = "abcdfghij3493",
                note = ""
        )
        AssertionError(entityTest.validateReason(reasons))
    }
}
