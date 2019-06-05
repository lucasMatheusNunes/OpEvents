package br.com.zup.op.events.domain

import br.com.zup.op.events.infra.validation.AnnotationFieldsValidation
import java.util.UUID
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotEmpty

//@Entity
data class EventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = UUID.randomUUID(),
    @field:[NotEmpty]
    val topic: String,
    @field:[NotEmpty]
    val payload: String,
    @field:NotEmpty
    val user: String,
    @field:NotEmpty
    val key : String
) {

    fun validateFields() =
        AnnotationFieldsValidation().validFields(this)


    fun validateTopic(list: List<TopicEntiy>) {
        //Example for future implementation of topic in blacklist
        //if(!this.topic.startsWith("rw_")) throw java.lang.IllegalArgumentException("Topic $topic is invalid name")

        list.firstOrNull { it == TopicEntiy(this.topic) } ?: throw IllegalArgumentException("Topic $topic not found")
    }
}