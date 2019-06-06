package br.com.zup.op.events.producer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class kafkaProducer(kafkaTemplate: KafkaTemplate<String, String>)  {

  @Autowired
  private var kafkaTemplate: KafkaTemplate<String, String>? = null

  init {
    this.kafkaTemplate = kafkaTemplate
  }
  fun send(payload: String) {
    kafkaTemplate!!.send("TOPIC_A", payload)
  }


}