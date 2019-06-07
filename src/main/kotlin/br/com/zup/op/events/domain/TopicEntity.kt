package br.com.zup.op.events.domain

import javax.persistence.Id

data class TopicEntity(
    @Id
    val name: String
)