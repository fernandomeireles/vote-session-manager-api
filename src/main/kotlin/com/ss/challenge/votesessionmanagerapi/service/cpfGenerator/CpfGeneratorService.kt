package com.ss.challenge.votesessionmanagerapi.service.cpfGenerator

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.ApiClientCpfGenerator
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfGeneratorDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfValidatorBodyDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfValidatorResultDto
import com.ss.challenge.votesessionmanagerapi.service.user.exception.CpfNotGeneratedException
import feign.FeignException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class CpfGeneratorService(private val apiClientCpfGenerator: ApiClientCpfGenerator) {

    @Value("\${vote-session.api-cpf-generator.token}")
    private val token: String = ""

    fun getCpf(): CpfGeneratorDto {

        try {
            val cpf = apiClientCpfGenerator.getCpf(
                MediaType.APPLICATION_JSON_VALUE,
                token
            )

            return if (cpf.status) cpf else throw CpfNotGeneratedException(cpf.message)
        } catch (f: FeignException) {
            throw CpfNotGeneratedException(f.localizedMessage)
        }
    }

    fun validateCpf(cpf: String): CpfValidatorResultDto {
        try {
            return apiClientCpfGenerator.getValidateCpf(
                MediaType.APPLICATION_JSON_VALUE,
                token,
                CpfValidatorBodyDto(cpf)
            )
        } catch (f: FeignException) {
            throw CpfNotGeneratedException(f.localizedMessage)
        }
    }
}
