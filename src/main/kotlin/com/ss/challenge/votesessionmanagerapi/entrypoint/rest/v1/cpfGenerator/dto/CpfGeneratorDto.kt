package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.cpfGenerator.dto

data class CpfGeneratorDto(
    val status: Boolean,
    val number: String,
    val number_formatted: String,
    val message: String
)
