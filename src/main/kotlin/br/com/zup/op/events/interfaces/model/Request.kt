package br.com.zup.op.events.interfaces.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Class that represents an event publishing request entity")
data class RepublishEventRequest(

        @ApiModelProperty(notes = "Title of topic", example = "TOPIC_A",  required = true)
        val topic: String,

        @ApiModelProperty(notes = "JSON Object of republish", required = true)
        val payload: Map<String, *>,

        @ApiModelProperty(notes = "Title of reason", example = "REASON_A", required = true)
        val reason: String,
        @ApiModelProperty(notes = "Approval user", required = true)
        val user_id: String,

        @ApiModelProperty(notes = "Key of kafka payload", required = true)
        val _key: String,

        @ApiModelProperty(notes = "Note regarding the event", required = false, allowEmptyValue = true)
        val note: String?
)

@ApiModel(description = "Class that represents an event republishing response entity")
data class RepublishEventResponse(

        @ApiModelProperty(notes = "UUID of persistened of event republished ", example = "401792cd-c65f-426b-8af5-ab34b828127e")
        val id: String,

        @ApiModelProperty(notes = "Message of success on republish ", example = "Event Republish Success")
        val message: String
)

