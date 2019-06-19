package br.com.zup.op.events.interfaces.controller


import br.com.zup.op.events.application.KafkaConsumer
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException
import java.io.File


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RepublishControllerTest {
    private val logger: Logger = LoggerFactory.getLogger(RepublishControllerTest::class.java)

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    private lateinit var controller: RepublishController

    @Autowired
    private lateinit var consumer : KafkaConsumer

    private val payload: Map<String, *> = jacksonObjectMapper().readValue(
            File("./src/test/resources/payload.json").readText())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()
    }

    @Test(expected = NestedServletException::class)
    fun `should result in a failed request because the key is blank`() {
        logger.info("should result in a failed request because the key is blank")
        val entityTest = RepublishEventRequest(
                "rw__test",
                payload,
                "Reason_1",
                "APPROVAL_USER'S_NAME",
                "",
                "t"
        )
        val jsonData = jacksonObjectMapper().writeValueAsString(entityTest)
        this.mvc.perform(MockMvcRequestBuilders.post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should result in successful if requisition is consumed`() {
        logger.info("should result in successful if requisition is consumed")
        val eventTest = RepublishEventRequest(
                "rw__test",
                payload,
                "Reason_1",
                "APPROVAL_USER'S_NAME",
                "key-msg",
                "t"
        )
        val republishEventRequest = jacksonObjectMapper().writeValueAsString(eventTest)
        this.mvc.perform(MockMvcRequestBuilders.post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(republishEventRequest))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
        val consumedPayload = File("./src/test/resources/payload.json")
                .readText()
                .replace("\n", "")
                .replace(" ", "")


        assertThat(consumer.latch.count).isNotEqualTo(1)
        assertThat(consumer.receiving).isEqualTo(consumedPayload)
        logger.info("\nsuccessful requisition and consuming\n${consumer.latch.count.compareTo(0)}\n${consumer.receiving}\n")
    }

    @Test(expected = NestedServletException::class)
    fun `should result in failed requisition because topic not exists`() {

        logger.info("should result in failed requisition and not republish and consumed")
        val entityTest = RepublishEventRequest(
                "rw_not-found",
                payload,
                "Reason_1",
                "APPROVAL_USER'S_NAME",
                "key-msg",
                "t"
        )
        val republishEventRequest = jacksonObjectMapper().writeValueAsString(entityTest)
        this.mvc.perform(MockMvcRequestBuilders.post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(republishEventRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test(expected = NestedServletException::class)
    fun `should result in failed requisition because reason not exists`() {

        logger.info("`should result in failed requisition because reason not exists`")
        val entityTest = RepublishEventRequest(
                "rw__test",
                payload,
                "Reason_not-found",
                "APPROVAL_USER'S_NAME",
                "key-msg",
                "t"
        )
        val republishEventRequest = jacksonObjectMapper().writeValueAsString(entityTest)
        this.mvc.perform(MockMvcRequestBuilders.post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(republishEventRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `when successful requisition, returns the list of topics`() {
        logger.info("`when successful requisition, returns the list of topics`")
        this.mvc.perform(MockMvcRequestBuilders.get("/events/topics"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
    }

    @Test
    fun `when successful requisition, returns the list of reasons`() {
        logger.info("`when successful requisition, returns the list of reasons`")
        this.mvc.perform(MockMvcRequestBuilders.get("/events/reasons"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
    }
}