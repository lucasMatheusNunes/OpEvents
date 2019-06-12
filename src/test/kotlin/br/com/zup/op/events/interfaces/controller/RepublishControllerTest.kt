package br.com.zup.op.events.interfaces.controller


import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RepublishControllerTest {
    private val logger: Logger = LoggerFactory.getLogger("\nLog RepublishControllerTest")

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    private lateinit var controller: RepublishController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()
    }

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
        var jsonData = jacksonObjectMapper().writeValueAsString(entityTest)
        this.mvc.perform(MockMvcRequestBuilders.post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
    }

    @Test
    fun `when successful requisition, returns the list of topics`() {
        logger.info("Testing:  when successful requisition, returns the list of topics\n")
        this.mvc.perform(MockMvcRequestBuilders.get("/events/topics"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
    }

    @Test
    fun `when successful requisition, returns the list of reasons`() {
        logger.info("Testing:  when successful requisition, returns the list of reasons\n")
        this.mvc.perform(MockMvcRequestBuilders.get("/events/reasons"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
    }
}