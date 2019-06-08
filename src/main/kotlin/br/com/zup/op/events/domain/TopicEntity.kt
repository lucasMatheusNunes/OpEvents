package br.com.zup.op.events.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Id

@ApiModel(description = "Class that represents an topic entity. On access the " +
        "URI \"/events/topics\", this application returns list of topics for " +
        "select a valid topic for republish of event on the kafka.")
data class TopicEntity(

        @Id
        @ApiModelProperty(notes = "Title of topic", example = "REASON_A")
        val name: String
)