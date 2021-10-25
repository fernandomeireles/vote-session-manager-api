package com.ss.challenge.votesessionmanagerapi

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@SpringBootTest
class VoteSessionManagerApiApplicationTests {

    @Test
    fun contextLoads() {
    }
}
