package br.com.zup.op.events.domain

import br.com.zup.op.events.infra.validation.InvalidFieldException
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit4.SpringRunner
import java.io.File


@RunWith(SpringRunner::class)
class EventEntityTest {
    private val logger: Logger = LoggerFactory.getLogger("Log EventEntityTest")
    private val topicEntityEmptyList: ArrayList<TopicEntiy> = arrayListOf()
    private val topicEntityFullList: ArrayList<TopicEntiy> =
        arrayListOf(
            TopicEntiy("rw_A"),
            TopicEntiy("rw_B"),
            TopicEntiy("rw_C")
        )
  private val reasons: ArrayList<ReasonEntity> = arrayListOf(
      ReasonEntity("REASON_A"),
      ReasonEntity("REASON_B"),
      ReasonEntity("REASON_C")
  )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should accept the validation when the topic found on list`() {
        logger.info("Testing: should accept the validation when the topic found on list")

        val eventEntity = EventEntity(
            topic = "rw_A",
            payload = File("./src/test/resources/payload.json").readText(),
            user = "lucas.nunes@zup.com.br",
            key = "abcdfghij3493"
        )
        Assert.assertTrue(eventEntity.validateFields() is Unit)
        Assert.assertTrue(eventEntity.validateTopic(topicEntityFullList) is Unit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should accept the validation when the topic not found on list`() {
        logger.info("Testing: should accept the validation when the topic not found on list")

        val eventEntity = EventEntity(null, "TOPIC1", "", "", "")
        eventEntity.validateTopic(topicEntityEmptyList)
    }

    @Test(expected = InvalidFieldException::class)
    fun `should accept the validation when payload and user is blank`() {
        logger.info("Testing: should accept the validation when payload and user is blank")

        val eventEntity = EventEntity(topic = "rw_A", payload = "", user = "", key = "")
        eventEntity.validateFields()
    }

    @Test(expected = InvalidFieldException::class)
    fun `should accept the validation when payload is invalid format`() {
        logger.info("Testing: should accept the validation when payload is invalid format")

        val eventEntity = EventEntity(
            topic = "rw_A",
            payload = File("./src/test/resources/invalidPayload.json").readText(),
            user = "lucas.nunes@zup.com.br",
            key = "abcdfghij3493"
        )
        eventEntity.validateFields()
    }

  @Test
  fun isReasonValidTest() {
    println("\n\n Test of reason validity \n\n")
    val entityTest = EventEntity(
        UUID.randomUUID(),
        "TOPIC_A",
        "REASON_A",
        "{" +
            "'attribute_a': 'param_a'," +
            "'attribute_b': {" +
            "'var_obj_a': 'value_a'" +
            "'var_obj_b': 'value_b'" +
            "'var_obj_c': 'value_c'" +
            "}" +
            "}",
        "APPROVER_USER'S_NAME",
        ""
    )
    Assert.assertTrue(entityTest!!.isReasonValid(reasons) is Unit)
  }

  @Test(expected = IllegalArgumentException::class)
  fun isInvalidReasonTest() {
    println("\n\n Test of reason validity \n\n")
    val entityTest = EventEntity(
        UUID.randomUUID(),
        "TOPIC_A",
        "INVALID_REASON",
        "{" +
            "'attribute_a': 'param_a'," +
            "'attribute_b': {" +
            "'var_obj_a': 'value_a'" +
            "'var_obj_b': 'value_b'" +
            "'var_obj_c': 'value_c'" +
            "}" +
            "}",
        "APPROVER_USER'S_NAME",
        ""
    )
    entityTest!!.isReasonValid(reasons)

        val eventEntity = EventEntity(null, "TOPIC1", "", "", "")
        eventEntity.validateTopic(topicEntityEmptyList)
    }
}
