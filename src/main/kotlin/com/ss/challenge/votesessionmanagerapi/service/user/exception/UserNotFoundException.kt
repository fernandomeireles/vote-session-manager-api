package com.ss.challenge.votesessionmanagerapi.service.user.exception

data class UserNotFoundException(private var idUser: Long) :
    RuntimeException("User not found for this id=$idUser")
