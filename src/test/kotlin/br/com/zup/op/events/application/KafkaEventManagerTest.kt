package br.com.zup.op.events.application

import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KafkaEventManagerTest {

  @Autowired
  lateinit var eventManager: KafkaEventManager

  @Test
  fun republishSuccessTest() {
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
    val result = this.eventManager!!.republish(entityTest_a)
    assertNotNull(result)
    assertEquals(result.status, "PUBLISHED")
    println("\n" + result.id + "\n" + result.status + "\n")

  }

}