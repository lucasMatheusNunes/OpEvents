package br.com.zup.op.events.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class KafkaConsumer {
    private val logger: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    var latch = CountDownLatch(1)

    lateinit var receiving: String

    @KafkaListener(id = "consumer-test-group", topics = ["rw__test"])
    fun listen(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
            @Payload payload: String
    ) {
        logger.info("listenMsg: $key \n")
        this.receiving = payload
        logger.info("CountDownLatch: $latch \n")
        this.latch.countDown()
        logger.info("CountDownLatch: $latch \n")
    }

}

