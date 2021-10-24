package com.ss.challenge.votesessionmanagerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class VoteSessionManagerApiApplication

fun main(args: Array<String>) {
    runApplication<VoteSessionManagerApiApplication>(*args)
}
