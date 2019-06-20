package br.com.zup.op.events.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Id

@Repository
interface EventRepository: CrudRepository<EventEntity, Id>{


}
