package br.com.zup.op.events.domain

class EventEntity(
    val topic : String
) {

    fun isValidTopic(list : ArrayList<TopicEntiy>) {
        if ( list.find { s -> s.name  == this.topic } == null) throw IllegalArgumentException("Topic not found")
    }
}