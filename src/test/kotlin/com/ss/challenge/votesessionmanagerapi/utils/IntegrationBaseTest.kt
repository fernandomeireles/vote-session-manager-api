package com.ss.challenge.votesessionmanagerapi.utils

import com.ss.challenge.votesessionmanagerapi.utils.kafka.EnableKafkaTest
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@ComponentScan("com.ss.challenge")
@EmbeddedKafka(partitions = 1)
@ContextConfiguration(classes = [EnableKafkaTest::class])
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
