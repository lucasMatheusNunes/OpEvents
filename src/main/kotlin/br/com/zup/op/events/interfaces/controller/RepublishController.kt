package br.com.zup.op.events.interfaces.controller

import br.com.zup.op.events.application.EventManager
import br.com.zup.op.events.domain.ReasonEntity
import br.com.zup.op.events.domain.TopicEntity
import br.com.zup.op.events.interfaces.model.RepublishEventRequest
import br.com.zup.op.events.interfaces.model.RepublishEventResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost"]) // Enabling CORS only for the local application. We will be changing the address as soon as it is implemented for production.
@RestController
@RequestMapping("/events")
class RepublishController(private val eventManager: EventManager) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun republish(@RequestBody request: RepublishEventRequest): RepublishEventResponse {

       return eventManager.republish(request)
    }

    @GetMapping("/reasons")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getReasonsList(): List<ReasonEntity> {
        return eventManager.listReasons()
    }

    @GetMapping("/topics")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getTopicsList(): List<TopicEntity> {
        return eventManager.listTopics()
    }
}
