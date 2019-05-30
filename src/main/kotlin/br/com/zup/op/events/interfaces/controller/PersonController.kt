package br.com.zup.op.events.interfaces.controller

import br.com.zup.op.events.domain.Person
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/person")
class PersonController {

    @PostMapping(produces = arrayOf("application/json"))
    fun add(@Valid @RequestBody person : Person) : String{
        return "Accept"
    }
}