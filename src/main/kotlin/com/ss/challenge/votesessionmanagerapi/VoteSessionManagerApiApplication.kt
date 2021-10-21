package com.ss.challenge.votesessionmanagerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class VoteSessionManagerApiApplication

fun main(args: Array<String>) {
    runApplication<VoteSessionManagerApiApplication>(*args)
}
