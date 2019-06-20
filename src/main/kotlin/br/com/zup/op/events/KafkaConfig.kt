package br.com.zup.op.events

import org.apache.kafka.clients.consumer.Consumer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.ConsumerFactory

@Configuration
class KafkaConfig(private val consumerFactory: ConsumerFactory<String, Any>) {

  @Bean
  fun consumerTopic(): Consumer<String, Any> {
    return consumerFactory.createConsumer()
  }

}

