package com.ss.challenge.votesessionmanagerapi.utils

import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@ComponentScan("com.ss.challenge")
@EnableFeignClients
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:clear-data.sql"])
abstract class IntegrationBaseTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    companion object {
        private const val port = 1337
        var mockServer: MockServerClient? = null

        init {
            mockServer = ClientAndServer.startClientAndServer(port)
        }
    }
}
