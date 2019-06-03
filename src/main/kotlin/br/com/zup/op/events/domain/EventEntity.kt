package br.com.zup.op.events.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
@ApiModel(description = "Class representing a person entity that makes payments")
class EventEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = UUID.randomUUID(),

    @ApiModelProperty(notes = "Title of topic", example = "TOPIC_A", required = true)
    val topic: String,
    @ApiModelProperty(notes = "Title of reason", example = "REASON_A", required = true)
    val reason: String,
    @ApiModelProperty(
        notes = "JSON Object of republish",
        example = "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}",
        required = true)
    val payload: String,
    @ApiModelProperty(notes = "Approver user", example = "John Smith", required = true)
    val user_name: String,
    @ApiModelProperty(notes = "Note regarding the event", example = "", required = false)
    val note: String?

) {

  fun isReasonValid(list: ArrayList<ReasonEntity>) {
    if (list.find { s -> s.name == this.reason } == null)
      throw IllegalArgumentException("Reason not Found!")
  }

}