package com.ss.challenge.votesessionmanagerapi

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
class VoteSessionManagerApiApplication

fun main(args: Array<String>) {
    runApplication<VoteSessionManagerApiApplication>(*args)
}
