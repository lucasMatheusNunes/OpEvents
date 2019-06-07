package br.com.zup.op.events.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Id

@ApiModel(description = "Class that represents an topic entity returned of to republish event")
data class TopicEntity(

    @Id
    @ApiModelProperty(notes = "Title of topic", example = "REASON_A")
    val name: String
)