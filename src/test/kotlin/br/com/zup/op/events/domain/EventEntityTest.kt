package br.com.zup.op.events.domain

import br.com.zup.op.events.interfaces.model.Reason
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class EventEntityTest {

    val reasons: ArrayList<Reason> = arrayListOf(
            Reason("REASON_A"),
            Reason("REASON_B"),
            Reason("REASON_C")
    )

    @Before
    fun setUp() {
        println("\n\n Initialize MokitoAnnotations \n\n")
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun isReasonValidTest() {
        println("\n\n Test of reason validity \n\n")
        val entityTest = EventEntity(
                "",
                "TOPIC_A",
                "REASON_A",
                "{" +
                        "'attribute_a': 'param_a'," +
                        "'attribute_b': {" +
                            "'obj_attribute_a': 'param_attribute_a'" +
                            "'obj_attribute_b': 'param_attribute_b'" +
                            "'obj_attribute_c': {" +
                                "'var_obj_a': 'value_a'" +
                                "'var_obj_b': 'value_b'" +
                                "'var_obj_c': 'value_c'" +
                                "}" +
                            "}" +
                        "}",
                "APPROVER_USER'S_NAME",
                ""
        )
        Assert.assertTrue(entityTest!!.isReasonValid(reasons) is Unit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidReasonTest(){
        println("\n\n Test of reason validity \n\n")
        val entityTest = EventEntity(
                "",
                "TOPIC_A",
                "INVALID_REASON",
                "{" +
                        "'attribute_a': 'param_a'," +
                        "'attribute_b': {" +
                        "'obj_attribute_a': 'param_attribute_a'" +
                        "'obj_attribute_b': 'param_attribute_b'" +
                        "'obj_attribute_c': {" +
                        "'var_obj_a': 'value_a'" +
                        "'var_obj_b': 'value_b'" +
                        "'var_obj_c': 'value_c'" +
                        "}" +
                        "}" +
                        "}",
                "APPROVER_USER'S_NAME",
                ""
        )
        entityTest!!.isReasonValid(reasons)
    }

}