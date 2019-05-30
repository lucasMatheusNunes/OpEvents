package br.com.zup.op.events.domain

import br.com.zup.op.events.interfaces.model.Reason
import javax.persistence.Entity


@Entity
class EventEntity (
    val reason: Reason,
    val obs: String

)  {
    fun isValidReason(list : Array<Reason>):Boolean{
        var isvalid: Boolean = false
        for (t in list){
            if (this.reason == t){
                isvalid = true
            }
        }
        return isvalid
    }

}