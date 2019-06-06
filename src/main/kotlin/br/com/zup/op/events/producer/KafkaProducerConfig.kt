package br.com.zup.op.events.producer

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
class KafkaProducerConfig {

  @Autowired
  private val consumerFactory: ConsumerFactory<String, Any>? = null

  val bootstrapAddress: String = "localhost:2181"

  @Bean
  fun producerFactory(): ProducerFactory<String, String> {
    val configProps = HashMap<String, Any>()
    configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
    configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    return DefaultKafkaProducerFactory(configProps)
  }

  @Bean
  fun kafkaTemplate(): KafkaTemplate<String, String> {
    return KafkaTemplate(producerFactory())
  }


  @Bean
  fun consumerTopic(): Consumer<String, Any> {
    return this.consumerFactory!!.createConsumer()
  }

}


