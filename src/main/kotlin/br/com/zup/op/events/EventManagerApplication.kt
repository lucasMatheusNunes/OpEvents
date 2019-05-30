package br.com.zup.op.events

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventManagerApplication

fun main(args: Array<String>) {
	runApplication<EventManagerApplication>(*args)
}
