package br.com.zup.op.events.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class ReasonEntity(

    @Id
    val name: String
)