package br.com.zup.op.events.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "topic")
data class TopicEntiy(
    @Id
    val name: String
)