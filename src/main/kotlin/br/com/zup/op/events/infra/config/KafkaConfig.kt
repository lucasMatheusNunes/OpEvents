package br.com.zup.op.events.infra.config

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.util.*

@Configuration
class KafkaConfig {

  @Autowired
  private val consumerFactory: ConsumerFactory<String, Any>? = null

  @Bean
  fun consumerTopic(): Consumer<String, Any> {
    return consumerFactory!!.createConsumer()
  }

}