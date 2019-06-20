package br.com.zup.op.events.domain

import br.com.zup.op.events.infra.validation.AnnotationFieldsValidation
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "event")
data class EventEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID? = UUID.randomUUID(),
        @field:[NotEmpty]
        val topic: String,
        @field:[NotEmpty]
        val reason: String,
        @field:[NotEmpty]
        val payload: String,
        val user_id: String,
        @field:NotEmpty
        val _key: String,
        val note: String?

) {

    fun validateFields() =
            AnnotationFieldsValidation().validFields(this)

    fun validateReason(list: List<ReasonEntity>) {
        list.firstOrNull { it == ReasonEntity(this.reason) } ?: throw IllegalArgumentException("$reason not found")
    }

    fun validateTopic(list: List<TopicEntity>) {
        //Example for future implementation of topic in blacklist
        //if(!this.topic.startsWith("rw_")) throw java.lang.IllegalArgumentException("Topic $topic is invalid name")

        list.firstOrNull { it == TopicEntity(this.topic) } ?: throw IllegalArgumentException("Topic $topic not found")
    }
}
