package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KafkaEventManagerTest {
    private val logger: Logger = LoggerFactory.getLogger("Log KafkaEventManagerTest")

    @Autowired
    lateinit var eventManager: KafkaEventManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(eventManager)
    }

    @Test
    fun republishSuccessTest() {
        logger.info("Testing: should result in successful requisition")
        val jsonInput = File("./src/test/resources/payload.json").readText()
        val typeRef: Map<String, *>

        typeRef = jacksonObjectMapper().readValue(jsonInput)

        val entityTest = RepublishEventRequest(
                "rw_1",
                typeRef,
                "Reason_1",
                "APPROVER_USER'S_NAME",
                "ertyertye",
                "t"
        )

        val result = this.eventManager.republish(entityTest)
        assertNotNull(result)
        assertEquals(result.message, "Event Republish Success")
        println("\n" + result.id + "\n" + result.message + "\n")
    }

}