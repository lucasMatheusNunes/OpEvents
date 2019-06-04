package br.com.zup.op.events.domain

import br.com.zup.op.events.infra.annotation.PayloadValid
import br.com.zup.op.events.validation.AnnotationFieldsValidation
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
    @field:[NotEmpty PayloadValid]
    val payload: String,
    @field:NotEmpty
    val user: String
) {

    fun validateFields() {
        AnnotationFieldsValidation().validFields(this)
    }

    fun validateTopic(list: ArrayList<TopicEntiy>) {
        if (!list.contains(TopicEntiy(this.topic))) throw IllegalArgumentException("Topic not found")
    }
}