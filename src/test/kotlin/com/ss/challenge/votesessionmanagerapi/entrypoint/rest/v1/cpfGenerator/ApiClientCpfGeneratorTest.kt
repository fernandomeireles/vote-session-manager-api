package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfGeneratorDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfValidatorBodyDto
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.apache.http.HttpHeaders
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.JsonBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.http.MediaType

class ApiClientCpfGeneratorTest : IntegrationBaseTest() {
    @Autowired
    private lateinit var apiClientCpfGenerator: ApiClientCpfGenerator

    @Value("classpath:json/cpf-generate.json")
    private lateinit var cpfGenerateResource: Resource

    @Value("classpath:json/cpf-validate.json")
    private lateinit var cpfValidateResource: Resource

    @Value("\${vote-session.api-cpf-generator.token}")
    private val token: String = ""

    private val ACCEPT = "Accept"
    private val AUTHORIZATION = "Authorization"

    @Test
    fun `Should return cpf generate with success`() {

        mockServer?.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/cpf/generate")
                .withHeader(
                    ACCEPT, MediaType.APPLICATION_JSON_VALUE
                )
                .withHeader(
                    AUTHORIZATION,
                    token
                )
        )?.respond(
            HttpResponse.response()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .withBody(cpfGenerateResource.inputStream.readBytes())
        )

        val cpfGenerator = apiClientCpfGenerator.getCpf(MediaType.APPLICATION_JSON_VALUE, token)

        Assertions.assertEquals(EXPECTED_RETURN_API.message, cpfGenerator.message)
    }

    @Test
    fun `Should return cpf generate validate with success`() {

        mockServer?.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/cpf/validate")
                .withHeader(
                    ACCEPT, MediaType.APPLICATION_JSON_VALUE
                )
                .withHeader(
                    AUTHORIZATION,
                    token
                )
                .withBody(JsonBody((String(cpfValidateResource.inputStream.readBytes()))))
        )?.respond(
            HttpResponse.response()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .withBody(cpfGenerateResource.inputStream.readBytes())
        )

        val cpfValidator = apiClientCpfGenerator.getValidateCpf(
            MediaType.APPLICATION_JSON_VALUE,
            token,
            CpfValidatorBodyDto("68716467795")
        )

        Assertions.assertEquals(EXPECTED_RETURN_API.message, cpfValidator.message)
    }

    companion object {

        val EXPECTED_RETURN_API = CpfGeneratorDto(
            true,
            "68716467795",
            "687.164.677-95",
            "CPF válido."
        )
    }
}
