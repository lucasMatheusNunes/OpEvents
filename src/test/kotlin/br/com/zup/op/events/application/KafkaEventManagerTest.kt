package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class KafkaEventManagerTest {

  @Autowired
  private var mockMvc: MockMvc? = null

  @Autowired
  private val kafkaEventManager = KafkaEventManager()


  @Before
  fun setUp() {
    println("\n\n KafkaEventManagerTest Initialize MockMvcBuilders \n\n")
    MockitoAnnotations.initMocks(this)
    MockMvcBuilders.standaloneSetup(kafkaEventManager).build()

  }


  @Test
  fun republish() {
    println("\n Test of republish method in KafkaEventManager \n")

    val payload = mapOf(
        "attribute_a" to "param_a",
        "attribute_b" to mapOf(
            "obj_attribute_a" to "param_attribute_a",
            "obj_attribute_b" to "param_attribute_b",
            "obj_attribute_c" to mapOf(
                "var_obj_a" to "value_a",
                "var_obj_b" to "value_b",
                "var_obj_c" to "value_c"
            )
        )
    )
    val entityTest_a = RepublishEventRequest(
        "TOPIC_A",
        payload,
        "REASON_A",
        "APPROVER_USER'S_NAME",
        ""
    )
    println("\nkafkaEventManager.republish(entityTest).run\n")
    val result = this.kafkaEventManager.republish(entityTest_a) //.run { status != "REPUBLISHED"  }
    assertNotNull(result)
    assertEquals(result.status, "PUBLISHED")
    println("\n" + result.id + "\n" + result.status + "\n")

    /*
    //java.lang.NullPointerException in MockMvc

    val entityTest_b = mapOf(
        "topic" to "TOPIC_A",
        "reason" to "REASON_A",
        "payload" to mapOf(
            "attribute_a" to "param_a",
            "attribute_b" to mapOf(
                "obj_attribute_a" to "param_attribute_a",
                "obj_attribute_b" to "param_attribute_b",
                "obj_attribute_c" to mapOf(
                    "var_obj_a" to "value_a",
                    "var_obj_b" to "value_b",
                    "var_obj_c" to "value_c"
                )
            )
        ),
        "user_name" to "APPROVER_USER'S_NAME"
    )
    this.mockMvc!!.perform(MockMvcRequestBuilders.post("/events") //java.lang.NullPointerException
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject(eventTest).toString())
    )
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").isString)
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString)
     */
    println("\n Test of republish method in KafkaEventManager complete!\n")
  }

}