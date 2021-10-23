package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto

data class CpfValidatorResultDto(
    val status: Boolean,
    val number: String,
    val number_formatted: String,
    val message: String
)
