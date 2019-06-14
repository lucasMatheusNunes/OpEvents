package br.com.zup.op.events.application

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch


@Component
class KafkaConsumerForTest {

    val latch: CountDownLatch = CountDownLatch(0)
    lateinit var receiving: String
    @KafkaListener(id = "kotlin",topics = ["test", "rw_1"])
    fun listen(value: String) {
        this.latch.countDown()
        this.receiving = value
    }

}

