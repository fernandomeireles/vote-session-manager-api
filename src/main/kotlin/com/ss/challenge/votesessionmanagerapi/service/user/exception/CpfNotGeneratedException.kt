package com.ss.challenge.votesessionmanagerapi.service.user.exception

data class CpfNotGeneratedException(private var status_message: String) :
    RuntimeException("CPF not generated, error=$status_message")
