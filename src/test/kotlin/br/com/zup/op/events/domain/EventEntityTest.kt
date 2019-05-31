package br.com.zup.op.events.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc


@RunWith(SpringRunner::class)
class EventEntityTest {

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun isValidTopicTest() {
        print("\n\n Testing isValidTopic method \n\n")
        val topicEntity: ArrayList<TopicEntiy> =
            arrayListOf(
                TopicEntiy("TOPICNAME-A"),
                TopicEntiy("TOPICNAME-B"),
                TopicEntiy("TOPICNAME-C"))

        val eventEntity = EventEntity("TOPICNAME-C")

        Assert.assertTrue(eventEntity!!.isValidTopic(topicEntity) is Unit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidTopicTest() {
        println("\n\n Testing invalid topic value")

        val topicEntity: ArrayList<TopicEntiy> = arrayListOf()

        val eventEntity = EventEntity("TOPIC")

        eventEntity.isValidTopic(topicEntity)

    }
}