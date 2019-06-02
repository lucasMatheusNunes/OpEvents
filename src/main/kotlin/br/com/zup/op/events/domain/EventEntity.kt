package br.com.zup.op.events.domain

import br.com.zup.op.events.interfaces.model.Reason
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class EventEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: String,
        val topic:String,
        val reason: String,
        val payload: String,
        val user_name: String,
        val note: String

) {

    fun isReasonValid(list: ArrayList<Reason>) {
        if (list.find { s -> s.name == this.reason } == null)
            throw IllegalArgumentException("Reason not Found!")
    }

}