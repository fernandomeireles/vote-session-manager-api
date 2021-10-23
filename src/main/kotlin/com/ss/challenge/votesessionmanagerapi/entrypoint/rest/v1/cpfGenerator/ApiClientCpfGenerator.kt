package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfGeneratorDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfValidatorBodyDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto.CpfValidatorResultDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "apiCpfGenerator", url = "\${vote-session.api-cpf-generator.url}")
interface ApiClientCpfGenerator {

    @GetMapping("/cpf/generate")
    fun getCpf(
        @RequestHeader("Accept") accept: String,
        @RequestHeader("Authorization") auth: String
    ): CpfGeneratorDto

    @GetMapping("/cpf/validate")
    fun getValidateCpf(
        @RequestHeader("Accept") accept: String,
        @RequestHeader("Authorization") auth: String,
        @SpringQueryMap @ModelAttribute number: CpfValidatorBodyDto
    ): CpfValidatorResultDto
}
