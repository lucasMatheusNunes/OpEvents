package br.com.zup.op.events.interfaces.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Reason(

    @Id
    val name: String
)