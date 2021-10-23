package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user

import java.time.LocalDateTime

data class UserDto(

    var idUser: Long?,
    var cpf: String?,
    var dateCreation: LocalDateTime,
    var dateUpdate: LocalDateTime,
    var isActive: Boolean
)
