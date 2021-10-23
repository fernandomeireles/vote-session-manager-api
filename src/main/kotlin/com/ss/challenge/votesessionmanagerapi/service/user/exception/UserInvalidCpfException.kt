package com.ss.challenge.votesessionmanagerapi.service.user.exception

data class UserInvalidCpfException(private var cpf: String) :
    RuntimeException("User not found for this CPF=$cpf")
