package br.com.zup.op.events.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "reason")
@ApiModel(description = "Class that represents an reason entity. On access the " +
        "URI \"/events/reasons\", this application returns list of reasons for " +
        "select a valid reason for republish of event on the kafka.")

data class ReasonEntity(

        @Id
        @ApiModelProperty(notes = "Title of reason", example = "REASON_A")
        val name: String
)