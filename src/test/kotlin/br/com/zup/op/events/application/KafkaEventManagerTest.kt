package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.status
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KafkaEventManagerTest {
    private val logger: Logger = LoggerFactory.getLogger("\n\nLog KafkaEventManagerTest")

    @Autowired
    lateinit var eventManager: KafkaEventManager

    @Test
    fun `should result in successful requisition`() {
        logger.info("Testing: should result in successful requisition\n")
        val jsonInput = File("./src/test/resources/payload.json").readText()
        val typeRef: Map<String, *> = jacksonObjectMapper().readValue(jsonInput)

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
        logger.info(result.message + "! ID: "+ result.id)
    }

}