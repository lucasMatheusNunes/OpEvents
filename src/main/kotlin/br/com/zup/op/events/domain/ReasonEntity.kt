package br.com.zup.op.events.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "reason")
data class ReasonEntity(

    @Id
    val name: String
)