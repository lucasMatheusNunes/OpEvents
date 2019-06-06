package br.com.zup.op.events.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository : JpaRepository<TopicEntiy, String>